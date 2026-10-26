package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use the canonical airlock block entity. */
@Deprecated(forRemoval = false)
public class AtmosphericsAirlockD1BlockEntity
        extends net.ssc14.content.block.airlock.AtmosphericsAirlockD1BlockEntity {
    public AtmosphericsAirlockD1BlockEntity(BlockPos pos, BlockState state) { super(pos, state); }
}
