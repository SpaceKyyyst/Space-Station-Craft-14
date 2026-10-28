package net.ssc14.content.block.structure;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class RodUpFloorBlock extends TransparentStructureBlock {
    public RodUpFloorBlock(BlockBehaviour.Properties properties) {
        super(properties.mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.IRON_XYLOPHONE),
                SoundType.COPPER_GRATE, -1.0f, 10.0f);
    }
}
