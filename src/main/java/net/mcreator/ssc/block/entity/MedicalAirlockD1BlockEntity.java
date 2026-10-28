package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use the canonical airlock block entity. */
@Deprecated(forRemoval = false)
public class MedicalAirlockD1BlockEntity
        extends net.ssc14.content.block.airlock.MedicalAirlockD1BlockEntity {
    public MedicalAirlockD1BlockEntity(BlockPos pos, BlockState state) { super(pos, state); }
}
