package net.ssc14.content.block.control;

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
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.ssc14.common.network.NetworkTrigger;

/** Canonical network trigger button. */
public class NetworkButtonBlock extends Block implements EntityBlock, NetworkTrigger {
    public static final EnumProperty<Direction> FACING = DirectionalBlock.FACING;
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    private static final String ACTIVATE_TRIGGER = "activate";
    private final Function<BlockState, VoxelShape> shapes = makeShapes();

    public NetworkButtonBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.LANTERN).strength(10.0F, 5.0F).noCollision()
                .isRedstoneConductor((state, level, pos) -> false));
        registerDefaultState(stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ACTIVE, false));
    }

    private Function<BlockState, VoxelShape> makeShapes() {
        return getShapeForEachState(state -> switch (state.getValue(FACING)) {
            case NORTH -> box(6, 6, 14, 10, 10, 16);
            case EAST -> box(0, 6, 6, 2, 10, 10);
            case WEST -> box(14, 6, 6, 16, 10, 10);
            case UP -> box(6, 0, 6, 10, 2, 10);
            case DOWN -> box(6, 14, 6, 10, 16, 10);
            default -> box(6, 6, 0, 10, 10, 2);
        });
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) {
        return shapes.apply(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, ACTIVE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state == null ? null
                : state.setValue(FACING, context.getClickedFace()).setValue(ACTIVE, false);
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
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
            Player player, BlockHitResult hit) {
        super.useWithoutItem(state, level, pos, player, hit);
        NetworkButtonInteractionService.activate(level, pos);
        return InteractionResult.SUCCESS;
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity instanceof MenuProvider provider ? provider : null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new NetworkButtonBlockEntity(pos, state);
    }

    @Override
    public List<String> getAvailableTriggers() {
        return List.of(ACTIVATE_TRIGGER);
    }

    @Override
    public String getTriggerName(String triggerId) {
        return ACTIVATE_TRIGGER.equals(triggerId) ? "При нажатии" : triggerId;
    }
}
