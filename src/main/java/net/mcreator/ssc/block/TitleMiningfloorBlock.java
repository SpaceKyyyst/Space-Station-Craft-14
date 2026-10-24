package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.floor.FloorTileBlock;

/** @deprecated Use {@link FloorTileBlock} through the canonical registry. */
@Deprecated
public class TitleMiningfloorBlock extends FloorTileBlock {
    public TitleMiningfloorBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
