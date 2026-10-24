package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use {@link net.ssc14.content.block.commerce.OrderPalletBlockEntity}. */
@Deprecated(forRemoval = false)
public class OrderPalletBlockEntity
        extends net.ssc14.content.block.commerce.OrderPalletBlockEntity {
    public OrderPalletBlockEntity(BlockPos pos, BlockState state) { super(pos, state); }
}
