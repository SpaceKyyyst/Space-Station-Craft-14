package net.mcreator.ssc.gametest;

import net.mcreator.ssc.AtmosBlockRegistry;
import net.mcreator.ssc.block.BaseAirlockD1Block;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

/** The gas seal follows the same opening and closing frames as the physical door. */
@EventBusSubscriber(modid = "ssc_14")
public final class AirlockAtmosphereGameTests {
    private static final Identifier TEST_ID = Identifier.fromNamespaceAndPath("ssc_14", "airlock_gas_seal_frames");

    private AirlockAtmosphereGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, helper -> helper.register(TEST_ID,
                AirlockAtmosphereGameTests::gasSealMatchesCollision));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(Identifier.fromNamespaceAndPath("ssc_14", "airlock_gas_environment"));
        var data = new TestData<>(environment, Identifier.fromNamespaceAndPath("ssc_14", "atmosphere_test_room"),
                20, 1, true);
        event.registerTest(TEST_ID, new FunctionGameTestInstance(ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID), data));
    }

    private static void gasSealMatchesCollision(GameTestHelper helper) {
        BlockPos pos = helper.absolutePos(new BlockPos(2, 2, 2));
        var level = helper.getLevel();
        for (int frame : BaseAirlockD1Block.ARL_VARIAT.getPossibleValues()) {
            var state = Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState()
                    .setValue(BaseAirlockD1Block.ARL_VARIAT, frame);
            level.setBlock(pos, state, 3);
            boolean expectedOpen = frame == 11 || frame == 13 || frame == 14;
            helper.assertTrue(state.getCollisionShape(level, pos).isEmpty() == expectedOpen,
                    "Airlock collision must follow the passable frames; frame=" + frame);
            helper.assertTrue(AtmosBlockRegistry.getPermeability(level, pos) == (expectedOpen ? 1f : 0f),
                    "Airlock gas seal must follow collision, including closed animation frames 12 and 15; frame=" + frame);
        }
        helper.succeed();
    }
}
