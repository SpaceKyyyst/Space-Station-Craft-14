package net.ssc14.content.block.explosives;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** Canonical inventory owned by the C4 charge block. */
public class C4ChargeBlockEntity extends AbstractExplosiveContainerBlockEntity {
    public C4ChargeBlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14ExplosiveBlockEntities.C_4.get(), "c_4", "C4", pos, state);
    }
}
