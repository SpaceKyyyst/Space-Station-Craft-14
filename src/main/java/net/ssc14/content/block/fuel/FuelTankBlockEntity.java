package net.ssc14.content.block.fuel;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.ZeroSlotContainerBlockEntity;

/** Canonical fuel tank state holder. Fuel remains in persistent block-entity data. */
public class FuelTankBlockEntity extends ZeroSlotContainerBlockEntity {
    public FuelTankBlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14FuelBlockEntities.FUEL_TANK.get(), pos, state, "fuel_tank", "Fuel Tank");
    }
}
