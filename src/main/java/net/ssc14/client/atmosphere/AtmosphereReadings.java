package net.ssc14.client.atmosphere;

import java.util.Optional;
import net.ssc14.content.atmosphere.runtime.ServerAtmosphereReadings;
import net.ssc14.integration.neoforge.atmosphere.network.GasAnalyzerSnapshotMessage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

/** Provides the same readings to server callers and the client analyser overlay. */
public final class AtmosphereReadings {
    private AtmosphereReadings() { }

    public static Optional<GasAnalyzerSnapshotMessage> read(LevelAccessor world, double x, double y, double z) {
        if (world instanceof ServerLevel level) {
            return ServerAtmosphereReadings.read(level, x, y, z);
        }
        return world instanceof Level level ? GasAnalyzerClientCache.get(level) : Optional.empty();
    }
}
