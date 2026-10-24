package net.ssc14.content.block.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/** Shared persisted storage and inventory behavior for stationary energy machines. */
public abstract class AbstractEnergyStorageBlockEntity
        extends AbstractEnergyMachineContainerBlockEntity implements EnergyStorage {
    public static final int SLOT_COUNT = AbstractEnergyMachineContainerBlockEntity.SLOT_COUNT;
    private final long maxEnergy;
    private long storedEnergy;
    private long netCurrent;
    private long netSupply;
    private long netConsume;

    protected AbstractEnergyStorageBlockEntity(BlockEntityType<?> type, BlockPos pos,
            BlockState state, long maxEnergy, String defaultName, String displayName) {
        super(type, pos, state, defaultName, displayName);
        this.maxEnergy = maxEnergy;
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

    @Override public long getStoredEnergy() { return storedEnergy; }
    @Override public void setStoredEnergy(long joules) {
        long next = Math.clamp(joules, 0, maxEnergy);
        if (storedEnergy == next) return;
        storedEnergy = next;
        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }
    @Override public void setStoredEnergySimulation(long joules) {
        long next = Math.clamp(joules, 0, maxEnergy);
        if (storedEnergy == next) return;
        storedEnergy = next;
        setChanged();
    }
    @Override public long getMaxEnergy() { return maxEnergy; }
    @Override public long getNetworkCurrentPower() { return netCurrent; }
    @Override public void setNetworkCurrentPower(long watt) { netCurrent = watt; setChanged(); }
    @Override public long getNetworkTheoreticalSupply() { return netSupply; }
    @Override public void setNetworkTheoreticalSupply(long watt) { netSupply = watt; }
    @Override public long getNetworkIdealConsumption() { return netConsume; }
    @Override public void setNetworkIdealConsumption(long watt) { netConsume = watt; }

    @Override public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        storedEnergy = Math.clamp(input.getLongOr("storedEnergy", 0L), 0, maxEnergy);
        netCurrent = input.getLongOr("netCurrent", 0L);
        netSupply = input.getLongOr("netSupply", 0L);
        netConsume = input.getLongOr("netConsume", 0L);
    }

    @Override public void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putLong("storedEnergy", storedEnergy);
        output.putLong("netCurrent", netCurrent);
        output.putLong("netSupply", netSupply);
        output.putLong("netConsume", netConsume);
    }

}
