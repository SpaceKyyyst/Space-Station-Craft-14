package net.mcreator.ssc.block;

import net.mcreator.ssc.block.entity.EmergencyLightBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;

/** @deprecated Use the canonical lighting implementation from {@code net.ssc14}. */
@Deprecated(forRemoval = false)
public class EmergencyLightBlock extends net.ssc14.content.block.lighting.EmergencyLightBlock {
    public EmergencyLightBlock(BlockBehaviour.Properties properties) {
        super(properties, EmergencyLightBlockEntity::new);
    }
}
