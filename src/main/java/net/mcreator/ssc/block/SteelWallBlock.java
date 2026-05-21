
package net.mcreator.ssc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;

public class SteelWallBlock extends Block {
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    public SteelWallBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(net.minecraft.world.level.block.SoundType.NETHERITE_BLOCK).strength(30f, 12f));
        this.registerDefaultState(
            this.stateDefinition.any()
                .setValue(UP, false).setValue(DOWN, false)
                .setValue(NORTH, false).setValue(EAST, false)
                .setValue(SOUTH, false).setValue(WEST, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        return defaultBlockState()
            .setValue(UP, connectsTo(context.getLevel(), pos.above()))
            .setValue(DOWN, connectsTo(context.getLevel(), pos.below()))
            .setValue(NORTH, connectsTo(context.getLevel(), pos.north()))
            .setValue(EAST, connectsTo(context.getLevel(), pos.east()))
            .setValue(SOUTH, connectsTo(context.getLevel(), pos.south()))
            .setValue(WEST, connectsTo(context.getLevel(), pos.west()));
    }

    // Вызываем процедуру обновления при установке
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        updateWallAndNeighbors(level, pos);
    }

    // Вызываем процедуру обновления при разрушении
    @Override
    public boolean onDestroyedByPlayer(BlockState blockstate, Level world, BlockPos pos, Player entity, boolean willHarvest, FluidState fluid) {
        boolean retval = super.onDestroyedByPlayer(blockstate, world, pos, entity, willHarvest, fluid);
        updateWallAndNeighbors(world, pos);
        try {
            net.mcreator.ssc.procedures.SteelWall_DestroyProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), entity);
        } catch (Exception ex) { /* Игнорируем */ }
        return retval;
    }

    @Override
    public void wasExploded(ServerLevel world, BlockPos pos, Explosion explosion) {
        super.wasExploded(world, pos, explosion);
        updateWallAndNeighbors(world, pos);
        try {
            net.mcreator.ssc.procedures.Wall_Explosion_BreakdownProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
        } catch (Exception ex) { /* Игнорируем */ }
    }

    // Логика соединения (для updateShape, если движок вызовет)
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, 
                                  LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        boolean shouldConnect = neighborState.getBlock() instanceof SteelWallBlock;
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
        return level.getBlockState(pos).getBlock() instanceof SteelWallBlock;
    }

    // === ГЛАВНАЯ ФУНКЦИЯ: Обновляет сам блок и всех 6 соседей ===
    private void updateWallAndNeighbors(Level level, BlockPos centerPos) {
        // Массив смещений: центр + 6 направлений
        int[][] offsets = {
            {0, 0, 0},   // центр
            {0, 1, 0},   // UP
            {0, -1, 0},  // DOWN
            {0, 0, -1},  // NORTH
            {1, 0, 0},   // EAST
            {0, 0, 1},   // SOUTH
            {-1, 0, 0}   // WEST
        };

        for (int[] offset : offsets) {
            BlockPos pos = centerPos.offset(offset[0], offset[1], offset[2]);
            BlockState state = level.getBlockState(pos);
            
            // Пропускаем, если это не наша стена
            if (!(state.getBlock() instanceof SteelWallBlock)) {
                continue;
            }

            // Пересчитываем 6 булевых свойств
            BlockState newState = state
                .setValue(UP, connectsTo(level, pos.above()))
                .setValue(DOWN, connectsTo(level, pos.below()))
                .setValue(NORTH, connectsTo(level, pos.north()))
                .setValue(EAST, connectsTo(level, pos.east()))
                .setValue(SOUTH, connectsTo(level, pos.south()))
                .setValue(WEST, connectsTo(level, pos.west()));

            // Применяем с флагом 3 (уведомить соседей + клиенты) для визуального обновления
            if (state != newState) {
                level.setBlock(pos, newState, Block.UPDATE_ALL);
            }
        }
    }
}
