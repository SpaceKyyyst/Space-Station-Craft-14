
package net.mcreator.ssc;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ConnectedBlock extends Block {
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    private final String connectionGroup;

    public ConnectedBlock(Properties properties, String connectionGroup) {
        super(properties);
        this.connectionGroup = connectionGroup;
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

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, 
                                  LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        boolean shouldConnect = connectsTo(neighborState);
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
        return connectsTo(level.getBlockState(pos));
    }

    private boolean connectsTo(BlockState state) {
        return state.getBlock() instanceof ConnectedBlock other && other.connectionGroup.equals(this.connectionGroup);
    }
}
