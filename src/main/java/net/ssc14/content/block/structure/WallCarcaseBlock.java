package net.ssc14.content.block.structure;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class WallCarcaseBlock extends TransparentStructureBlock {
    public WallCarcaseBlock(BlockBehaviour.Properties properties) {
        super(properties, SoundType.COPPER_GRATE, 10.0f, 5.0f);
    }
}
