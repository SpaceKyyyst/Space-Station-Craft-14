package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use {@link net.ssc14.content.block.commerce.SalePalletBlockEntity}. */
@Deprecated(forRemoval = false)
public class SalePalletBlockEntity
        extends net.ssc14.content.block.commerce.SalePalletBlockEntity {
    public SalePalletBlockEntity(BlockPos pos, BlockState state) { super(pos, state); }
}
