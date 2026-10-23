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
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.decoration.DirectionalFenceBlock;
import net.ssc14.content.block.decoration.FenceCornerBlock;
import net.ssc14.content.block.decoration.FenceLineBlock;
import net.ssc14.content.block.decoration.FencePointBlock;
import net.ssc14.content.block.decoration.FenceThreeWayBlock;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksFenceGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_fence_contract");
    private static final List<ExpectedBlock> EXPECTED = List.of(
            expected(Ssc14Blocks.FENCE_POINT, "fence_point", FencePointBlock.class, Kind.POINT),
            expected(Ssc14Blocks.FENCE_LINE, "fence_line", FenceLineBlock.class, Kind.LINE),
            expected(Ssc14Blocks.FENCE_G, "fence_g", FenceCornerBlock.class, Kind.CORNER),
            expected(Ssc14Blocks.FENCE_P, "fence_p", FenceThreeWayBlock.class, Kind.THREE_WAY));

    private Ssc14BlocksFenceGameTests() {
    }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID, Ssc14BlocksFenceGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_fence_compatibility_environment"));
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
                    expected.path() + " canonical implementation changed");
            helper.assertTrue(Float.compare(10.0f, block.defaultDestroyTime()) == 0,
                    expected.path() + " destroy time changed");
            helper.assertTrue(Float.compare(4.0f, block.getExplosionResistance()) == 0,
                    expected.path() + " explosion resistance changed");
            helper.assertTrue(state.getSoundType() == SoundType.CHAIN, expected.path() + " sound changed");
            helper.assertTrue(block.asItem().getDefaultInstance().getMaxStackSize() == 1,
                    expected.path() + " item stack limit changed");
            helper.assertTrue(state.getValue(DirectionalFenceBlock.FACING) == Direction.NORTH,
                    expected.path() + " default facing changed");
            helper.assertTrue(state.rotate(Rotation.CLOCKWISE_90)
                    .getValue(DirectionalFenceBlock.FACING) == Direction.EAST,
                    expected.path() + " rotation changed");
            helper.assertTrue(state.setValue(DirectionalFenceBlock.FACING, Direction.EAST)
                    .mirror(Mirror.FRONT_BACK).getValue(DirectionalFenceBlock.FACING) == Direction.WEST,
                    expected.path() + " mirror changed");
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState directed = state.setValue(DirectionalFenceBlock.FACING, direction);
                helper.setBlock(relative, directed);
                helper.assertTrue(!Shapes.joinIsNotEmpty(
                                directed.getShape(helper.getLevel(), absolute),
                                shape(expected.kind(), direction), BooleanOp.NOT_SAME),
                        expected.path() + " shape changed for " + direction);
                helper.assertTrue(directed.getVisualShape(
                        helper.getLevel(), absolute, CollisionContext.empty()).isEmpty(),
                        expected.path() + " visual shape changed");
                helper.assertTrue(directed.propagatesSkylightDown(), expected.path() + " blocks skylight");
                helper.assertTrue(((DirectionalFenceBlock) block).getLightDampening(directed) == 0,
                        expected.path() + " light dampening changed");
                helper.assertTrue(!directed.isRedstoneConductor(helper.getLevel(), absolute),
                        expected.path() + " became a redstone conductor");
            }
        }
        helper.succeed();
    }

    private static VoxelShape shape(Kind kind, Direction direction) {
        VoxelShape north = Block.box(0, 0, 14, 16, 14, 16);
        VoxelShape south = Block.box(0, 0, 0, 16, 14, 2);
        VoxelShape east = Block.box(0, 0, 0, 2, 14, 16);
        VoxelShape west = Block.box(14, 0, 0, 16, 14, 16);
        return switch (kind) {
            case POINT -> switch (direction) {
                case NORTH -> Block.box(0, 0, 14, 2, 14, 16);
                case EAST -> Block.box(0, 0, 0, 2, 14, 2);
                case WEST -> Block.box(14, 0, 14, 16, 14, 16);
                default -> Block.box(14, 0, 0, 16, 14, 2);
            };
            case LINE -> switch (direction) {
                case NORTH -> north;
                case EAST -> east;
                case WEST -> west;
                default -> south;
            };
            case CORNER -> switch (direction) {
                case NORTH -> Shapes.or(north, east);
                case EAST -> Shapes.or(east, south);
                case WEST -> Shapes.or(west, north);
                default -> Shapes.or(south, west);
            };
            case THREE_WAY -> switch (direction) {
                case NORTH -> Shapes.or(north, east, west);
                case EAST -> Shapes.or(east, south, north);
                case WEST -> Shapes.or(west, north, south);
                default -> Shapes.or(south, west, east);
            };
        };
    }

    private static ExpectedBlock expected(
            DeferredBlock<Block> holder, String path, Class<? extends Block> implementation, Kind kind) {
        return new ExpectedBlock(holder, path, implementation, kind);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private enum Kind { POINT, LINE, CORNER, THREE_WAY }

    private record ExpectedBlock(
            DeferredBlock<Block> holder, String path, Class<? extends Block> implementation, Kind kind) {
    }
}
