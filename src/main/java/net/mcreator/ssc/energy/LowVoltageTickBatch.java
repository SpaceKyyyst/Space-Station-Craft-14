package net.mcreator.ssc.energy;

import net.minecraft.server.level.ServerLevel;

/** @deprecated Use {@link net.ssc14.content.energy.LowVoltageTickBatch}. */
@Deprecated
public final class LowVoltageTickBatch {
    private final net.ssc14.content.energy.LowVoltageTickBatch delegate;
    public LowVoltageTickBatch(ServerLevel level) { delegate = new net.ssc14.content.energy.LowVoltageTickBatch(level); }
    public boolean track(net.minecraft.core.BlockPos pos) { return delegate.track(pos); }
    public boolean needsPower(net.minecraft.core.BlockPos pos) { return delegate.needsPower(pos); }
    public void power(net.minecraft.core.BlockPos pos) { delegate.power(pos); }
    public void apply() { delegate.apply(); }
}
