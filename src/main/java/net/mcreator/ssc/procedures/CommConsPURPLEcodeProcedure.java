
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.Identifier;

import net.mcreator.ssc.network.Ssc14ModVariables;
import net.ssc14.content.communication.StationAlertService;

public class CommConsPURPLEcodeProcedure {
	public static void execute(LevelAccessor world) {
		if (world instanceof ServerLevel level) {
			var state = Ssc14ModVariables.MapVariables.get(level);
			if (StationAlertService.announce(level, state.station_code, state.station_name, 5, 0x9d2896,
					Identifier.parse("ssc_14:blue_code"), "Код ФИОЛЕТОВЫЙ!\nНа станции зафиксирована биологическая угроза. "
							+ "Активирован протокол изоляции. Медицинскому персоналу предписано изолировать членов экипажа с любыми симптомами.")) {
				state.station_code = 5;
				state.markSyncDirty();
			}
		}
	}
}
