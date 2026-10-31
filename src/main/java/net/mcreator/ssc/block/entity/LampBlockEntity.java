package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use the canonical shared lighting block entity. */
@Deprecated(forRemoval = false)
public class LampBlockEntity extends net.ssc14.content.block.lighting.LightingBlockEntity {
    public LampBlockEntity(BlockPos position, BlockState state) {
        super("lamp", "Lamp", true, position, state);
    }
}
