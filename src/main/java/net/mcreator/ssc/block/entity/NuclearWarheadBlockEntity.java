package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use {@link net.ssc14.content.block.explosives.NuclearWarheadBlockEntity}. */
@Deprecated(forRemoval = false)
public class NuclearWarheadBlockEntity
        extends net.ssc14.content.block.explosives.NuclearWarheadBlockEntity {
    public NuclearWarheadBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }
}
