package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use {@link net.ssc14.content.block.commerce.OrderConsoleBlockEntity}. */
@Deprecated(forRemoval = false)
public class OrderConsoleBlockEntity
        extends net.ssc14.content.block.commerce.OrderConsoleBlockEntity {
    public OrderConsoleBlockEntity(BlockPos position, BlockState state) {
        super(position, state);
    }
}
