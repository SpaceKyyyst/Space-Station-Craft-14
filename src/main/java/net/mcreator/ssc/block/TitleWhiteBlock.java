package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.floor.InsetFloorTileBlock;

/** @deprecated Use {@link InsetFloorTileBlock} through the canonical registry. */
@Deprecated
public class TitleWhiteBlock extends InsetFloorTileBlock {
    public TitleWhiteBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
