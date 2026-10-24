package net.ssc14.content.block.window;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class DirectedArmoredWindowBlock extends AbstractDirectedWindowBlock {
    public static final EnumProperty<Direction> FACING = AbstractDirectedWindowBlock.FACING;
    public static final IntegerProperty WINDOW_DISSASAMBLY =
            IntegerProperty.create("window_dissasambly", 0, 5);

    public DirectedArmoredWindowBlock(BlockBehaviour.Properties properties) {
        super(properties, 15.0f, 10.0f);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH)
                .setValue(WINDOW_DISSASAMBLY, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WINDOW_DISSASAMBLY);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = placementState(context);
        return state == null ? null : state.setValue(WINDOW_DISSASAMBLY, 0);
    }
}
