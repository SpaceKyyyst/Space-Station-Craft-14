package net.mcreator.ssc.energy;

import net.minecraft.server.level.ServerLevel;
import net.ssc14.content.energy.EnergyNetwork;

/** @deprecated Use {@link net.ssc14.content.energy.EnergyTelemetry}. */
@Deprecated
public final class EnergyTelemetry {
    private EnergyTelemetry() { }
    public static void publish(ServerLevel level, EnergyNetwork network) {
        net.ssc14.content.energy.EnergyTelemetry.publish(level, network);
    }
}
