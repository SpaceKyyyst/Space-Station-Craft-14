package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.explosives.C4ChargeBlockEntity;

/** @deprecated Use {@link C4ChargeBlockEntity}. */
@Deprecated(forRemoval = false)
public class C4BlockEntity extends C4ChargeBlockEntity {
    public C4BlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }
}
