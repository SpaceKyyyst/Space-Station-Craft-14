package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.planetary.PlanetDirtBlock;

/** @deprecated Use {@link PlanetDirtBlock}. */
@Deprecated(forRemoval = false)
public class DirtBlock extends PlanetDirtBlock {
    public DirtBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
