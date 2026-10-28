package net.ssc14.content.block.communication;

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

/** Canonical communication console block; legacy GUI integration stays behind a port. */
public class CommunicationConsoleBlock extends Block implements EntityBlock {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    private final Function<BlockState, VoxelShape> shapes = makeShapes();

    public CommunicationConsoleBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.ANVIL).strength(20.0F, 10.0F).noOcclusion()
                .isRedstoneConductor((state, level, pos) -> false));
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    private Function<BlockState, VoxelShape> makeShapes() {
        return getShapeForEachState(state -> switch (state.getValue(FACING)) {
            case NORTH -> Shapes.or(box(2, 0, 12, 14, 18, 16), box(1, 0, 4, 15, 13, 11),
                    box(1, 13, 1, 15, 16, 13),
                    box(0.5, 13.77111, 7.39283, 15.5, 24.77111, 15));
            case EAST -> Shapes.or(box(0, 0, 2, 4, 18, 14), box(5, 0, 1, 12, 13, 15),
                    box(3, 13, 1, 15, 16, 15),
                    box(1, 13.77111, 0.5, 8.60717, 24.77111, 15.5));
            case WEST -> Shapes.or(box(12, 0, 2, 16, 18, 14), box(4, 0, 1, 11, 13, 15),
                    box(1, 13, 1, 13, 16, 15),
                    box(7.39283, 13.77111, 0.5, 15, 24.77111, 15.5));
            default -> Shapes.or(box(2, 0, 0, 14, 18, 4), box(1, 0, 5, 15, 13, 12),
                    box(1, 13, 3, 15, 16, 15),
                    box(0.5, 13.77111, 1, 15.5, 24.77111, 8.60717));
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
        return state == null ? null
                : state.setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
            Player player, BlockHitResult hit) {
        CommunicationConsoleIntegrationService.use(level, pos, player);
        return InteractionResult.SUCCESS;
    }

    @Override public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity instanceof MenuProvider provider ? provider : null;
    }

    @Override public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CommunicationConsoleBlockEntity(pos, state);
    }

    @Override public boolean triggerEvent(BlockState state, Level level, BlockPos pos,
            int eventId, int eventParam) {
        super.triggerEvent(state, level, pos, eventId, eventParam);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity != null && blockEntity.triggerEvent(eventId, eventParam);
    }
}
