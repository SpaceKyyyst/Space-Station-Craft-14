package net.ssc14.content.block.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/** Low-voltage APC storage with breaker and overload state. */
public class ApcBlockEntity extends AbstractEnergyStorageBlockEntity {
    public static final long MAX_ENERGY = 2_500_000L;
    public static final int TRIP_TICKS_THRESHOLD = 60;
    public static final long MAX_LOAD_WATT = 20_000L;
    private boolean mainBreakerEnabled = true;
    private boolean tripFlag;
    private int overloadTicks;
    public long lastTickReceivingJoules;
    public long lastTickDischargingJoules;

    public ApcBlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14EnergyMachineBlockEntities.APC.get(), pos, state,
                MAX_ENERGY, "apc", "APC");
    }

    public void serverTick() {
        if (level == null || level.isClientSide()) return;
        if (mainBreakerEnabled && getNetworkIdealConsumption() > MAX_LOAD_WATT) {
            overloadTicks++;
            if (overloadTicks >= TRIP_TICKS_THRESHOLD) {
                tripFlag = true;
                mainBreakerEnabled = false;
                overloadTicks = 0;
            }
        } else {
            overloadTicks = Math.max(0, overloadTicks - 1);
        }
        BlockState state = getBlockState();
        int target = tripFlag || !mainBreakerEnabled || getStoredEnergy() == 0 ? 1 : 0;
        if (state.hasProperty(ApcBlock.BLOCKSTATE)
                && state.getValue(ApcBlock.BLOCKSTATE) != target) {
            level.setBlock(worldPosition, state.setValue(ApcBlock.BLOCKSTATE, target), 3);
        }
    }

    public boolean isMainBreakerEnabled() { return mainBreakerEnabled; }
    public void setMainBreakerEnabled(boolean enabled) {
        mainBreakerEnabled = enabled;
        if (enabled) tripFlag = false;
        setChanged();
    }
    public boolean isTripFlag() { return tripFlag; }

    @Override public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        mainBreakerEnabled = input.getBooleanOr("mainBreaker", true);
        tripFlag = input.getBooleanOr("tripFlag", false);
        overloadTicks = Math.clamp(input.getIntOr("overloadTicks", 0),
                0, TRIP_TICKS_THRESHOLD);
    }
    @Override public void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putBoolean("mainBreaker", mainBreakerEnabled);
        output.putBoolean("tripFlag", tripFlag);
        output.putInt("overloadTicks", overloadTicks);
    }
}
