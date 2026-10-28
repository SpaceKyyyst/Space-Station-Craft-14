package net.mcreator.ssc.energy;

import net.minecraft.server.level.ServerLevel;

/** @deprecated Use {@link net.ssc14.content.energy.LampTickBatch}. */
@Deprecated
public final class LampTickBatch {
    private final net.ssc14.content.energy.LampTickBatch delegate;
    public LampTickBatch(ServerLevel level) { delegate = new net.ssc14.content.energy.LampTickBatch(level); }
    public boolean track(net.minecraft.core.BlockPos pos) { return delegate.track(pos); }
    public boolean needsPower(net.minecraft.core.BlockPos pos) { return delegate.needsPower(pos); }
    public void power(net.minecraft.core.BlockPos pos) { delegate.power(pos); }
    public void apply() { delegate.apply(); }
}
