package net.ssc14.content.block.explosives;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.ssc14.content.explosive.C4ActivationService;
import net.ssc14.content.explosive.C4DetonationService;
import net.ssc14.content.explosive.C4PlantService;

/** Canonical C4 charge block with constant-time lifecycle delegation. */
public class C4ChargeBlock extends Block implements EntityBlock {
    public static final EnumProperty<Direction> FACING = DirectionalBlock.FACING;
    private final Function<BlockState, VoxelShape> shapes = getShapeForEachState(state ->
            switch (state.getValue(FACING)) {
                case NORTH -> box(5, 3, 13, 11, 13, 16);
                case EAST -> box(0, 3, 5, 3, 13, 11);
                case WEST -> box(13, 3, 5, 16, 13, 11);
                case UP -> box(5, 0, 3, 11, 3, 13);
                case DOWN -> box(5, 13, 3, 11, 16, 13);
                default -> box(5, 3, 0, 11, 13, 3);
            });

    public C4ChargeBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.SPAWNER).strength(50.0F, 5.0F)
                .lightLevel(state -> 1).noCollision()
                .isRedstoneConductor((state, level, pos) -> false));
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
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
        return state == null ? null : state.setValue(FACING, context.getClickedFace());
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
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState,
            boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        C4PlantService.execute(level, pos);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player,
            ItemStack tool, boolean willHarvest, FluidState fluid) {
        boolean destroyed = super.onDestroyedByPlayer(
                state, level, pos, player, tool, willHarvest, fluid);
        C4DetonationService.execute(level, pos.getX(), pos.getY(), pos.getZ());
        return destroyed;
    }

    @Override
    public void wasExploded(ServerLevel level, BlockPos pos, Explosion explosion) {
        super.wasExploded(level, pos, explosion);
        C4DetonationService.execute(level, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
            Player player, BlockHitResult hit) {
        super.useWithoutItem(state, level, pos, player, hit);
        C4ActivationService.execute(level, pos, player);
        return InteractionResult.SUCCESS;
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity instanceof MenuProvider provider ? provider : null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new C4ChargeBlockEntity(pos, state);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int eventId,
            int eventParam) {
        super.triggerEvent(state, level, pos, eventId, eventParam);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity != null && blockEntity.triggerEvent(eventId, eventParam);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos,
            boolean movedByPiston) {
        Containers.updateNeighboursAfterDestroy(state, level, pos);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos,
            Direction direction) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity instanceof C4ChargeBlockEntity container
                ? AbstractContainerMenu.getRedstoneSignalFromContainer(container) : 0;
    }
}
