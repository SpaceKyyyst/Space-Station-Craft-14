package net.ssc14.content.block;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.decoration.DirectionalBannerBlock;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksBannerGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_banner_contract");
    private static final List<ExpectedBlock> EXPECTED = List.of(
            expected(Ssc14Blocks.BANNER_NT, "banner_nt"),
            expected(Ssc14Blocks.BANNER_MED, "banner_med"),
            expected(Ssc14Blocks.BANNER_RN_D, "banner_rn_d"),
            expected(Ssc14Blocks.BANNER_SEC, "banner_sec"),
            expected(Ssc14Blocks.BANNER_CARGO, "banner_cargo"),
            expected(Ssc14Blocks.BANNER_ENG, "banner_eng"),
            expected(Ssc14Blocks.BANNER_SYND, "banner_synd"));

    private Ssc14BlocksBannerGameTests() {
    }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID, Ssc14BlocksBannerGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_banner_compatibility_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 20, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        BlockPos relative = new BlockPos(1, 2, 1);
        BlockPos absolute = helper.absolutePos(relative);
        for (ExpectedBlock expected : EXPECTED) {
            Block block = expected.holder().get();
            BlockState state = block.defaultBlockState();
            helper.assertTrue(id(expected.path()).equals(BuiltInRegistries.BLOCK.getKey(block)),
                    expected.path() + " registry ID changed");
            helper.assertTrue(block instanceof DirectionalBannerBlock,
                    expected.path() + " is not canonical");
            helper.assertTrue(Float.compare(10.0f, block.defaultDestroyTime()) == 0,
                    expected.path() + " destroy time changed");
            helper.assertTrue(Float.compare(10.0f, block.getExplosionResistance()) == 0,
                    expected.path() + " explosion resistance changed");
            helper.assertTrue(block.defaultBlockState().getSoundType() == SoundType.STEM,
                    expected.path() + " sound changed");
            helper.assertTrue(block.asItem().getDefaultInstance().getMaxStackSize() == 1,
                    expected.path() + " item stack limit changed");
            helper.assertTrue(state.getValue(DirectionalBannerBlock.FACING) == Direction.NORTH,
                    expected.path() + " default facing changed");
            helper.assertTrue(state.rotate(Rotation.CLOCKWISE_90)
                    .getValue(DirectionalBannerBlock.FACING) == Direction.EAST,
                    expected.path() + " rotation changed");
            helper.assertTrue(state.setValue(DirectionalBannerBlock.FACING, Direction.EAST)
                    .mirror(Mirror.FRONT_BACK).getValue(DirectionalBannerBlock.FACING) == Direction.WEST,
                    expected.path() + " mirror changed");
            helper.setBlock(relative, state);
            helper.assertTrue(!Shapes.joinIsNotEmpty(
                            state.getShape(helper.getLevel(), absolute),
                            Block.box(1, 0, 1, 15, 1, 15), BooleanOp.NOT_SAME),
                    expected.path() + " shape changed");
            helper.assertTrue(state.getVisualShape(
                    helper.getLevel(), absolute, CollisionContext.empty()).isEmpty(),
                    expected.path() + " visual shape changed");
            helper.assertTrue(state.propagatesSkylightDown(), expected.path() + " blocks skylight");
            helper.assertTrue(((DirectionalBannerBlock) block).getLightDampening(state) == 0,
                    expected.path() + " light dampening changed");
            helper.assertTrue(!state.isRedstoneConductor(helper.getLevel(), absolute),
                    expected.path() + " became a redstone conductor");
        }
        helper.succeed();
    }

    private static ExpectedBlock expected(DeferredBlock<Block> holder, String path) {
        return new ExpectedBlock(holder, path);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedBlock(DeferredBlock<Block> holder, String path) {
    }
}
