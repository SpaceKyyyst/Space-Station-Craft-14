package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.gravity.GravityGeneratorPlugBlock;

/** @deprecated Use {@link GravityGeneratorPlugBlock}. */
@Deprecated(forRemoval = false)
public class GravGenPlugBlockBlock extends GravityGeneratorPlugBlock {
    public GravGenPlugBlockBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
