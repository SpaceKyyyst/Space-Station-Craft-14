package net.ssc14.content.block.lighting;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Shared directional geometry and placement contract for wall-mounted station lights. */
public abstract class WallMountedLightBlock extends Block {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    private final Function<BlockState, VoxelShape> shapes;

    protected WallMountedLightBlock(BlockBehaviour.Properties properties, Geometry geometry,
            float strength, int lightLevel) {
        super(configure(properties, strength, lightLevel));
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
        shapes = getShapeForEachState(state -> geometry.shape(state.getValue(FACING)));
    }

    private static BlockBehaviour.Properties configure(BlockBehaviour.Properties properties,
            float strength, int lightLevel) {
        BlockBehaviour.Properties configured = properties.sound(SoundType.GLASS)
                .strength(strength)
                .noCollision()
                .isRedstoneConductor((state, level, pos) -> false);
        return lightLevel == 0
                ? configured
                : configured.lightLevel(state -> lightLevel);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) {
        return shapes.apply(state);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        if (state == null) {
            return null;
        }
        Direction direction = context.getClickedFace().getAxis() == Direction.Axis.Y
                ? Direction.NORTH
                : context.getClickedFace();
        return state.setValue(FACING, direction);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    public enum Geometry {
        TUBE {
            @Override
            VoxelShape shape(Direction direction) {
                return switch (direction) {
                    case NORTH -> box(1, 13, 14, 15, 15, 16);
                    case EAST -> box(0, 13, 1, 2, 15, 15);
                    case WEST -> box(14, 13, 1, 16, 15, 15);
                    default -> box(1, 13, 0, 15, 15, 2);
                };
            }
        },
        TECHNICAL {
            @Override
            VoxelShape shape(Direction direction) {
                return switch (direction) {
                    case NORTH -> Shapes.or(
                            box(6.5, 10.5, 14, 9.5, 13.5, 16),
                            box(7, 11, 13, 9, 13, 14),
                            box(6, 10, 9, 10, 14, 13));
                    case EAST -> Shapes.or(
                            box(0, 10.5, 6.5, 2, 13.5, 9.5),
                            box(2, 11, 7, 3, 13, 9),
                            box(3, 10, 6, 7, 14, 10));
                    case WEST -> Shapes.or(
                            box(14, 10.5, 6.5, 16, 13.5, 9.5),
                            box(13, 11, 7, 14, 13, 9),
                            box(9, 10, 6, 13, 14, 10));
                    default -> Shapes.or(
                            box(6.5, 10.5, 0, 9.5, 13.5, 2),
                            box(7, 11, 2, 9, 13, 3),
                            box(6, 10, 3, 10, 14, 7));
                };
            }
        },
        EMERGENCY {
            @Override
            VoxelShape shape(Direction direction) {
                return switch (direction) {
                    case NORTH -> box(5, 14, 14, 11, 16, 16);
                    case EAST -> box(0, 14, 5, 2, 16, 11);
                    case WEST -> box(14, 14, 5, 16, 16, 11);
                    default -> box(5, 14, 0, 11, 16, 2);
                };
            }
        };

        abstract VoxelShape shape(Direction direction);
    }
}
