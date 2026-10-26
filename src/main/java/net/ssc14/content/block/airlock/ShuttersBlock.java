package net.ssc14.content.block.airlock;

import java.util.List;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.ssc14.common.network.NetworkAction;
import net.ssc14.common.network.NetworkTrigger;

/** Canonical animated shutter block. */
public class ShuttersBlock extends Block implements EntityBlock, NetworkAction, NetworkTrigger {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty OPENING = BooleanProperty.create("opening");
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    private static final String OPEN_ACTION = "open";
    private static final String CLOSE_ACTION = "close";
    private static final String TOGGLE_ACTION = "toggle";
    private final Function<BlockState, VoxelShape> shapes = makeShapes();

    public ShuttersBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.TRIAL_SPAWNER).strength(30.0F, 10.0F)
                .noOcclusion().isRedstoneConductor((state, level, pos) -> false));
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH)
                .setValue(UP, false).setValue(DOWN, false)
                .setValue(OPENING, false).setValue(OPEN, false));
    }

    private Function<BlockState, VoxelShape> makeShapes() {
        return getShapeForEachState(state -> {
            Direction facing = state.getValue(FACING);
            boolean opening = state.getValue(OPENING);
            boolean open = state.getValue(OPEN);
            if (opening) {
                if (!open && !state.getValue(UP) && !state.getValue(DOWN)) {
                    return lowerShape(facing);
                }
                return intermediateShape(facing);
            }
            if (open) {
                return state.getValue(UP) ? sideRailsShape(facing) : topShape(facing);
            }
            return closedShape(facing);
        });
    }

    private static VoxelShape lowerShape(Direction facing) {
        return isEastWest(facing) ? box(7, 8, 0, 9, 16, 16)
                : box(0, 8, 7, 16, 16, 9);
    }

    private static VoxelShape intermediateShape(Direction facing) {
        return isEastWest(facing)
                ? Shapes.or(box(6, 14, 0, 10, 16, 16), box(7, 8, 0, 9, 16, 16))
                : Shapes.or(box(0, 14, 6, 16, 16, 10), box(0, 8, 7, 16, 16, 9));
    }

    private static VoxelShape topShape(Direction facing) {
        return isEastWest(facing) ? box(6, 14, 0, 10, 16, 16)
                : box(0, 14, 6, 16, 16, 10);
    }

    private static VoxelShape sideRailsShape(Direction facing) {
        return switch (facing) {
            case NORTH -> Shapes.or(box(0.5, 0, 7, 0.501, 16, 9),
                    box(15.5, 0, 7, 15.501, 16, 9));
            case EAST -> Shapes.or(box(7, 0, 0.5, 9, 16, 0.501),
                    box(7, 0, 15.5, 9, 16, 15.501));
            case WEST -> Shapes.or(box(7, 0, 15.499, 9, 16, 15.5),
                    box(7, 0, 0.499, 9, 16, 0.5));
            default -> Shapes.or(box(15.499, 0, 7, 15.5, 16, 9),
                    box(0.499, 0, 7, 0.5, 16, 9));
        };
    }

    private static VoxelShape closedShape(Direction facing) {
        return isEastWest(facing) ? box(7, 0, 0, 9, 16, 16)
                : box(0, 0, 7, 16, 16, 9);
    }

    private static boolean isEastWest(Direction facing) {
        return facing == Direction.EAST || facing == Direction.WEST;
    }

    @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) { return shapes.apply(state); }

    @Override protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, UP, DOWN, OPENING, OPEN);
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

    @Override protected void neighborChanged(BlockState state, Level level, BlockPos pos,
            Block neighborBlock, Orientation orientation, boolean moving) {
        super.neighborChanged(state, level, pos, neighborBlock, orientation, moving);
        ShuttersIntegrationService.update(level, pos, state);
    }

    @Override public InteractionResult useWithoutItem(BlockState state, Level level,
            BlockPos pos, Player player, BlockHitResult hit) {
        super.useWithoutItem(state, level, pos, player, hit);
        ShuttersIntegrationService.use(level, pos, state, player);
        return InteractionResult.SUCCESS;
    }

    @Override public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockEntity entity = level.getBlockEntity(pos);
        return entity instanceof MenuProvider provider ? provider : null;
    }

    @Override public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ShuttersBlockEntity(pos, state);
    }

    @Override public List<String> getAvailableActions() {
        return List.of(OPEN_ACTION, CLOSE_ACTION, TOGGLE_ACTION);
    }

    @Override public String getActionName(String actionId) {
        return switch (actionId) {
            case OPEN_ACTION -> "Открыть";
            case CLOSE_ACTION -> "Закрыть";
            case TOGGLE_ACTION -> "Переключить";
            default -> actionId;
        };
    }

    @Override public List<String> getAvailableTriggers() { return List.of(TOGGLE_ACTION); }

    @Override public String getTriggerName(String triggerId) {
        return TOGGLE_ACTION.equals(triggerId) ? "При активации" : triggerId;
    }

    @Override public void executeNetworkAction(String actionId, Level level, BlockPos pos) {
        if (level.isClientSide()) return;
        BlockState state = level.getBlockState(pos);
        if (OPEN_ACTION.equals(actionId)) {
            ShuttersIntegrationService.setOpen(level, pos, state, true);
        } else if (CLOSE_ACTION.equals(actionId)) {
            ShuttersIntegrationService.setOpen(level, pos, state, false);
        } else if (TOGGLE_ACTION.equals(actionId)) {
            ShuttersIntegrationService.setOpen(level, pos, state, !state.getValue(OPEN));
        }
    }
}
