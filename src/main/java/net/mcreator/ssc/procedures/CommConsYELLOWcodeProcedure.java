
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.Identifier;

import net.mcreator.ssc.network.Ssc14ModVariables;
import net.ssc14.content.communication.StationAlertService;

public class CommConsYELLOWcodeProcedure {
	public static void execute(LevelAccessor world) {
		if (world instanceof ServerLevel level) {
			var state = Ssc14ModVariables.MapVariables.get(level);
			if (StationAlertService.announce(level, state.station_code, state.station_name, 2, 0xe6b917,
					Identifier.parse("ssc_14:blue_code"), "Код ЖЁЛТЫЙ!\nНа станции выявлена структурная или атмосферная угроза. "
							+ "Инженерный отдел уполномочен координировать ликвидацию последствий. Дополнительные инструкции указаны в КПК.")) {
				state.station_code = 2;
				state.markSyncDirty();
			}
		}
	}
}
