package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use the canonical shared closet block entity. */
@Deprecated(forRemoval = false)
public class ClosetGreenBlockEntity extends net.ssc14.content.block.storage.ClosetBlockEntity {
    public ClosetGreenBlockEntity(BlockPos position, BlockState state) {
        super("closet_green", "Green closet", position, state);
    }
}
