package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use {@link net.ssc14.content.block.energy.PodstationBlockEntity}. */
@Deprecated(forRemoval = false)
public class PodstationBlockEntity extends net.ssc14.content.block.energy.PodstationBlockEntity
        implements net.mcreator.ssc.IEnergyStorageBlock {
    public PodstationBlockEntity(BlockPos pos, BlockState state) { super(pos, state); }
}
