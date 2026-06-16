
package net.mcreator.ssc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.InteractionResult;

import net.mcreator.ssc.procedures.Windows_KnockKnock_Procedure;

public class BaseWindowBlock extends Block {
    // === Твои оригинальные свойства ===
    public static final IntegerProperty WINDOW_DISASSEMBLY = IntegerProperty.create("window_disassembly", 0, 1);
    
    // === Новые свойства для соединения (как у стен) ===
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    public BaseWindowBlock(BlockBehaviour.Properties properties) {
        super(properties
            .sound(net.minecraft.world.level.block.SoundType.GLASS)
            .strength(10f, 5f)
            .noOcclusion()
            .isRedstoneConductor((bs, br, bp) -> false)
        );
        // Регистрируем дефолтное состояние со всеми свойствами
        this.registerDefaultState(
            this.stateDefinition.any()
                .setValue(WINDOW_DISASSEMBLY, 0)
                .setValue(UP, false).setValue(DOWN, false)
                .setValue(NORTH, false).setValue(EAST, false)
                .setValue(SOUTH, false).setValue(WEST, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WINDOW_DISASSEMBLY, UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        return defaultBlockState()
            .setValue(WINDOW_DISASSEMBLY, 0)
            .setValue(UP, connectsTo(context.getLevel(), pos.above()))
            .setValue(DOWN, connectsTo(context.getLevel(), pos.below()))
            .setValue(NORTH, connectsTo(context.getLevel(), pos.north()))
            .setValue(EAST, connectsTo(context.getLevel(), pos.east()))
            .setValue(SOUTH, connectsTo(context.getLevel(), pos.south()))
            .setValue(WEST, connectsTo(context.getLevel(), pos.west()));
    }
    // Процедура стука по окну
	@Override
	public InteractionResult useWithoutItem(BlockState blockstate, Level world, BlockPos pos, Player entity, BlockHitResult hit) {
		super.useWithoutItem(blockstate, world, pos, entity, hit);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		double hitX = hit.getLocation().x;
		double hitY = hit.getLocation().y;
		double hitZ = hit.getLocation().z;
		Direction direction = hit.getDirection();
		Windows_KnockKnock_Procedure.execute(world, x, y, z, entity);
		return InteractionResult.SUCCESS;
	}

    // === Вызываем обновление соседей при установке ===
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        updateWindowAndNeighbors(level, pos);
    }

    // === Вызываем обновление соседей при разрушении ===
    @Override
    public boolean onDestroyedByPlayer(BlockState blockstate, Level world, BlockPos pos, Player entity, boolean willHarvest, FluidState fluid) {
        boolean retval = super.onDestroyedByPlayer(blockstate, world, pos, entity, willHarvest, fluid);
        updateWindowAndNeighbors(world, pos);
        // Если есть процедура разрушения окна — раскомментируй и поправь имя
        // try { net.mcreator.ssc.procedures.Window_DestroyProcedure.execute(...); } catch (Exception ex) {}
        return retval;
    }

    // === Логика соединения для updateShape ===
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, 
                                  LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        boolean shouldConnect = neighborState.getBlock() instanceof BaseWindowBlock;
        return switch (direction) {
            case UP -> state.setValue(UP, shouldConnect);
            case DOWN -> state.setValue(DOWN, shouldConnect);
            case NORTH -> state.setValue(NORTH, shouldConnect);
            case EAST -> state.setValue(EAST, shouldConnect);
            case SOUTH -> state.setValue(SOUTH, shouldConnect);
            case WEST -> state.setValue(WEST, shouldConnect);
        };
    }

	private boolean connectsTo(LevelAccessor level, BlockPos pos) {
	    BlockState state = level.getBlockState(pos);
	    return state.getBlock() instanceof BaseWindowBlock 
	        && state.getValue(WINDOW_DISASSEMBLY) == 0; // Только целые окна коннектятся
	}

    // === ГЛАВНАЯ ФУНКЦИЯ: Обновляет окно и всех 6 соседей ===
    private void updateWindowAndNeighbors(Level level, BlockPos centerPos) {
        int[][] offsets = {
            {0, 0, 0}, {0, 1, 0}, {0, -1, 0}, {0, 0, -1}, {1, 0, 0}, {0, 0, 1}, {-1, 0, 0}
        };
        for (int[] offset : offsets) {
            BlockPos pos = centerPos.offset(offset[0], offset[1], offset[2]);
            BlockState state = level.getBlockState(pos);
            if (!(state.getBlock() instanceof BaseWindowBlock)) continue;
            
            BlockState newState = state
                .setValue(UP, connectsTo(level, pos.above()))
                .setValue(DOWN, connectsTo(level, pos.below()))
                .setValue(NORTH, connectsTo(level, pos.north()))
                .setValue(EAST, connectsTo(level, pos.east()))
                .setValue(SOUTH, connectsTo(level, pos.south()))
                .setValue(WEST, connectsTo(level, pos.west()));
            
            if (state != newState) {
                level.setBlock(pos, newState, Block.UPDATE_ALL);
            }
        }
    }

    // === Твои оригинальные методы для окон (сохранены!) ===
    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return adjacentBlockState.getBlock() == this ? true : super.skipRendering(state, adjacentBlockState, side);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    @Override
    public int getLightBlock(BlockState state) {
        return 0;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }
}
