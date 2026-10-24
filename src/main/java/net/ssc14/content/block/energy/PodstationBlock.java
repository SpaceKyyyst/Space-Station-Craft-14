package net.ssc14.content.block.energy;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Directional medium/high-voltage power substation. */
public class PodstationBlock extends Block implements EntityBlock {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    private final Function<BlockState, VoxelShape> shapes = makeShapes();

    public PodstationBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.ANVIL).strength(50.0F, 20.0F)
                .lightLevel(state -> 3).noOcclusion()
                .isRedstoneConductor((state, level, pos) -> false));
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    private Function<BlockState, VoxelShape> makeShapes() {
        return getShapeForEachState(state -> switch (state.getValue(FACING)) {
            case NORTH -> Shapes.or(box(7.5, 0, 1.5, 15.5, 16, 15.5), box(0.5, 2, 4.5, 7.5, 19, 13.5), box(1.5, 0, 5.5, 6.5, 2, 12.5), box(7.5, 16, 3.5, 15.5, 24, 14.5), box(2, 19, 7, 3, 22, 8), box(5, 19, 7, 6, 23, 8), box(2, 19, 10, 3, 22, 11), box(5, 19, 10, 6, 23, 11));
            case EAST -> Shapes.or(box(0.5, 0, 7.5, 14.5, 16, 15.5), box(2.5, 2, 0.5, 11.5, 19, 7.5), box(3.5, 0, 1.5, 10.5, 2, 6.5), box(1.5, 16, 7.5, 12.5, 24, 15.5), box(8, 19, 2, 9, 22, 3), box(8, 19, 5, 9, 23, 6), box(5, 19, 2, 6, 22, 3), box(5, 19, 5, 6, 23, 6));
            case WEST -> Shapes.or(box(1.5, 0, 0.5, 15.5, 16, 8.5), box(4.5, 2, 8.5, 13.5, 19, 15.5), box(5.5, 0, 9.5, 12.5, 2, 14.5), box(3.5, 16, 0.5, 14.5, 24, 8.5), box(7, 19, 13, 8, 22, 14), box(7, 19, 10, 8, 23, 11), box(10, 19, 13, 11, 22, 14), box(10, 19, 10, 11, 23, 11));
            default -> Shapes.or(box(0.5, 0, 0.5, 8.5, 16, 14.5), box(8.5, 2, 2.5, 15.5, 19, 11.5), box(9.5, 0, 3.5, 14.5, 2, 10.5), box(0.5, 16, 1.5, 8.5, 24, 12.5), box(13, 19, 8, 14, 22, 9), box(10, 19, 8, 11, 23, 9), box(13, 19, 5, 14, 22, 6), box(10, 19, 5, 11, 23, 6));
        });
    }

    @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) { return shapes.apply(state); }
    @Override public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) { return Shapes.empty(); }
    @Override protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }
    @Override public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state == null ? null : state.setValue(FACING,
                context.getHorizontalDirection().getOpposite());
    }
    @Override public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }
    @Override public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
    @Override public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockEntity entity = level.getBlockEntity(pos);
        return entity instanceof MenuProvider provider ? provider : null;
    }
    @Override public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PodstationBlockEntity(pos, state);
    }
    @Override public boolean triggerEvent(BlockState state, Level level, BlockPos pos,
            int eventId, int eventParam) {
        super.triggerEvent(state, level, pos, eventId, eventParam);
        BlockEntity entity = level.getBlockEntity(pos);
        return entity != null && entity.triggerEvent(eventId, eventParam);
    }
    @Override protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level,
            BlockPos pos, boolean movedByPiston) {
        Containers.updateNeighboursAfterDestroy(state, level, pos);
    }
    @Override public boolean hasAnalogOutputSignal(BlockState state) { return true; }
    @Override public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos,
            Direction direction) {
        BlockEntity entity = level.getBlockEntity(pos);
        return entity instanceof PodstationBlockEntity podstation
                ? AbstractContainerMenu.getRedstoneSignalFromContainer(podstation) : 0;
    }
}
