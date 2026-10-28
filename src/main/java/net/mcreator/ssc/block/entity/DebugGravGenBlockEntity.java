package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use {@link net.ssc14.content.block.debug.DebugGravityGeneratorBlockEntity}. */
@Deprecated(forRemoval = false)
public class DebugGravGenBlockEntity
        extends net.ssc14.content.block.debug.DebugGravityGeneratorBlockEntity {
    public DebugGravGenBlockEntity(BlockPos pos, BlockState state) { super(pos, state); }
}
