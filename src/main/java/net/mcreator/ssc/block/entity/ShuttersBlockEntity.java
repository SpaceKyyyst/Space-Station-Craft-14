package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use {@link net.ssc14.content.block.airlock.ShuttersBlockEntity}. */
@Deprecated(forRemoval = false)
public class ShuttersBlockEntity
        extends net.ssc14.content.block.airlock.ShuttersBlockEntity {
    public ShuttersBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }
}
