package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use the canonical shared closet block entity. */
@Deprecated(forRemoval = false)
public class ClosetBlackBlockEntity extends net.ssc14.content.block.storage.ClosetBlockEntity {
    public ClosetBlackBlockEntity(BlockPos position, BlockState state) {
        super("closet_black", "Black closet", position, state);
    }
}
