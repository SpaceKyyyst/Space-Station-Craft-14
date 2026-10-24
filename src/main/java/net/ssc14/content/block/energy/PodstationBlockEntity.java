package net.ssc14.content.block.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

/** Medium/high-voltage substation storage. */
public class PodstationBlockEntity extends AbstractEnergyStorageBlockEntity {
    public static final long MAX_ENERGY = 15_000_000L;

    public PodstationBlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14EnergyMachineBlockEntities.PODSTATION.get(), pos, state,
                MAX_ENERGY, "podstation", "Podstation");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return super.createMenu(id, inventory);
    }
}
