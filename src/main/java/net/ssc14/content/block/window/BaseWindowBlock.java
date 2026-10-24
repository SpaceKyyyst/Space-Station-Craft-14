package net.ssc14.content.block.window;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class BaseWindowBlock extends AbstractWindowBlock {
    public static final IntegerProperty WINDOW_DISASSEMBLY =
            IntegerProperty.create("window_disassembly", 0, 1);

    public BaseWindowBlock(BlockBehaviour.Properties properties) {
        super(properties, 10.0f, 5.0f);
        registerDefaultState(stateDefinition.any().setValue(WINDOW_DISASSEMBLY, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WINDOW_DISASSEMBLY);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state == null ? null : state.setValue(WINDOW_DISASSEMBLY, 0);
    }
}
