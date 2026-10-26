package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use {@link net.ssc14.content.block.commerce.SaleConsoleBlockEntity}. */
@Deprecated(forRemoval = false)
public class SaleConsoleBlockEntity
        extends net.ssc14.content.block.commerce.SaleConsoleBlockEntity {
    public SaleConsoleBlockEntity(BlockPos position, BlockState state) {
        super(position, state);
    }
}
