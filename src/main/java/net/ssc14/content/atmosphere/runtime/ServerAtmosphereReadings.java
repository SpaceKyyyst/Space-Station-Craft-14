package net.ssc14.content.atmosphere.runtime;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.ssc14.common.atmosphere.model.GasType;
import net.ssc14.integration.neoforge.atmosphere.AtmosCell;
import net.ssc14.integration.neoforge.atmosphere.network.GasAnalyzerSnapshotMessage;

/** Builds an analyser snapshot from authoritative server atmosphere state. */
public final class ServerAtmosphereReadings {
    private ServerAtmosphereReadings() { }

    public static Optional<GasAnalyzerSnapshotMessage> read(ServerLevel level, double x, double y, double z) {
        BlockPos pos = BlockPos.containing(x, y, z);
        AtmosCell cell = AtmosphereManager.get(level).getCellAt(pos);
        if (cell == null) {
            return Optional.empty();
        }
        return Optional.of(new GasAnalyzerSnapshotMessage(level.dimension().identifier(), pos,
                cell.getPressure(), cell.getTemperature(), cell.getTotalMoles(),
                cell.getMoles(GasType.OXYGEN.ordinal()), cell.getMoles(GasType.NITROGEN.ordinal())));
    }
}
