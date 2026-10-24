package net.ssc14.content.block.fuel;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Directional tank whose fuel behavior is provided through a migration boundary. */
public class FuelTankBlock extends Block implements EntityBlock {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    private final Function<BlockState, VoxelShape> shapes = makeShapes();

    public FuelTankBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.COPPER_GOLEM_STATUE).strength(10.0F, 5.0F)
                .noOcclusion().isRedstoneConductor((state, level, pos) -> false));
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    private Function<BlockState, VoxelShape> makeShapes() {
        return getShapeForEachState(state -> switch (state.getValue(FACING)) {
            case NORTH -> Shapes.or(box(1, 1, 2, 15, 3, 14),
                    box(2, 3, 2.5, 9, 15, 13.5), box(9, 3, 2, 15, 14, 14),
                    box(15, 11, 4, 17, 12, 12), box(1, 3, 2, 2, 14, 14));
            case EAST -> Shapes.or(box(2, 1, 1, 14, 3, 15),
                    box(2.5, 3, 2, 13.5, 15, 9), box(2, 3, 9, 14, 14, 15),
                    box(4, 11, 15, 12, 12, 17), box(2, 3, 1, 14, 14, 2));
            case WEST -> Shapes.or(box(2, 1, 1, 14, 3, 15),
                    box(2.5, 3, 7, 13.5, 15, 14), box(2, 3, 1, 14, 14, 7),
                    box(4, 11, -1, 12, 12, 1), box(2, 3, 14, 14, 14, 15));
            default -> Shapes.or(box(1, 1, 2, 15, 3, 14),
                    box(7, 3, 2.5, 14, 15, 13.5), box(1, 3, 2, 7, 14, 14),
                    box(-1, 11, 4, 1, 12, 12), box(14, 3, 2, 15, 14, 14));
        });
    }

    @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) { return shapes.apply(state); }
    @Override public boolean propagatesSkylightDown(BlockState state) { return true; }
    @Override public int getLightDampening(BlockState state) { return 0; }
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
    @Override public void onPlace(BlockState state, Level level, BlockPos pos,
            BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        FuelTankInteractionService.initialize(level, pos);
    }
    @Override public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
            Player player, BlockHitResult hit) {
        super.useWithoutItem(state, level, pos, player, hit);
        FuelTankInteractionService.interact(level, pos, player);
        return InteractionResult.SUCCESS;
    }
    @Override public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockEntity entity = level.getBlockEntity(pos);
        return entity instanceof MenuProvider provider ? provider : null;
    }
    @Override public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FuelTankBlockEntity(pos, state);
    }
    @Override public boolean triggerEvent(BlockState state, Level level, BlockPos pos,
            int eventId, int eventParam) {
        super.triggerEvent(state, level, pos, eventId, eventParam);
        BlockEntity entity = level.getBlockEntity(pos);
        return entity != null && entity.triggerEvent(eventId, eventParam);
    }
}
