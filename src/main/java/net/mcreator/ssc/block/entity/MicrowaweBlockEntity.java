package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use the canonical machinery content type. */
@Deprecated(forRemoval = false)
public class MicrowaweBlockEntity extends net.ssc14.content.block.machinery.MicrowaveBlockEntity {
    public MicrowaweBlockEntity(BlockPos pos, BlockState state) { super(pos, state); }
}
