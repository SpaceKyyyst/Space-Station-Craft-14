package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use the canonical airlock block entity. */
@Deprecated(forRemoval = false)
public class VirologyAirlockD1BlockEntity
        extends net.ssc14.content.block.airlock.VirologyAirlockD1BlockEntity {
    public VirologyAirlockD1BlockEntity(BlockPos pos, BlockState state) { super(pos, state); }
}
