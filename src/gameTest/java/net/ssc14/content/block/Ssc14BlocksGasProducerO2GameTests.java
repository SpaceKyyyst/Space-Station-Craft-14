package net.ssc14.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
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
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.atmosphere.GasProducerO2Block;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksGasProducerO2GameTests {
    private static final Identifier TEST_ID = id("compatibility_block_gas_producer_o2_contract");

    private Ssc14BlocksGasProducerO2GameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14BlocksGasProducerO2GameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_gas_producer_o2_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        GasProducerO2Block block =
                (GasProducerO2Block) Ssc14Blocks.GAS_PRODUCER_O_2.get();
        helper.assertTrue(id("gas_producer_o_2").equals(BuiltInRegistries.BLOCK.getKey(block)),
                "gas producer registry ID changed");
        BlockPos pos = helper.absolutePos(new BlockPos(2, 2, 2));
        var state = block.defaultBlockState();
        var shape = state.getShape(helper.getLevel(), pos);
        helper.assertTrue(shape.min(Direction.Axis.Y) == -0.5
                        && shape.max(Direction.Axis.Y) == 0.25,
                "gas producer shape changed");
        helper.assertTrue(block.getPermeability(state) == 1.0F
                        && state.propagatesSkylightDown()
                        && block.getLightDampening(state) == 0,
                "gas producer atmosphere or light contract changed");
        helper.assertTrue(state.getVisualShape(helper.getLevel(), pos,
                        net.minecraft.world.phys.shapes.CollisionContext.empty()).isEmpty(),
                "gas producer visual shape changed");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
