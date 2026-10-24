package net.ssc14.content.block;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.wall.PlasteelWallBlock;
import net.ssc14.content.block.wall.WallDamageService;
import net.ssc14.content.entity.C4CrutchEntEntity;
import net.ssc14.content.entity.Ssc14EntityTypes;
import net.ssc14.integration.neoforge.server.ServerWorkScheduler;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksStationWallGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_station_wall_contract");
    private static final List<Expected> EXPECTED = List.of(
            new Expected("STEEL_WALL", "steel_wall", 30.0f, 12.0f, 30),
            new Expected("PLASTEEL_WALL", "plasteel_wall", 300.0f, 16.5f, 30),
            new Expected("SHUTTLE_WALL", "shuttle_wall", 300.0f, 16.5f, 1),
            new Expected("ARMORED_SHUTTLE_WALL", "armored_shuttle_wall", 600.0f, 18.0f, 1));

    private Ssc14BlocksStationWallGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID,
                        Ssc14BlocksStationWallGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_station_wall_compatibility_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 80, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        var level = helper.getLevel();
        BlockPos pos = helper.absolutePos(new BlockPos(1, 1, 1));

        for (Expected expected : EXPECTED) {
            Block block = get(expected.field());
            helper.assertTrue(id(expected.path()).equals(BuiltInRegistries.BLOCK.getKey(block)),
                    expected.path() + " registry ID changed");
            helper.assertTrue(Float.compare(expected.destroyTime(), block.defaultDestroyTime()) == 0
                            && Float.compare(expected.resistance(),
                                    block.getExplosionResistance()) == 0,
                    expected.path() + " strength changed");
            helper.assertTrue(block.defaultBlockState().getSoundType() == SoundType.NETHERITE_BLOCK,
                    expected.path() + " sound changed");
            ItemStack item = block.asItem().getDefaultInstance();
            helper.assertTrue(item.getMaxStackSize() == expected.stackSize()
                            && item.has(DataComponents.DAMAGE_RESISTANT),
                    expected.path() + " item properties changed");
        }

        var plasteel = (PlasteelWallBlock) get("PLASTEEL_WALL");
        helper.assertTrue(PlasteelWallBlock.BLOCKSTATE.getPossibleValues().size() == 9
                        && plasteel.defaultBlockState().getValue(PlasteelWallBlock.BLOCKSTATE) == 0,
                "plasteel wall blockstate contract changed");

        var survival = helper.makeMockPlayer(GameType.SURVIVAL);
        for (Expected expected : EXPECTED) {
            Block block = get(expected.field());
            level.setBlock(pos, block.defaultBlockState(), Block.UPDATE_ALL);
            block.onDestroyedByPlayer(block.defaultBlockState(), level, pos, survival,
                    ItemStack.EMPTY, false, Fluids.EMPTY.defaultFluidState());
            helper.assertTrue(level.getBlockState(pos).is(Ssc14Blocks.WALL_CARCASE.get()),
                    expected.path() + " did not leave a wall carcase for survival destruction");
        }

        var creative = helper.makeMockPlayer(GameType.CREATIVE);
        Block steel = get("STEEL_WALL");
        level.setBlock(pos, steel.defaultBlockState(), Block.UPDATE_ALL);
        steel.onDestroyedByPlayer(steel.defaultBlockState(), level, pos, creative,
                ItemStack.EMPTY, false, Fluids.EMPTY.defaultFluidState());
        helper.assertTrue(level.getBlockState(pos).isAir(),
                "creative destruction incorrectly left a wall carcase");

        verifyExplosion(helper, pos, steel, 1.125, true,
                state -> state.isAir(), "steel direct radius changed", () ->
            verifyExplosion(helper, pos, plasteel, 1.125, false,
                    state -> state.is(Ssc14Blocks.PLASTEEL_WALL_CARCASE.get()),
                    "plasteel direct radius changed", () ->
                verifyExplosion(helper, pos, plasteel, 0.0, false,
                        state -> state.isAir(), "plasteel direct C4 did not destroy wall", () ->
                    verifyExplosion(helper, pos, steel, 5.0, true,
                            state -> state.is(Ssc14Blocks.WALL_CARCASE.get()),
                            "C4 degradation radius changed", () ->
                        verifyExplosion(helper, pos, steel, 5.25, true,
                                state -> state.isAir(),
                                "C4 outside degradation radius did not destroy wall", () -> {
                                    helper.succeed();
                                })))));
    }

    private static void verifyExplosion(GameTestHelper helper, BlockPos pos, Block wall,
            double markerDistance, boolean steel, Predicate<BlockState> expected,
            String failureMessage, Runnable next) {
        var level = helper.getLevel();
        C4CrutchEntEntity c4 = Ssc14EntityTypes.C_4_CRUTCH_ENT.get().create(level,
                EntitySpawnReason.MOB_SUMMONED);
        helper.assertTrue(c4 != null, "could not create C4 marker entity");
        c4.setNoAi(true);
        c4.setNoGravity(true);
        c4.setPos(pos.getX() + 0.5 + markerDistance, pos.getY() + 0.5, pos.getZ() + 0.5);
        level.addFreshEntity(c4);
        level.setBlock(pos, wall.defaultBlockState(), Block.UPDATE_ALL);
        if (steel) {
            WallDamageService.scheduleSteelExplosion(level, pos.getX(), pos.getY(), pos.getZ());
        } else {
            WallDamageService.schedulePlasteelExplosion(level, pos.getX(), pos.getY(), pos.getZ());
        }
        helper.assertTrue(level.getBlockState(pos).is(wall),
                "wall damage ran synchronously instead of on the next tick");
        var observedNextTick = new AtomicReference<BlockState>();
        ServerWorkScheduler.schedule(level, 1, () -> {
            observedNextTick.set(level.getBlockState(pos));
            c4.discard();
        });
        helper.runAfterDelay(2, () -> {
            BlockState observed = observedNextTick.get();
            helper.assertTrue(observed != null && expected.test(observed),
                    failureMessage + "; observed " + observed);
            next.run();
        });
    }

    private static Block get(String field) {
        try {
            return (Block) ((DeferredBlock<?>) Ssc14Blocks.class.getField(field).get(null)).get();
        } catch (ReflectiveOperationException error) {
            throw new IllegalStateException(error);
        }
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record Expected(String field, String path, float destroyTime, float resistance,
            int stackSize) { }
}
