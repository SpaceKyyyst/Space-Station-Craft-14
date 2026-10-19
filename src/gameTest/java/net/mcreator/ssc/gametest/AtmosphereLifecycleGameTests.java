package net.mcreator.ssc.gametest;

import net.mcreator.ssc.AtmosphereManager;
import net.mcreator.ssc.GasType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

/** Exercises the production chunk callbacks without unloading the GameTest fixture itself. */
@EventBusSubscriber(modid = "ssc_14")
public final class AtmosphereLifecycleGameTests {
    private static final Identifier TEST_ID = Identifier.fromNamespaceAndPath("ssc_14", "atmosphere_chunk_resume");
    private static final int INITIALIZATION_TICKS = 20;
    private static final int SUSPENSION_TICKS = 8;
    private static final float INITIAL_NITROUS_OXIDE = 10f;
    private static final float TWO_REACTION_STEPS_THRESHOLD = 0.06f;

    private AtmosphereLifecycleGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, helper -> helper.register(TEST_ID,
                AtmosphereLifecycleGameTests::chunkResumeContinuesReaction));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(Identifier.fromNamespaceAndPath("ssc_14", "atmosphere_lifecycle_environment"));
        var data = new TestData<>(environment, Identifier.fromNamespaceAndPath("ssc_14", "atmosphere_test_room"),
                200, 1, true);
        event.registerTest(TEST_ID, new FunctionGameTestInstance(ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID), data));
    }

    private static void chunkResumeContinuesReaction(GameTestHelper helper) {
        BlockPos relative = new BlockPos(20, 2, 2);
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) helper.setBlock(relative.offset(x, y, z), Blocks.STONE);
            }
        }
        helper.setBlock(relative, Blocks.AIR);
        // Let the initial block notifications settle before suspending this section's work.
        helper.runAfterDelay(INITIALIZATION_TICKS, () -> {
            var manager = AtmosphereManager.get(helper.getLevel());
            BlockPos absolute = helper.absolutePos(relative);
            var chunk = ChunkPos.containing(absolute);
            var cell = manager.getCellAt(absolute);
            for (GasType gas : GasType.values()) cell.setMoles(gas.ordinal(), 0);
            cell.setMoles(GasType.NITROUS_OXIDE.ordinal(), INITIAL_NITROUS_OXIDE);
            cell.setTemperature(1000);
            manager.onChunkUnloaded(chunk);
            helper.runAfterDelay(SUSPENSION_TICKS, () -> {
                helper.assertTrue(cell.getMoles(GasType.NITROUS_OXIDE.ordinal()) == INITIAL_NITROUS_OXIDE,
                        "Suspended chunk work must preserve gas without running reactions");
                manager.onChunkLoaded(chunk);
                helper.assertTrue(cell.getMoles(GasType.NITROUS_OXIDE.ordinal()) == INITIAL_NITROUS_OXIDE,
                        "Chunk load must defer simulation until a normal server tick");
                helper.succeedWhen(() -> {
                    helper.assertTrue(cell.getMoles(GasType.NITROUS_OXIDE.ordinal())
                                    < INITIAL_NITROUS_OXIDE - TWO_REACTION_STEPS_THRESHOLD,
                            "Reloaded chunk work must continue reacting across normal server ticks");
                    helper.assertTrue(cell.getMoles(GasType.OXYGEN.ordinal()) > 0,
                            "Resumed nitrous oxide decay must produce oxygen");
                });
            });
        });
    }
}
