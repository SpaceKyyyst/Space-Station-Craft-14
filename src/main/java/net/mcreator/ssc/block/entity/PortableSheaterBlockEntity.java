package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use {@link net.ssc14.content.block.machinery.PortableSheaterBlockEntity}. */
@Deprecated(forRemoval = false)
public class PortableSheaterBlockEntity
        extends net.ssc14.content.block.machinery.PortableSheaterBlockEntity {
    public PortableSheaterBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }
}
