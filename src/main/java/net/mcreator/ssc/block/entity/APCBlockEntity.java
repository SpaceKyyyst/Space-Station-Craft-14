package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use {@link net.ssc14.content.block.energy.ApcBlockEntity}. */
@Deprecated(forRemoval = false)
public class APCBlockEntity extends net.ssc14.content.block.energy.ApcBlockEntity
        implements net.mcreator.ssc.IEnergyStorageBlock {
    public APCBlockEntity(BlockPos pos, BlockState state) { super(pos, state); }
}
