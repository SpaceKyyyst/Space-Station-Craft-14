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
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.commerce.VendingUpperPlugBlock;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksVendingPlugGameTests {
    private static final Identifier TEST_ID =
            id("compatibility_block_vending_plug_contract");

    private Ssc14BlocksVendingPlugGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(TEST_ID,
                Ssc14BlocksVendingPlugGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_vending_plug_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        VendingUpperPlugBlock plug =
                (VendingUpperPlugBlock) Ssc14Blocks.VENDING_KOSTIL.get();
        helper.assertTrue(id("vending_kostil").equals(
                        BuiltInRegistries.BLOCK.getKey(plug)),
                "vending upper plug registry ID changed");

        BlockPos vendingRelativePos = new BlockPos(2, 2, 2);
        BlockPos vendingAbsolutePos = helper.absolutePos(vendingRelativePos);
        BlockPos plugAbsolutePos = vendingAbsolutePos.above();
        helper.setBlock(vendingRelativePos, Ssc14Blocks.VENDING_AUT_1.get());
        helper.assertTrue(helper.getLevel().getBlockState(plugAbsolutePos).is(plug),
                "vending machine did not create its canonical upper plug");

        var actualShape = helper.getLevel().getBlockState(plugAbsolutePos)
                .getShape(helper.getLevel(), plugAbsolutePos);
        helper.assertTrue(!Shapes.joinIsNotEmpty(
                        Block.box(4, 0, 4, 12, 14, 12), actualShape,
                        BooleanOp.NOT_SAME),
                "vending upper plug shape changed");

        helper.getLevel().setBlock(vendingAbsolutePos, Blocks.AIR.defaultBlockState(),
                Block.UPDATE_ALL);
        helper.assertTrue(helper.getLevel().getBlockState(plugAbsolutePos).isAir(),
                "orphan vending upper plug was not removed");

        BlockPos leverPos = helper.absolutePos(new BlockPos(4, 2, 2));
        helper.getLevel().setBlock(leverPos, Blocks.LEVER.defaultBlockState(),
                Block.UPDATE_CLIENTS);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        BlockPos upperPos = leverPos.above();
        plug.useWithoutItem(plug.defaultBlockState(), helper.getLevel(), upperPos, player,
                BlockHitResult.miss(Vec3.atCenterOf(upperPos), Direction.UP, upperPos));
        helper.assertTrue(helper.getLevel().getBlockState(leverPos)
                        .getValue(LeverBlock.POWERED),
                "vending upper plug did not delegate interaction to the lower block");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
