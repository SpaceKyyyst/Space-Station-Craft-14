package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use the canonical shared closet block entity. */
@Deprecated(forRemoval = false)
public class ClosetN2BlockEntity extends net.ssc14.content.block.storage.ClosetBlockEntity {
    public ClosetN2BlockEntity(BlockPos position, BlockState state) {
        super("closet_n_2", "N2 closet", position, state);
    }
}
