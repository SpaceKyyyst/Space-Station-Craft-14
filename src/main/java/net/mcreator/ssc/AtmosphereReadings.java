package net.mcreator.ssc;

import net.ssc14.common.atmosphere.model.GasType;
import java.util.Optional;
import net.mcreator.ssc.network.GasAnalyzerClientCache;
import net.mcreator.ssc.network.GasAnalyzerSnapshotMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

/** Provides the same readings to server callers and the client analyser overlay. */
public final class AtmosphereReadings {
    private AtmosphereReadings() { }

    public static Optional<GasAnalyzerSnapshotMessage> read(LevelAccessor world, double x, double y, double z) {
        if (world instanceof ServerLevel level) {
            BlockPos pos = BlockPos.containing(x, y, z);
            AtmosCell cell = AtmosphereManager.get(level).getCellAt(pos);
            if (cell == null) return Optional.empty();
            return Optional.of(new GasAnalyzerSnapshotMessage(level.dimension().identifier(), pos,
                    cell.getPressure(), cell.getTemperature(), cell.getTotalMoles(),
                    cell.getMoles(GasType.OXYGEN.ordinal()), cell.getMoles(GasType.NITROGEN.ordinal())));
        }
        return world instanceof Level level ? GasAnalyzerClientCache.get(level) : Optional.empty();
    }
}
