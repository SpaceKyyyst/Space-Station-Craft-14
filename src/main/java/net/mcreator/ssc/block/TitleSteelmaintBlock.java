package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.floor.FloorTileBlock;

/** @deprecated Use {@link FloorTileBlock} through the canonical registry. */
@Deprecated
public class TitleSteelmaintBlock extends FloorTileBlock {
    public TitleSteelmaintBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
