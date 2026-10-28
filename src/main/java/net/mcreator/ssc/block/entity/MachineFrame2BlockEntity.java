package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use {@link net.ssc14.content.block.machinery.MachineFrameBlockEntity}. */
@Deprecated(forRemoval = false)
public class MachineFrame2BlockEntity
        extends net.ssc14.content.block.machinery.MachineFrameBlockEntity {
    public MachineFrame2BlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }
}
