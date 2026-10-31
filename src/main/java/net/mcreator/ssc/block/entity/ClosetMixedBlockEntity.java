package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use the canonical shared closet block entity. */
@Deprecated(forRemoval = false)
public class ClosetMixedBlockEntity extends net.ssc14.content.block.storage.ClosetBlockEntity {
    public ClosetMixedBlockEntity(BlockPos position, BlockState state) {
        super("closet_mixed", "Mixed closet", position, state);
    }
}
