package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockBehaviour;

/** @deprecated Use {@link net.ssc14.content.block.airlock.ShuttersBlock}. */
@Deprecated(forRemoval = false)
public class ShuttersBlock extends net.ssc14.content.block.airlock.ShuttersBlock
        implements net.ssc14.common.network.NetworkAction,
        net.ssc14.common.network.NetworkTrigger {
    public ShuttersBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
