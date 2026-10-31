package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use the canonical shared lighting block entity. */
@Deprecated(forRemoval = false)
public class EmergencyLightBlockEntity
        extends net.ssc14.content.block.lighting.LightingBlockEntity {
    public EmergencyLightBlockEntity(BlockPos position, BlockState state) {
        super("emergency_light", "Emergency Light", false, position, state);
    }
}
