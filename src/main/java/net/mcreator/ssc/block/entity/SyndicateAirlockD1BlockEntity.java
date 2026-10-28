package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use the canonical airlock block entity. */
@Deprecated(forRemoval = false)
public class SyndicateAirlockD1BlockEntity
        extends net.ssc14.content.block.airlock.SyndicateAirlockD1BlockEntity {
    public SyndicateAirlockD1BlockEntity(BlockPos pos, BlockState state) { super(pos, state); }
}
