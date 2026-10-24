package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use {@link net.ssc14.content.block.fuel.FuelTankBlockEntity}. */
@Deprecated(forRemoval = false)
public class FuelTankBlockEntity extends net.ssc14.content.block.fuel.FuelTankBlockEntity {
    public FuelTankBlockEntity(BlockPos pos, BlockState state) { super(pos, state); }
}
