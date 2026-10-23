package net.ssc14.content.block.surface;

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

abstract class DirectionalStaircaseBlock extends Block {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    private final Function<BlockState, VoxelShape> shapes;

    DirectionalStaircaseBlock(
            BlockBehaviour.Properties properties, Function<Direction, VoxelShape> shapeProvider) {
        super(properties.sound(SoundType.COPPER)
                .strength(100.0f, 7.5f)
                .noOcclusion()
                .isRedstoneConductor((state, world, pos) -> false));
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
        shapes = getShapeForEachState(state -> shapeProvider.apply(state.getValue(FACING)));
    }

    @Override
    public VoxelShape getShape(
            BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return shapes.apply(state);
    }

    @Override
    public VoxelShape getVisualShape(
            BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
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
        return state == null ? null : state.setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}
