package net.ssc14.content.block.structure;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class PlasteelWallCarcaseBlock extends TransparentStructureBlock {
    public PlasteelWallCarcaseBlock(BlockBehaviour.Properties properties) {
        super(properties, SoundType.COPPER_GRATE, 20.0f, 7.5f);
    }
}
