package net.ssc14.content.block.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** Debug high-voltage source inventory and topology lifecycle. */
public class DebugGeneratorBlockEntity extends AbstractEnergyMachineContainerBlockEntity {
    public DebugGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14EnergyMachineBlockEntities.DEBU_GGENERATOR.get(), pos, state,
                "debu_ggenerator", "DEBUG generator [1000 W]");
    }

    @Override public void onLoad() {
        super.onLoad();
        CableTopologyService.queueUpdate(level, worldPosition);
    }
    @Override public void setRemoved() {
        super.setRemoved();
        CableTopologyService.queueUpdate(level, worldPosition);
    }
    @Override public void onChunkUnloaded() {
        super.onChunkUnloaded();
        CableTopologyService.queueUpdate(level, worldPosition);
    }
}
