package net.mcreator.ssc.integration;

import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.mcreator.ssc.network.Ssc14ModVariables;
import net.ssc14.content.communication.StationAlertService;

/** Compatibility boundary for communication-console alert procedures. */
public final class LegacyCommunicationConsoleAdapter {
    private LegacyCommunicationConsoleAdapter() {
    }

    public static void trigger(Level level, int buttonId) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        int targetCode = switch (buttonId) {
            case 0 -> 4;
            case 1 -> 3;
            case 2 -> 5;
            case 3 -> 2;
            case 4 -> 1;
            default -> -1;
        };
        if (targetCode < 0) {
            return;
        }
        var state = Ssc14ModVariables.MapVariables.get(serverLevel);
        if (StationAlertService.announceCode(serverLevel, state.station_code, state.station_name, targetCode)) {
            state.station_code = targetCode;
            state.markSyncDirty();
        }
    }
}
