package net.ssc14.content.block.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** SMES inventory block entity; energy simulation remains owned by the server subsystem. */
public class SmesBlockEntity extends AbstractEnergyMachineContainerBlockEntity {
    public SmesBlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14EnergyMachineBlockEntities.SMES.get(), pos, state, "smes", "SMES");
    }
}
