package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockBehaviour;

/** @deprecated Use {@link net.ssc14.content.block.atmosphere.GasProducerO2Block}. */
@Deprecated(forRemoval = false)
public class GasProducerO2Block
        extends net.ssc14.content.block.atmosphere.GasProducerO2Block
        implements net.mcreator.ssc.IAtmosBlock {
    public GasProducerO2Block(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
