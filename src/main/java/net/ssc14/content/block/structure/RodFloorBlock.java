package net.ssc14.content.block.structure;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class RodFloorBlock extends TransparentStructureBlock {
    public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 7);

    public RodFloorBlock(BlockBehaviour.Properties properties) {
        super(properties.mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.IRON_XYLOPHONE),
                SoundType.COPPER_GRATE, -1.0f, 10.0f);
        registerDefaultState(stateDefinition.any().setValue(BLOCKSTATE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BLOCKSTATE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state == null ? null : state.setValue(BLOCKSTATE, 0);
    }
}
