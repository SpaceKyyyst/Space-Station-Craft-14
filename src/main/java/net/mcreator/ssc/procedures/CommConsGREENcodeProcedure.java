
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.Identifier;

import net.mcreator.ssc.network.Ssc14ModVariables;
import net.ssc14.content.communication.StationAlertService;

public class CommConsGREENcodeProcedure {
	public static void execute(LevelAccessor world) {
		if (world instanceof ServerLevel level) {
			var state = Ssc14ModVariables.MapVariables.get(level);
			if (StationAlertService.announce(level, state.station_code, state.station_name, 1, 0x21a528,
					Identifier.parse("ssc_14:green_code"), "Код ЗЕЛЁНЫЙ!\nОтсутствие угроз безопасности. Станция работает в штатном режиме.")) {
				state.station_code = 1;
				state.markSyncDirty();
			}
		}
	}
}
