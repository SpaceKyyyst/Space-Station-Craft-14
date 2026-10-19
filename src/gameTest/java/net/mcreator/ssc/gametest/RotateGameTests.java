package net.mcreator.ssc.gametest;

import com.mojang.authlib.GameProfile;
import java.lang.reflect.Proxy;
import java.util.UUID;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.medical.MedicalPlayerEffects;
import net.mcreator.ssc.network.RotateMessage;
import net.mcreator.ssc.procedures.Objects_Rotate_RRRProcedure;
import net.mcreator.ssc.rotation.RotateCommand;
import net.mcreator.ssc.rotation.RotationPolicy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.DirectionalBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/** Exercises the same public transport and compatibility entry points as callers. */
@EventBusSubscriber(modid = "ssc_14")
public final class RotateGameTests {
    private static final String[] NAMES = {"rotate_exact_hit", "rotate_critical", "rotate_spectator", "rotate_invalid_intent",
            "rotate_miss", "rotate_reach", "rotate_facade", "rotate_world_border", "rotate_unloaded", "rotate_replaced_target",
            "rotate_loaded_traversal", "rotate_supported_states", "rotate_unsupported_states", "rotate_non_server_facade", "rotate_height",
            "rotate_transport", "rotate_medical_thresholds"};
    private static BlockPos observedPosition;
    private static int neighborNotifications;

    private RotateGameTests() { }

    private static Identifier id(String name) { return Identifier.fromNamespaceAndPath("ssc_14", name); }

    @SubscribeEvent
    public static void functions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> {
            registry.register(id(NAMES[0]), RotateGameTests::exactHit);
            registry.register(id(NAMES[1]), helper -> denied(helper, GameType.SURVIVAL, 100));
            registry.register(id(NAMES[2]), helper -> denied(helper, GameType.SPECTATOR, 0));
            registry.register(id(NAMES[3]), RotateGameTests::invalidIntent);
            registry.register(id(NAMES[4]), helper -> noTarget(helper, false));
            registry.register(id(NAMES[5]), helper -> noTarget(helper, true));
            registry.register(id(NAMES[6]), RotateGameTests::facade);
            registry.register(id(NAMES[7]), RotateGameTests::worldBorder);
            registry.register(id(NAMES[8]), RotateGameTests::unloaded);
            registry.register(id(NAMES[9]), RotateGameTests::replacedTarget);
            registry.register(id(NAMES[10]), RotateGameTests::loadedTraversal);
            registry.register(id(NAMES[11]), RotateGameTests::supportedStates);
            registry.register(id(NAMES[12]), RotateGameTests::unsupportedStates);
            registry.register(id(NAMES[13]), RotateGameTests::nonServerFacade);
            registry.register(id(NAMES[14]), RotateGameTests::height);
            registry.register(id(NAMES[15]), RotateGameTests::transport);
            registry.register(id(NAMES[16]), RotateGameTests::medicalThresholds);
        });
    }

    @SubscribeEvent
    public static void tests(RegisterGameTestsEvent event) {
        var data = new TestData<>(event.registerEnvironment(id("rotate_environment")), id("audit_empty"), 100, 1, true);
        for (String name : NAMES) {
            event.registerTest(id(name), new FunctionGameTestInstance(ResourceKey.create(Registries.TEST_FUNCTION, id(name)), data));
        }
    }

    private static void exactHit(GameTestHelper helper) {
        var fixture = fixture(helper);
        observedPosition = fixture.target;
        neighborNotifications = 0;
        try {
            RotateMessage.pressAction(fixture.player, 0, 0);
        } finally {
            observedPosition = null;
        }
        helper.assertTrue(helper.getLevel().getBlockState(fixture.target).getValue(HorizontalDirectionalBlock.FACING) == Direction.EAST,
                "The actual nonzero-Z hit must rotate clockwise exactly once");
        helper.assertTrue(neighborNotifications == 1, "Exactly one target update must notify its neighbors");
        assertDecoy(helper, fixture);
        helper.succeed();
    }

    @SubscribeEvent
    public static void observeMutation(BlockEvent.NeighborNotifyEvent event) {
        if (event.getPos().equals(observedPosition)) neighborNotifications++;
    }

    private static void denied(GameTestHelper helper, GameType mode, double damage) {
        var fixture = fixture(helper);
        fixture.player.setGameMode(mode);
        fixture.player.getPersistentData().putDouble(MedicalPlayerEffects.TOTAL_DAMAGE, damage);
        RotateMessage.pressAction(fixture.player, 0, 0);
        assertUnchanged(helper, fixture);
        helper.succeed();
    }

    private static void invalidIntent(GameTestHelper helper) {
        var fixture = fixture(helper);
        for (int type : new int[] {-1, 1, Integer.MIN_VALUE, Integer.MAX_VALUE}) {
            RotateMessage.pressAction(fixture.player, type, Integer.MIN_VALUE);
            assertUnchanged(helper, fixture);
        }
        helper.succeed();
    }

    private static void noTarget(GameTestHelper helper, boolean distant) {
        var fixture = fixture(helper);
        if (distant) fixture.player.setPos(fixture.player.getX(), fixture.player.getY(), fixture.target.getZ() - 8);
        else helper.getLevel().setBlock(fixture.target, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
        RotateMessage.pressAction(fixture.player, 0, 0);
        if (distant) assertUnchanged(helper, fixture);
        assertDecoy(helper, fixture);
        helper.succeed();
    }

    private static void facade(GameTestHelper helper) {
        var fixture = fixture(helper);
        fixture.player.setGameMode(GameType.ADVENTURE);
        Objects_Rotate_RRRProcedure.execute(helper.getLevel(), fixture.player);
        assertUnchanged(helper, fixture);
        // A mismatched level must not redirect a ServerPlayer command into another accessor.
        Objects_Rotate_RRRProcedure.execute(null, fixture.player);
        assertUnchanged(helper, fixture);
        fixture.player.setGameMode(GameType.SURVIVAL);
        Objects_Rotate_RRRProcedure.execute(helper.getLevel(), fixture.player);
        helper.assertTrue(helper.getLevel().getBlockState(fixture.target).getValue(HorizontalDirectionalBlock.FACING) == Direction.EAST,
                "A legitimate direct facade must converge on the same command");
        assertDecoy(helper, fixture);
        helper.succeed();
    }

    private static void worldBorder(GameTestHelper helper) {
        var fixture = fixture(helper);
        var border = helper.getLevel().getWorldBorder();
        double centerX = border.getCenterX();
        double centerZ = border.getCenterZ();
        double size = border.getSize();
        try {
            border.setCenter(fixture.player.getX(), fixture.player.getZ());
            border.setSize(1);
            helper.assertTrue(!helper.getLevel().mayInteract(fixture.player, fixture.target), "Target must be denied by real level protection");
            RotateMessage.pressAction(fixture.player, 0, 0);
            assertUnchanged(helper, fixture);
        } finally {
            border.setCenter(centerX, centerZ);
            border.setSize(size);
        }
        helper.succeed();
    }

    private static void loadedTraversal(GameTestHelper helper) {
        var fixture = fixture(helper);
        int chunkX = fixture.target.getX() >> 4;
        int chunkZ = 1000;
        helper.getLevel().getChunk(chunkX, chunkZ); // Only the fixture may load its starting chunk.
        helper.assertTrue(helper.getLevel().getChunkSource().getChunkNow(chunkX, chunkZ + 1) == null,
                "The next traversal chunk must start unavailable");
        fixture.player.setPos(chunkX * 16 + 8.5, 80, chunkZ * 16 + 15.5);
        RotateMessage.pressAction(fixture.player, 0, 0);
        helper.assertTrue(helper.getLevel().getChunkSource().getChunkNow(chunkX, chunkZ + 1) == null,
                "A ray starting in a loaded chunk must stop before loading the next chunk");
        assertUnchanged(helper, fixture);
        helper.succeed();
    }

    private static void supportedStates(GameTestHelper helper) {
        var fixture = fixture(helper);
        for (Direction expected : new Direction[] {Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH}) {
            helper.assertTrue(RotateCommand.execute(fixture.player, 0), "A supported facing must rotate");
            helper.assertTrue(helper.getLevel().getBlockState(fixture.target).getValue(HorizontalDirectionalBlock.FACING) == expected,
                    "Each intent must advance by exactly one clockwise quarter turn");
        }
        fixture.player.setGameMode(GameType.CREATIVE);
        helper.assertTrue(RotateCommand.execute(fixture.player, 0), "Creative must retain supported rotation");
        helper.assertTrue(helper.getLevel().getBlockState(fixture.target).getValue(HorizontalDirectionalBlock.FACING) == Direction.EAST,
                "Creative rotation must also advance once");
        assertDecoy(helper, fixture);
        helper.succeed();
    }

    private static void unsupportedStates(GameTestHelper helper) {
        var fixture = fixture(helper);
        for (var state : new net.minecraft.world.level.block.state.BlockState[] {
                Blocks.STONE.defaultBlockState(), Blocks.OAK_LOG.defaultBlockState(),
                Blocks.DISPENSER.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.UP),
                Blocks.DISPENSER.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.DOWN),
                Blocks.FURNACE.defaultBlockState()}) {
            helper.getLevel().setBlock(fixture.target, state, Block.UPDATE_ALL);
            helper.assertTrue(RotationPolicy.rotate(state).isEmpty(), "Unsupported or untagged states must fail closed");
            helper.assertTrue(!RotateCommand.execute(fixture.player, 0), "Unsupported target must not mutate");
            helper.assertTrue(helper.getLevel().getBlockState(fixture.target) == state, "Unsupported state must remain exact");
        }
        assertDecoy(helper, fixture);
        helper.succeed();
    }

    private static void nonServerFacade(GameTestHelper helper) {
        var fixture = fixture(helper);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setPos(fixture.player.position());
        player.setYRot(0);
        player.setXRot(0);
        RotateMessage.pressAction(player, 0, 0);
        Objects_Rotate_RRRProcedure.execute(helper.getLevel(), player);
        RotateMessage.pressAction(null, 0, 0);
        Objects_Rotate_RRRProcedure.execute(helper.getLevel(), null);
        assertUnchanged(helper, fixture);
        helper.succeed();
    }

    private static void height(GameTestHelper helper) {
        var fixture = fixture(helper);
        fixture.player.setPos(fixture.player.getX(), helper.getLevel().getMaxY() + 8, fixture.player.getZ());
        helper.assertTrue(!RotateCommand.execute(fixture.player, 0), "Out-of-height ray must not mutate");
        assertUnchanged(helper, fixture);
        helper.succeed();
    }

    private static void transport(GameTestHelper helper) {
        var fixture = fixture(helper);
        var message = new RotateMessage(0, Integer.MAX_VALUE);
        RotateMessage.handleData(message, context(fixture.player, PacketFlow.CLIENTBOUND));
        RotateMessage.handleData(message, context(null, PacketFlow.SERVERBOUND));
        RotateMessage.handleData(message, context(helper.makeMockPlayer(GameType.SURVIVAL), PacketFlow.SERVERBOUND));
        for (int type : new int[] {-1, 1, Integer.MIN_VALUE, Integer.MAX_VALUE}) {
            RotateMessage.handleData(new RotateMessage(type, Integer.MIN_VALUE), context(fixture.player, PacketFlow.SERVERBOUND));
        }
        assertUnchanged(helper, fixture);
        RotateMessage.handleData(message, context(fixture.player, PacketFlow.SERVERBOUND));
        helper.assertTrue(helper.getLevel().getBlockState(fixture.target).getValue(HorizontalDirectionalBlock.FACING) == Direction.EAST,
                "One valid packet must cause one authoritative rotation; unused duration cannot change authority");
        assertDecoy(helper, fixture);
        helper.succeed();
    }

    private static IPayloadContext context(Player player, PacketFlow flow) {
        // Only network plumbing is substituted; the player, command, policy, level and mutation are real.
        return (IPayloadContext) Proxy.newProxyInstance(IPayloadContext.class.getClassLoader(), new Class<?>[] {IPayloadContext.class},
                (proxy, method, args) -> switch (method.getName()) {
                    case "flow" -> flow;
                    case "player" -> player;
                    default -> throw new AssertionError("Unexpected transport operation: " + method.getName());
                });
    }

    private static void medicalThresholds(GameTestHelper helper) {
        var fixture = fixture(helper);
        for (int damage : new int[] {99, 100, 200, 201}) {
            var original = Ssc14ModBlocks.MACHINE_FRAME.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH);
            helper.getLevel().setBlock(fixture.target, original, Block.UPDATE_ALL);
            fixture.player.getPersistentData().putDouble(MedicalPlayerEffects.TOTAL_DAMAGE, damage);
            boolean rotated = RotateCommand.execute(fixture.player, 0);
            boolean permitted = damage == 99 || damage == 201;
            helper.assertTrue(rotated == permitted, "Rotate must use the shared inclusive critical window at " + damage);
            helper.assertTrue(helper.getLevel().getBlockState(fixture.target).getValue(HorizontalDirectionalBlock.FACING)
                    == (permitted ? Direction.EAST : Direction.NORTH), "Critical authorization must govern the actual mutation");
        }
        assertDecoy(helper, fixture);
        helper.succeed();
    }

    private static void unloaded(GameTestHelper helper) {
        var fixture = fixture(helper);
        int chunk = 100_000;
        helper.assertTrue(helper.getLevel().getChunkSource().getChunkNow(chunk, chunk) == null, "Fixture chunk must start unloaded");
        fixture.player.setPos(chunk * 16 + 15.5, 80, chunk * 16 + 15.5);
        RotateMessage.pressAction(fixture.player, 0, 0);
        helper.assertTrue(helper.getLevel().getChunkSource().getChunkNow(chunk, chunk) == null, "Rotate must never load the eye chunk");
        helper.assertTrue(helper.getLevel().getChunkSource().getChunkNow(chunk, chunk + 1) == null, "Rotate must never load traversal chunks");
        assertUnchanged(helper, fixture);
        helper.succeed();
    }

    private static void replacedTarget(GameTestHelper helper) {
        var fixture = fixture(helper);
        helper.getLevel().setBlock(fixture.target, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
        RotateMessage.pressAction(fixture.player, 0, 0);
        helper.assertTrue(helper.getLevel().getBlockState(fixture.target).is(Blocks.STONE), "Handling must use the current target state");
        assertDecoy(helper, fixture);
        helper.succeed();
    }

    private static Fixture fixture(GameTestHelper helper) {
        var origin = helper.absolutePos(new BlockPos(3, 0, 6));
        // GameTest chooses a random million-block Z. Keep the historical decoy's
        // Y=hitZ inside build bounds so that it is an actual vulnerable sink.
        var target = new BlockPos(origin.getX(), 80, 20 + Math.floorMod(origin.getZ(), 200));
        var decoy = new BlockPos(target.getX(), target.getZ(), 0);
        var player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "rotate-test"));
        player.setGameMode(GameType.SURVIVAL);
        player.setPos(target.getX() + 0.5, target.getY() - 1, target.getZ() - 2.5);
        player.setYRot(0);
        player.setXRot(0);
        for (int dz = -8; dz <= 1; dz++) {
            helper.getLevel().setBlock(target.offset(0, 0, dz), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
        }
        var state = Ssc14ModBlocks.MACHINE_FRAME.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH);
        helper.getLevel().setBlock(target, state, Block.UPDATE_ALL);
        helper.getLevel().setBlock(decoy, state, Block.UPDATE_ALL);
        helper.assertTrue(helper.getLevel().getBlockState(target) == state && helper.getLevel().getBlockState(decoy) == state,
                "Both the real target and historical decoy must be installed");
        return new Fixture(player, target, decoy);
    }

    private static void assertUnchanged(GameTestHelper helper, Fixture fixture) {
        helper.assertTrue(helper.getLevel().getBlockState(fixture.target).getValue(HorizontalDirectionalBlock.FACING) == Direction.NORTH,
                "Rejected rotation must preserve the actual target");
        assertDecoy(helper, fixture);
    }

    private static void assertDecoy(GameTestHelper helper, Fixture fixture) {
        helper.assertTrue(helper.getLevel().getBlockState(fixture.decoy).getValue(HorizontalDirectionalBlock.FACING) == Direction.NORTH,
                "The coordinate-confusion decoy must never mutate");
    }

    private record Fixture(FakePlayer player, BlockPos target, BlockPos decoy) { }
}
