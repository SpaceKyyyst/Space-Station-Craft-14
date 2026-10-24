package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use {@link net.ssc14.content.block.debug.FailureSentinelBlockEntity}. */
@Deprecated(forRemoval = false)
public class KostilniblokBlockEntity
        extends net.ssc14.content.block.debug.FailureSentinelBlockEntity {
    public KostilniblokBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }
}
