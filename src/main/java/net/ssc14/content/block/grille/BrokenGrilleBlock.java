package net.ssc14.content.block.grille;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BrokenGrilleBlock extends Block {
    public static final EnumProperty<Direction> FACING = DirectionalBlock.FACING;
    private final Function<BlockState, VoxelShape> shapes = makeShapes();

    public BrokenGrilleBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.CHAIN).strength(2.0f).noCollision()
                .isRedstoneConductor((state, level, pos) -> false));
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    private Function<BlockState, VoxelShape> makeShapes() {
        return getShapeForEachState(state -> switch (state.getValue(FACING)) {
            case NORTH -> hollowFrame(box(0, 0, 11, 16, 16, 16), box(2, 2, 0, 14, 14, 16));
            case EAST -> hollowFrame(box(0, 0, 0, 5, 16, 16), box(0, 2, 2, 16, 14, 14));
            case WEST -> hollowFrame(box(11, 0, 0, 16, 16, 16), box(0, 2, 2, 16, 14, 14));
            case UP -> hollowFrame(box(0, 0, 0, 16, 5, 16), box(2, 0, 2, 14, 16, 14));
            case DOWN -> hollowFrame(box(0, 11, 0, 16, 16, 16), box(2, 0, 2, 14, 16, 14));
            case SOUTH -> hollowFrame(box(0, 0, 0, 16, 16, 5), box(2, 2, 0, 14, 14, 16));
        });
    }

    private static VoxelShape hollowFrame(VoxelShape outside, VoxelShape inside) {
        return Shapes.join(outside, inside, BooleanOp.ONLY_FIRST);
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
        return state == null ? null : state.setValue(FACING, context.getNearestLookingDirection().getOpposite());
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
