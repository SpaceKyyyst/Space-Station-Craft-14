package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.lighting.WallMountedLightBlock;

/** @deprecated Use the canonical lighting implementation from {@code net.ssc14}. */
@Deprecated(forRemoval = false)
public class DEBUGTechLampBlock extends net.ssc14.content.block.lighting.DebugLightBlock {
    public DEBUGTechLampBlock(BlockBehaviour.Properties properties) {
        super(properties, WallMountedLightBlock.Geometry.TECHNICAL);
    }
}
