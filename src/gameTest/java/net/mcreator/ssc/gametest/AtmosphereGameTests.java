package net.mcreator.ssc.gametest;

import net.ssc14.content.atmosphere.runtime.AtmosphereManager;
import net.ssc14.common.atmosphere.model.GasType;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

/** Real server tests: scheduled block ticks and LevelTickEvent drive the production code. */
@EventBusSubscriber(modid = "ssc_14")
public final class AtmosphereGameTests {
    private static final String NAMESPACE = "ssc_14";
    private AtmosphereGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, helper -> {
            helper.register(id("atmosphere_producer_and_wall"), AtmosphereGameTests::producerAndWall);
            helper.register(id("atmosphere_cross_region"), AtmosphereGameTests::crossRegion);
            helper.register(id("atmosphere_reaction_tick"), AtmosphereGameTests::reactionTick);
        });
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("atmosphere_environment"));
        for (String name : new String[]{"atmosphere_producer_and_wall", "atmosphere_cross_region", "atmosphere_reaction_tick"}) {
            var data = new TestData<>(environment, id("atmosphere_test_room"), 200, 1, true);
            event.registerTest(id(name), new FunctionGameTestInstance(ResourceKey.create(Registries.TEST_FUNCTION, id(name)), data));
        }
    }

    private static void producerAndWall(GameTestHelper helper) {
        fillCorridor(helper, 4, 9);
        helper.setBlock(new BlockPos(5, 2, 2), Ssc14ModBlocks.GAS_PRODUCER_O_2.get());
        helper.setBlock(new BlockPos(6, 2, 2), Blocks.AIR);
        helper.setBlock(new BlockPos(8, 2, 2), Blocks.AIR);
        helper.runAfterDelay(20, () -> {
            helper.assertTrue(oxygen(helper, 6) > 0, "Producer oxygen must reach the open neighbor through scheduled server ticks");
            helper.assertTrue(oxygen(helper, 8) == 0, "An intact wall must seal the far cell");
            helper.destroyBlock(new BlockPos(7, 2, 2));
            helper.succeedWhen(() -> helper.assertTrue(oxygen(helper, 8) > 0,
                    "Breaking the wall must wake gas flow using the resulting block state"));
        });
    }

    private static void crossRegion(GameTestHelper helper) {
        int boundary = 2;
        while ((helper.absolutePos(new BlockPos(boundary, 2, 2)).getX() & 15) != 15) boundary++;
        fillCorridor(helper, boundary - 1, boundary + 3);
        for (int x = boundary; x <= boundary + 2; x++) helper.setBlock(new BlockPos(x, 2, 2), Blocks.AIR);
        var source = AtmosphereManager.get(helper.getLevel()).getCellAt(helper.absolutePos(new BlockPos(boundary, 2, 2)));
        source.setMoles(GasType.OXYGEN.ordinal(), 10);
        int farCell = boundary + 2;
        helper.succeedWhen(() -> helper.assertTrue(oxygen(helper, farCell) > 0,
                "A newly reached region must continue diffusing into its own next cell"));
    }

    private static void reactionTick(GameTestHelper helper) {
        fillCorridor(helper, 1, 3);
        helper.setBlock(new BlockPos(2, 2, 2), Blocks.AIR);
        var cell = AtmosphereManager.get(helper.getLevel()).getCellAt(helper.absolutePos(new BlockPos(2, 2, 2)));
        cell.setMoles(GasType.NITROUS_OXIDE.ordinal(), 1);
        cell.setTemperature(1000);
        helper.succeedWhen(() -> {
            helper.assertTrue(cell.getMoles(GasType.NITROUS_OXIDE.ordinal()) < 0.94f,
                    "Non-combustion reactions must continue across at least two normal server atmosphere steps");
            helper.assertTrue(cell.getMoles(GasType.OXYGEN.ordinal()) > 0, "Decay must produce oxygen");
        });
    }

    private static void fillCorridor(GameTestHelper helper, int fromX, int toX) {
        for (int x = fromX; x <= toX; x++) {
            for (int y = 1; y <= 3; y++) {
                for (int z = 1; z <= 3; z++) helper.setBlock(x, y, z, Blocks.STONE);
            }
        }
    }

    private static float oxygen(GameTestHelper helper, int x) {
        return AtmosphereManager.get(helper.getLevel()).getCellAt(helper.absolutePos(new BlockPos(x, 2, 2)))
                .getMoles(GasType.OXYGEN.ordinal());
    }

    private static Identifier id(String path) { return Identifier.fromNamespaceAndPath(NAMESPACE, path); }
}
