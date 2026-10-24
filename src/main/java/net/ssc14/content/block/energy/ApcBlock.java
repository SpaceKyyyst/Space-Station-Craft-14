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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Low-voltage area power controller. */
public class ApcBlock extends Block implements EntityBlock {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 1);
    private final Function<BlockState, VoxelShape> shapes = makeShapes();

    public ApcBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.LODESTONE).strength(30.0F, 10.0F)
                .lightLevel(state -> 3).noCollision().postProcess((state, level, pos) -> pos)
                .emissiveRendering((state, level, pos) -> true)
                .isRedstoneConductor((state, level, pos) -> false));
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH)
                .setValue(BLOCKSTATE, 0));
    }

    private Function<BlockState, VoxelShape> makeShapes() {
        return getShapeForEachState(state -> switch (state.getValue(FACING)) {
            case NORTH -> Shapes.or(box(3.5, 7, 14, 12.5, 12, 16), box(4, 5, 15, 12, 7, 16), box(9.5, 5.25, 14.5, 10.5, 7.25, 15.5), box(7.5, 5.25, 14.5, 8.5, 7.25, 15.5), box(5.5, 5.25, 14.5, 6.5, 7.25, 15.5));
            case EAST -> Shapes.or(box(0, 7, 3.5, 2, 12, 12.5), box(0, 5, 4, 1, 7, 12), box(0.5, 5.25, 9.5, 1.5, 7.25, 10.5), box(0.5, 5.25, 7.5, 1.5, 7.25, 8.5), box(0.5, 5.25, 5.5, 1.5, 7.25, 6.5));
            case WEST -> Shapes.or(box(14, 7, 3.5, 16, 12, 12.5), box(15, 5, 4, 16, 7, 12), box(14.5, 5.25, 5.5, 15.5, 7.25, 6.5), box(14.5, 5.25, 7.5, 15.5, 7.25, 8.5), box(14.5, 5.25, 9.5, 15.5, 7.25, 10.5));
            default -> Shapes.or(box(3.5, 7, 0, 12.5, 12, 2), box(4, 5, 0, 12, 7, 1), box(5.5, 5.25, 0.5, 6.5, 7.25, 1.5), box(7.5, 5.25, 0.5, 8.5, 7.25, 1.5), box(9.5, 5.25, 0.5, 10.5, 7.25, 1.5));
        });
    }

    @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) { return shapes.apply(state); }
    @Override public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) { return Shapes.empty(); }
    @Override protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, BLOCKSTATE);
    }
    @Override public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state == null ? null : state.setValue(FACING,
                context.getHorizontalDirection().getOpposite()).setValue(BLOCKSTATE, 0);
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
        return new ApcBlockEntity(pos, state);
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
        return entity instanceof ApcBlockEntity apc
                ? AbstractContainerMenu.getRedstoneSignalFromContainer(apc) : 0;
    }
}
