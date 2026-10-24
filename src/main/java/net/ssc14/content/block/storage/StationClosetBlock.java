package net.ssc14.content.block.storage;

import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Shared runtime behavior for the station's inventory closets. */
public class StationClosetBlock extends Block implements EntityBlock {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    private final BiFunction<BlockPos, BlockState, ? extends BlockEntity> blockEntityFactory;
    private final Function<BlockState, VoxelShape> shapes;

    public StationClosetBlock(BlockBehaviour.Properties properties,
            BiFunction<BlockPos, BlockState, ? extends BlockEntity> blockEntityFactory) {
        this(properties, blockEntityFactory, 20.0F, 10.0F);
    }

    protected StationClosetBlock(BlockBehaviour.Properties properties,
            BiFunction<BlockPos, BlockState, ? extends BlockEntity> blockEntityFactory,
            float destroyTime, float explosionResistance) {
        super(properties.sound(SoundType.VAULT)
                .strength(destroyTime, explosionResistance)
                .noOcclusion()
                .isRedstoneConductor((state, level, pos) -> false));
        this.blockEntityFactory = blockEntityFactory;
        registerDefaultState(stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(OPEN, false));
        this.shapes = getShapeForEachState(this::createShape);
    }

    private VoxelShape createShape(BlockState state) {
        if (isDoorOpen(state)) {
            return switch (state.getValue(FACING)) {
                case NORTH -> subtractInterior(box(2, 1, 0, 14, 31, 14));
                case EAST -> subtractInterior(box(2, 1, 2, 16, 31, 14));
                case WEST -> subtractInterior(box(0, 1, 2, 14, 31, 14));
                default -> subtractInterior(box(2, 1, 2, 14, 31, 16));
            };
        }
        return subtractInterior(box(2, 1, 2, 14, 31, 14));
    }

    protected boolean isDoorOpen(BlockState state) {
        return state.getValue(OPEN);
    }

    private static VoxelShape subtractInterior(VoxelShape interior) {
        return Shapes.join(box(1, 0, 1, 15, 32, 15), interior, BooleanOp.ONLY_FIRST);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) {
        return shapes.apply(state);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    @Override
    public int getLightDampening(BlockState state) {
        return 0;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, OPEN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state == null ? null : state
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(OPEN, false);
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
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (level instanceof LevelAccessor accessor) {
            return ClosetStructureService.canPlace(accessor, pos);
        }
        return super.canSurvive(state, level, pos);
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader level,
            ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction direction,
            BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        return !state.canSurvive(level, currentPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, level, scheduledTickAccess, currentPos, direction,
                        neighborPos, neighborState, random);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState,
            boolean moving) {
        super.onPlace(state, level, pos, oldState, moving);
        ClosetStructureService.placeUpperPlug(level, pos, state);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player,
            ItemStack tool, boolean willHarvest, FluidState fluid) {
        boolean destroyed = super.onDestroyedByPlayer(
                state, level, pos, player, tool, willHarvest, fluid);
        ClosetStructureService.destroyCounterpart(level, pos);
        return destroyed;
    }

    @Override
    public void wasExploded(ServerLevel level, BlockPos pos, Explosion explosion) {
        super.wasExploded(level, pos, explosion);
        ClosetStructureService.destroyCounterpart(level, pos);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
            Player player, BlockHitResult hit) {
        interact(level, pos, state, player);
        return InteractionResult.SUCCESS;
    }

    protected void interact(Level level, BlockPos pos, BlockState state, Player player) {
        ClosetInteractionService.toggle(level, pos, state);
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity instanceof MenuProvider provider ? provider : null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return blockEntityFactory.apply(pos, state);
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
            boolean moved) {
        Containers.updateNeighboursAfterDestroy(state, level, pos);
    }
}
