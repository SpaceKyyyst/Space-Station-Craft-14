package net.ssc14.content.block.window;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class DirectedBaseWindowBlock extends AbstractDirectedWindowBlock {
    public static final EnumProperty<Direction> FACING = AbstractDirectedWindowBlock.FACING;
    public static final IntegerProperty WINDOW_DISASSEMBLY =
            IntegerProperty.create("window_disassembly", 0, 1);
    public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 1);

    public DirectedBaseWindowBlock(BlockBehaviour.Properties properties) {
        super(properties, 5.0f, 4.0f);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH)
                .setValue(WINDOW_DISASSEMBLY, 0).setValue(BLOCKSTATE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WINDOW_DISASSEMBLY, BLOCKSTATE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = placementState(context);
        return state == null ? null : state.setValue(WINDOW_DISASSEMBLY, 0).setValue(BLOCKSTATE, 0);
    }
}
