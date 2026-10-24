package net.ssc14.content.block.explosives;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** Canonical inventory owned by the nuclear warhead block. */
public class NuclearWarheadBlockEntity extends AbstractExplosiveContainerBlockEntity {
    public NuclearWarheadBlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14ExplosiveBlockEntities.NUCLEAR_WARHEAD.get(),
                "nuclear_warhead", "Nuclear Warhead", pos, state);
    }
}
