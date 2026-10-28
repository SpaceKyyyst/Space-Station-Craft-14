package net.ssc14.content.block.airlock;

import java.util.function.BiFunction;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Shared canonical implementation for closed and open upper airlock plugs. */
public class AirlockUpperPlugBlock extends Block implements EntityBlock {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    private final Function<BlockState, VoxelShape> shapes;
    private final BiFunction<BlockPos, BlockState, ? extends BlockEntity> entityFactory;

    public AirlockUpperPlugBlock(BlockBehaviour.Properties properties, boolean open,
            BiFunction<BlockPos, BlockState, ? extends BlockEntity> entityFactory) {
        super(configure(properties, open));
        this.entityFactory = entityFactory;
        shapes = getShapeForEachState(state -> shape(state.getValue(FACING), open));
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    private static BlockBehaviour.Properties configure(
            BlockBehaviour.Properties properties, boolean open) {
        properties.sound(SoundType.EMPTY).strength(-1.0F, 20.0F)
                .isRedstoneConductor((state, level, pos) -> false);
        return open ? properties.noCollision() : properties.noOcclusion();
    }

    private static VoxelShape shape(Direction facing, boolean open) {
        boolean eastWest = facing == Direction.EAST || facing == Direction.WEST;
        VoxelShape outside = eastWest ? box(5, 0, 0, 11, 16, 16)
                : box(0, 0, 5, 16, 16, 11);
        if (!open) return outside;
        VoxelShape inside = eastWest ? box(5, 0, 1, 11, 16, 15)
                : box(1, 0, 5, 15, 16, 11);
        return Shapes.join(outside, inside, BooleanOp.ONLY_FIRST);
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
        return state == null ? null : state.setValue(
                FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override public ItemStack getCloneItemStack(LevelReader level, BlockPos pos,
            BlockState state, boolean includeData, Player player) { return ItemStack.EMPTY; }

    @Override protected void neighborChanged(BlockState state, Level level, BlockPos pos,
            Block neighborBlock, @Nullable Orientation orientation, boolean moving) {
        super.neighborChanged(state, level, pos, neighborBlock, orientation, moving);
        AirlockUpperPlugIntegrationService.update(level, pos);
    }

    @Override public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos,
            Player player, ItemStack tool, boolean willHarvest, FluidState fluid) {
        boolean removed = super.onDestroyedByPlayer(
                state, level, pos, player, tool, willHarvest, fluid);
        AirlockUpperPlugIntegrationService.destroy(level, pos);
        return removed;
    }

    @Override public void wasExploded(ServerLevel level, BlockPos pos, Explosion explosion) {
        super.wasExploded(level, pos, explosion);
        AirlockUpperPlugIntegrationService.destroy(level, pos);
    }

    @Override public InteractionResult useWithoutItem(BlockState state, Level level,
            BlockPos pos, Player player, BlockHitResult hit) {
        super.useWithoutItem(state, level, pos, player, hit);
        AirlockUpperPlugIntegrationService.use(level, pos, player);
        return InteractionResult.SUCCESS;
    }

    @Override public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockEntity entity = level.getBlockEntity(pos);
        return entity instanceof MenuProvider provider ? provider : null;
    }

    @Override public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return entityFactory.apply(pos, state);
    }

    @Override public boolean triggerEvent(BlockState state, Level level, BlockPos pos,
            int eventId, int eventParam) {
        super.triggerEvent(state, level, pos, eventId, eventParam);
        BlockEntity entity = level.getBlockEntity(pos);
        return entity != null && entity.triggerEvent(eventId, eventParam);
    }
}
