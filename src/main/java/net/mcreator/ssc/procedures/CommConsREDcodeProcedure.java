
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.ssc14.content.communication.StationAlertService;
import net.mcreator.ssc.network.Ssc14ModVariables;

public class CommConsREDcodeProcedure {
	public static void execute(LevelAccessor world) {
		if (world instanceof ServerLevel level) {
			var state = Ssc14ModVariables.MapVariables.get(level);
			if (StationAlertService.announceRed(level, state.station_code, state.station_name)) {
				state.station_code = 4;
				state.markSyncDirty();
			}
		}
	}
}
