package net.ssc14.content.block.lighting;

import net.minecraft.world.level.block.state.BlockBehaviour;

/** Always-on debug fixture with the legacy light level and durability. */
public class DebugLightBlock extends WallMountedLightBlock {
    public DebugLightBlock(BlockBehaviour.Properties properties, Geometry geometry) {
        super(properties, geometry, 10.0F, 15);
    }
}
