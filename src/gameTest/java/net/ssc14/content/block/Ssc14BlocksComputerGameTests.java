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
import net.ssc14.content.block.machinery.ComputerBlock;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksComputerGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_computer_contract");

    private Ssc14BlocksComputerGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14BlocksComputerGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_computer_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        ComputerBlock block = (ComputerBlock) Ssc14Blocks.COMPUTER.get();
        helper.assertTrue(id("computer").equals(BuiltInRegistries.BLOCK.getKey(block)),
                "computer registry ID changed");
        BlockPos pos = helper.absolutePos(new BlockPos(2, 2, 2));
        var north = block.defaultBlockState().setValue(ComputerBlock.FACING, Direction.NORTH)
                .getShape(helper.getLevel(), pos);
        var east = block.defaultBlockState().setValue(ComputerBlock.FACING, Direction.EAST)
                .getShape(helper.getLevel(), pos);
        helper.assertTrue(north.max(Direction.Axis.Y) > 1.5,
                "computer monitor height changed");
        helper.assertTrue(!north.equals(east), "computer directional shapes collapsed");
        helper.assertTrue(block.defaultBlockState().getVisualShape(
                        helper.getLevel(), pos, net.minecraft.world.phys.shapes.CollisionContext.empty())
                        .isEmpty(),
                "computer visual occlusion shape changed");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
