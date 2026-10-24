package net.ssc14.content.block.window;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

abstract class AbstractDirectedWindowBlock extends AbstractWindowBlock {
    public static final EnumProperty<Direction> FACING = DirectionalBlock.FACING;
    private final Function<BlockState, VoxelShape> shapes = getShapeForEachState(
            state -> shape(state.getValue(FACING)));

    protected AbstractDirectedWindowBlock(BlockBehaviour.Properties properties, float strength, float resistance) {
        super(properties, strength, resistance);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    protected BlockState placementState(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state == null ? null : state.setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return shapes.apply(state);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player,
            BlockHitResult hit) {
        return WindowKnockService.knock(level, pos.getX(), pos.getY(), pos.getZ(), player);
    }

    private static VoxelShape shape(Direction facing) {
        return switch (facing) {
            case NORTH -> Block.box(0, 0, 13, 16, 16, 16);
            case EAST -> Block.box(0, 0, 0, 3, 16, 16);
            case WEST -> Block.box(13, 0, 0, 16, 16, 16);
            case UP -> Block.box(0, 0, 0, 16, 3, 16);
            case DOWN -> Block.box(0, 13, 0, 16, 16, 16);
            default -> Block.box(0, 0, 0, 16, 16, 3);
        };
    }
}
