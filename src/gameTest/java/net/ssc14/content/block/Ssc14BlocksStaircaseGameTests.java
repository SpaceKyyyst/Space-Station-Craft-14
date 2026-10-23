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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.surface.StaircaseBlock;
import net.ssc14.content.block.surface.UpperStaircaseBlock;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksStaircaseGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_staircase_contract");
    private static final List<ExpectedBlock> EXPECTED = List.of(
            expected(Ssc14Blocks.STEEL_STAIRCASE, "steel_staircase", StaircaseBlock.class, false),
            expected(Ssc14Blocks.WHITE_STAIRCASE, "white_staircase", StaircaseBlock.class, false),
            expected(Ssc14Blocks.DARK_STAIRCASE, "dark_staircase", StaircaseBlock.class, false),
            expected(Ssc14Blocks.UP_STEEL_STAIRCASE, "up_steel_staircase", UpperStaircaseBlock.class, true),
            expected(Ssc14Blocks.UP_WHITE_STAIRCASE, "up_white_staircase", UpperStaircaseBlock.class, true),
            expected(Ssc14Blocks.UP_DARK_STAIRCASE, "up_dark_staircase", UpperStaircaseBlock.class, true));

    private Ssc14BlocksStaircaseGameTests() {
    }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID, Ssc14BlocksStaircaseGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_staircase_compatibility_environment"));
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
            helper.assertTrue(expected.implementation().isInstance(block),
                    expected.path() + " factory class changed to " + block.getClass().getName());
            helper.assertTrue(Float.compare(100.0f, block.defaultDestroyTime()) == 0,
                    expected.path() + " destroy time changed");
            helper.assertTrue(Float.compare(7.5f, block.getExplosionResistance()) == 0,
                    expected.path() + " explosion resistance changed");
            helper.assertTrue(block.asItem().getDefaultInstance().getMaxStackSize() == 1,
                    expected.path() + " item stack limit changed");
            helper.assertTrue(state.getValue(StaircaseBlock.FACING) == Direction.NORTH,
                    expected.path() + " default facing changed");
            helper.assertTrue(state.rotate(Rotation.CLOCKWISE_90)
                    .getValue(StaircaseBlock.FACING) == Direction.EAST,
                    expected.path() + " rotation changed");
            helper.assertTrue(state.mirror(Mirror.FRONT_BACK).mirror(Mirror.FRONT_BACK).equals(state),
                    expected.path() + " mirror round trip changed");

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState directed = state.setValue(StaircaseBlock.FACING, direction);
                helper.setBlock(relative, directed);
                VoxelShape actual = directed.getShape(helper.getLevel(), absolute);
                VoxelShape wanted = expected.upper() ? upperShape(direction) : lowerShape(direction);
                helper.assertTrue(!Shapes.joinIsNotEmpty(actual, wanted, BooleanOp.NOT_SAME),
                        expected.path() + " shape changed for " + direction);
                helper.assertTrue(directed.getVisualShape(
                        helper.getLevel(), absolute, CollisionContext.empty()).isEmpty(),
                        expected.path() + " visual shape changed");
                helper.assertTrue(!directed.isRedstoneConductor(helper.getLevel(), absolute),
                        expected.path() + " became a redstone conductor");
            }
        }
        helper.succeed();
    }

    private static VoxelShape lowerShape(Direction direction) {
        return switch (direction) {
            case NORTH -> Shapes.or(
                    Block.box(0, 2, -1, 16, 4, 4), Block.box(0, 6, 3, 16, 8, 8),
                    Block.box(0, 10, 7, 16, 12, 12), Block.box(0, 14, 11, 16, 16, 16),
                    Block.box(0, 0, 0, 16, 2, 4), Block.box(0, 0, 4, 16, 6, 8),
                    Block.box(0, 0, 8, 16, 10, 12), Block.box(0, 0, 12, 16, 14, 16));
            case EAST -> Shapes.or(
                    Block.box(12, 2, 0, 17, 4, 16), Block.box(8, 6, 0, 13, 8, 16),
                    Block.box(4, 10, 0, 9, 12, 16), Block.box(0, 14, 0, 5, 16, 16),
                    Block.box(12, 0, 0, 16, 2, 16), Block.box(8, 0, 0, 12, 6, 16),
                    Block.box(4, 0, 0, 8, 10, 16), Block.box(0, 0, 0, 4, 14, 16));
            case WEST -> Shapes.or(
                    Block.box(-1, 2, 0, 4, 4, 16), Block.box(3, 6, 0, 8, 8, 16),
                    Block.box(7, 10, 0, 12, 12, 16), Block.box(11, 14, 0, 16, 16, 16),
                    Block.box(0, 0, 0, 4, 2, 16), Block.box(4, 0, 0, 8, 6, 16),
                    Block.box(8, 0, 0, 12, 10, 16), Block.box(12, 0, 0, 16, 14, 16));
            default -> Shapes.or(
                    Block.box(0, 2, 12, 16, 4, 17), Block.box(0, 6, 8, 16, 8, 13),
                    Block.box(0, 10, 4, 16, 12, 9), Block.box(0, 14, 0, 16, 16, 5),
                    Block.box(0, 0, 12, 16, 2, 16), Block.box(0, 0, 8, 16, 6, 12),
                    Block.box(0, 0, 4, 16, 10, 8), Block.box(0, 0, 0, 16, 14, 4));
        };
    }

    private static VoxelShape upperShape(Direction direction) {
        return switch (direction) {
            case NORTH -> Shapes.or(
                    Block.box(0, 12, 0, 16, 16, 4), Block.box(0, 8, 4, 16, 16, 8),
                    Block.box(0, 4, 8, 16, 16, 12), Block.box(0, 0, 12, 16, 16, 16));
            case EAST -> Shapes.or(
                    Block.box(12, 12, 0, 16, 16, 16), Block.box(8, 8, 0, 12, 16, 16),
                    Block.box(4, 4, 0, 8, 16, 16), Block.box(0, 0, 0, 4, 16, 16));
            case WEST -> Shapes.or(
                    Block.box(0, 12, 0, 4, 16, 16), Block.box(4, 8, 0, 8, 16, 16),
                    Block.box(8, 4, 0, 12, 16, 16), Block.box(12, 0, 0, 16, 16, 16));
            default -> Shapes.or(
                    Block.box(0, 12, 12, 16, 16, 16), Block.box(0, 8, 8, 16, 16, 12),
                    Block.box(0, 4, 4, 16, 16, 8), Block.box(0, 0, 0, 16, 16, 4));
        };
    }

    private static ExpectedBlock expected(
            DeferredBlock<Block> holder, String path, Class<? extends Block> implementation, boolean upper) {
        return new ExpectedBlock(holder, path, implementation, upper);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedBlock(
            DeferredBlock<Block> holder,
            String path,
            Class<? extends Block> implementation,
            boolean upper) {
    }
}
