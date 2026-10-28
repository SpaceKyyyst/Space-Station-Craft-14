package net.mcreator.ssc.gametest;

import com.mojang.authlib.GameProfile;
import java.lang.reflect.Proxy;
import java.util.UUID;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.ssc14.content.medical.MedicalPlayerEffects;
import net.ssc14.content.rotation.network.RotatePayloadHandler;
import net.ssc14.integration.neoforge.rotation.network.RotateMessage;
import net.ssc14.content.rotation.RotateCommand;
import net.ssc14.content.rotation.RotationPolicy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.FullChunkStatus;
import net.minecraft.world.level.ChunkPos;
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
            "rotate_transport", "rotate_medical_thresholds", "rotate_boundary_update_loading", "rotate_multipart_rejected", "rotate_client_sync",
            "rotate_unapproved_tag_member"};
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
            registry.register(id(NAMES[6]), RotateGameTests::directCommand);
            registry.register(id(NAMES[7]), RotateGameTests::worldBorder);
            registry.register(id(NAMES[8]), RotateGameTests::unloaded);
            registry.register(id(NAMES[9]), RotateGameTests::replacedTarget);
            registry.register(id(NAMES[10]), RotateGameTests::loadedTraversal);
            registry.register(id(NAMES[11]), RotateGameTests::supportedStates);
            registry.register(id(NAMES[12]), RotateGameTests::unsupportedStates);
            registry.register(id(NAMES[13]), RotateGameTests::nonServerActor);
            registry.register(id(NAMES[14]), RotateGameTests::height);
            registry.register(id(NAMES[15]), RotateGameTests::transport);
            registry.register(id(NAMES[16]), RotateGameTests::medicalThresholds);
            registry.register(id(NAMES[17]), RotateGameTests::boundaryUpdateLoading);
            registry.register(id(NAMES[18]), RotateGameTests::multipartRejected);
            registry.register(id(NAMES[19]), RotateGameTests::clientSync);
            registry.register(id(NAMES[20]), RotateGameTests::unapprovedTagMember);
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
            RotatePayloadHandler.pressAction(fixture.player, 0, 0);
        } finally {
            observedPosition = null;
        }
        helper.assertTrue(helper.getLevel().getBlockState(fixture.target).getValue(HorizontalDirectionalBlock.FACING) == Direction.EAST,
                "The actual nonzero-Z hit must rotate clockwise exactly once");
        helper.assertTrue(neighborNotifications == 0, "State-only rotation must not dispatch potentially loading neighbor callbacks");
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
        RotatePayloadHandler.pressAction(fixture.player, 0, 0);
        assertUnchanged(helper, fixture);
        helper.succeed();
    }

    private static void invalidIntent(GameTestHelper helper) {
        var fixture = fixture(helper);
        for (int type : new int[] {-1, 1, Integer.MIN_VALUE, Integer.MAX_VALUE}) {
            RotatePayloadHandler.pressAction(fixture.player, type, Integer.MIN_VALUE);
            assertUnchanged(helper, fixture);
        }
        helper.succeed();
    }

    private static void noTarget(GameTestHelper helper, boolean distant) {
        var fixture = fixture(helper);
        if (distant) fixture.player.setPos(fixture.player.getX(), fixture.player.getY(), fixture.target.getZ() - 8);
        else helper.getLevel().setBlock(fixture.target, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
        RotatePayloadHandler.pressAction(fixture.player, 0, 0);
        if (distant) assertUnchanged(helper, fixture);
        assertDecoy(helper, fixture);
        helper.succeed();
    }

    private static void directCommand(GameTestHelper helper) {
        var fixture = fixture(helper);
        fixture.player.setGameMode(GameType.ADVENTURE);
        helper.assertTrue(!RotateCommand.execute(fixture.player, 0),
                "Adventure players must not rotate blocks");
        assertUnchanged(helper, fixture);
        fixture.player.setGameMode(GameType.SURVIVAL);
        helper.assertTrue(RotateCommand.execute(fixture.player, 0),
                "A survival player must reach the canonical command");
        helper.assertTrue(helper.getLevel().getBlockState(fixture.target).getValue(HorizontalDirectionalBlock.FACING) == Direction.EAST,
                "A legitimate direct command must rotate exactly once");
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
            RotatePayloadHandler.pressAction(fixture.player, 0, 0);
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
        RotatePayloadHandler.pressAction(fixture.player, 0, 0);
        helper.assertTrue(helper.getLevel().getChunkSource().getChunkNow(chunkX, chunkZ + 1) == null,
                "A ray starting in a loaded chunk must stop before loading the next chunk");
        assertUnchanged(helper, fixture);
        helper.succeed();
    }

    private static void boundaryUpdateLoading(GameTestHelper helper) {
        var level = helper.getLevel();
        int chunkX = helper.absolutePos(BlockPos.ZERO).getX() >> 4;
        int chunkZ = 3000;
        level.getChunk(chunkX, chunkZ);
        var target = new BlockPos(chunkX * 16 + 15, 80, chunkZ * 16 + 8);
        var original = Ssc14ModBlocks.MACHINE_FRAME.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH);
        // Fixture installation must not itself request the unavailable east chunk.
        level.setBlock(target, original, Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);
        var player = new FakePlayer(level, new GameProfile(UUID.randomUUID(), "rotate-boundary"));
        player.setGameMode(GameType.SURVIVAL);
        player.setPos(target.getX() + 0.5, target.getY() - 1, target.getZ() - 2.5);
        player.setYRot(0);
        player.setYHeadRot(0);
        player.setXRot(0);
        helper.assertTrue(level.getChunkSource().getChunkNow(chunkX + 1, chunkZ) == null,
                "The adjacent update chunk must start unavailable while the entire ray chunk is loaded");
        RotatePayloadHandler.pressAction(player, 0, 0);
        helper.assertTrue(level.getChunkSource().getChunkNow(chunkX + 1, chunkZ) == null,
                "Successful-target validation must not let neighbor notifications load an unavailable chunk");
        helper.assertTrue(level.getBlockState(target) == original.setValue(HorizontalDirectionalBlock.FACING, Direction.EAST),
                "A supported state-only rotation must still succeed with an unavailable neighboring chunk");
        helper.succeed();
    }

    private static void multipartRejected(GameTestHelper helper) {
        var fixture = fixture(helper);
        var level = helper.getLevel();
        for (var block : new Block[] {Ssc14ModBlocks.CLOSET.get(), Ssc14ModBlocks.CLOSET_SECURE.get()}) {
            var original = block.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH);
            level.setBlock(fixture.target.above(), Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);
            level.setBlock(fixture.target, original, Block.UPDATE_ALL);
            var upper = level.getBlockState(fixture.target.above());
            helper.assertTrue(upper.is(Ssc14ModBlocks.UP_CLOSET_PLUG.get()), "Normal closet placement must install its real upper plug");
            RotatePayloadHandler.pressAction(fixture.player, 0, 0);
            helper.assertTrue(level.getBlockState(fixture.target) == original, "Multipart base must be rejected before its facing changes");
            helper.assertTrue(level.getBlockState(fixture.target.above()) == upper, "Rejected rotation must not replay placement or change the paired plug");
        }
        assertDecoy(helper, fixture);
        helper.succeed();
    }

    private static void clientSync(GameTestHelper helper) {
        var level = helper.getLevel();
        // GameTest waits for the structure's chunk to become entity-ticking. Unlike
        // remote security fixtures, this exercises the real client update queue.
        var target = helper.absolutePos(new BlockPos(1, 50, 1));
        var chunk = level.getChunkAt(target);
        helper.assertTrue(chunk.getFullStatus().isOrAfter(FullChunkStatus.BLOCK_TICKING), "Sync fixture must be in a ticking chunk");
        var original = Ssc14ModBlocks.MACHINE_FRAME.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH);
        level.setBlock(target, original, Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);
        var player = new FakePlayer(level, new GameProfile(UUID.randomUUID(), "rotate-sync"));
        player.setGameMode(GameType.SURVIVAL);
        player.setPos(target.getX() + 0.5, target.getY() - 1, target.getZ() - 2.5);
        player.setYRot(0);
        player.setYHeadRot(0);
        player.setXRot(0);
        try {
            var lookup = ChunkMap.class.getDeclaredMethod("getVisibleChunkIfPresent", long.class);
            lookup.setAccessible(true);
            var holder = (ChunkHolder) lookup.invoke(level.getChunkSource().chunkMap, ChunkPos.pack(target.getX() >> 4, target.getZ() >> 4));
            holder.broadcastChanges(chunk); // Drain fixture writes through the native broadcaster.
            RotatePayloadHandler.pressAction(player, 0, 0);
            var changes = ChunkHolder.class.getDeclaredField("changedBlocksPerSection");
            changes.setAccessible(true);
            var sections = (it.unimi.dsi.fastutil.shorts.ShortSet[]) changes.get(holder);
            var changed = sections[level.getSectionIndex(target.getY())];
            helper.assertTrue(changed != null && changed.size() == 1 && changed.contains(SectionPos.sectionRelativePos(target)),
                    "Exactly the rotated target must enter the real pending client block-update queue");
            var packet = new ClientboundBlockUpdatePacket(level, target);
            helper.assertTrue(packet.getPos().equals(target)
                    && packet.getBlockState() == original.setValue(HorizontalDirectionalBlock.FACING, Direction.EAST),
                    "The broadcaster's block-update payload must expose the new facing at the exact target");
        } catch (ReflectiveOperationException error) {
            throw new AssertionError("Pinned-platform client update observation failed", error);
        }
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
        for (var block : new Block[] {Ssc14ModBlocks.MACHINE_FRAME.get(), Ssc14ModBlocks.COMPUTER.get(),
                Ssc14ModBlocks.OFICE_BLACK_CHAIR.get(), Ssc14ModBlocks.OFICE_WHITE_CHAIR.get(), Ssc14ModBlocks.WOOD_CHAIR.get(),
                Ssc14ModBlocks.STEEL_CHAIR.get(), Ssc14ModBlocks.BAR_CHAIR.get(), Ssc14ModBlocks.CONSOLE_OF_ID.get(),
                Ssc14ModBlocks.PODSTATION.get(), Ssc14ModBlocks.SMES.get(), Ssc14ModBlocks.PORTABLE_SHEATER.get()}) {
            var original = block.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH);
            helper.getLevel().setBlock(fixture.target, original, Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE | Block.UPDATE_SKIP_ON_PLACE);
            // Use a broad component inside the owning voxel, rather than a thin
            // chair leg or an over-height backrest at the traversal boundary.
            var component = original.getShape(helper.getLevel(), fixture.target).toAabbs().stream().filter(box -> box.minY < 1)
                    .max(java.util.Comparator.comparingDouble(box -> box.getXsize() * box.getZsize()))
                    .orElseThrow(() -> new AssertionError("No outline in the owning voxel: " + block));
            var center = component.getCenter();
            var aim = new net.minecraft.world.phys.Vec3(center.x, (Math.max(0, component.minY) + Math.min(1, component.maxY)) / 2, center.z);
            fixture.player.setPos(fixture.target.getX() + aim.x, fixture.target.getY() + aim.y - fixture.player.getEyeHeight(),
                    fixture.target.getZ() - 2.5);
            var eye = fixture.player.getEyePosition();
            var end = eye.add(fixture.player.getViewVector(1).scale(5));
            var outlineHit = original.getShape(helper.getLevel(), fixture.target).clip(eye, end, fixture.target);
            helper.assertTrue(outlineHit != null, "Supported fixture ray must intersect its outline: " + block
                    + ", target=" + fixture.target + ", eye=" + eye + ", aim=" + aim + ", end=" + end
                    + ", headYaw=" + fixture.player.getYHeadRot() + ", bodyYaw=" + fixture.player.getYRot());
            helper.assertTrue(RotationPolicy.rotate(original).isPresent(), "The current tag member must be eligible: " + block);
            helper.assertTrue(helper.getLevel().getBlockState(fixture.target) == original, "Supported fixture state must be installed: " + block);
            helper.assertTrue(RotateCommand.execute(fixture.player, 0), "Every supported current tag member must rotate: " + block
                    + ", target=" + fixture.target + ", eye=" + eye + ", hit=" + outlineHit);
            helper.assertTrue(helper.getLevel().getBlockState(fixture.target)
                    == original.setValue(HorizontalDirectionalBlock.FACING, Direction.EAST), "State-only rotation must preserve all other properties");
        }
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

    private static void unapprovedTagMember(GameTestHelper helper) {
        var fixture = fixture(helper);
        var state = Blocks.FURNACE.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH);
        helper.getLevel().setBlock(fixture.target, state, Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);
        helper.assertTrue(state.is(net.minecraft.tags.BlockTags.create(Identifier.parse("ssc14:rotatable"))),
                "The fixture must really be tagged by the test datapack");
        helper.assertTrue(!RotateCommand.execute(fixture.player, 0), "A tag addition must not authorize an unaudited block");
        helper.assertTrue(helper.getLevel().getBlockState(fixture.target) == state, "An unaudited tagged block must remain unchanged");
        assertDecoy(helper, fixture);
        helper.succeed();
    }

    private static void nonServerActor(GameTestHelper helper) {
        var fixture = fixture(helper);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setPos(fixture.player.position());
        player.setYRot(0);
        player.setYHeadRot(0);
        player.setXRot(0);
        RotatePayloadHandler.pressAction(player, 0, 0);
        RotatePayloadHandler.pressAction(null, 0, 0);
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
        RotatePayloadHandler.handle(message, context(fixture.player, PacketFlow.CLIENTBOUND));
        RotatePayloadHandler.handle(message, context(null, PacketFlow.SERVERBOUND));
        RotatePayloadHandler.handle(message, context(helper.makeMockPlayer(GameType.SURVIVAL), PacketFlow.SERVERBOUND));
        for (int type : new int[] {-1, 1, Integer.MIN_VALUE, Integer.MAX_VALUE}) {
            RotatePayloadHandler.handle(new RotateMessage(type, Integer.MIN_VALUE), context(fixture.player, PacketFlow.SERVERBOUND));
        }
        assertUnchanged(helper, fixture);
        RotatePayloadHandler.handle(message, context(fixture.player, PacketFlow.SERVERBOUND));
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
        RotatePayloadHandler.pressAction(fixture.player, 0, 0);
        helper.assertTrue(helper.getLevel().getChunkSource().getChunkNow(chunk, chunk) == null, "Rotate must never load the eye chunk");
        helper.assertTrue(helper.getLevel().getChunkSource().getChunkNow(chunk, chunk + 1) == null, "Rotate must never load traversal chunks");
        assertUnchanged(helper, fixture);
        helper.succeed();
    }

    private static void replacedTarget(GameTestHelper helper) {
        var fixture = fixture(helper);
        helper.getLevel().setBlock(fixture.target, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
        RotatePayloadHandler.pressAction(fixture.player, 0, 0);
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
        // LivingEntity randomizes head yaw separately; ray tracing uses that yaw.
        player.setYHeadRot(0);
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
