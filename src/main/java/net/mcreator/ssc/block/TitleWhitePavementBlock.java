package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.floor.FloorTileBlock;

/** @deprecated Use {@link FloorTileBlock} through the canonical registry. */
@Deprecated
public class TitleWhitePavementBlock extends FloorTileBlock {
    public TitleWhitePavementBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
