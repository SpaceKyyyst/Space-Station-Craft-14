package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use {@link net.ssc14.content.block.energy.SheathingBlockEntity}. */
@Deprecated(forRemoval = false)
public class SheathingBlockEntity
        extends net.ssc14.content.block.energy.SheathingBlockEntity {
    public SheathingBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }
}
