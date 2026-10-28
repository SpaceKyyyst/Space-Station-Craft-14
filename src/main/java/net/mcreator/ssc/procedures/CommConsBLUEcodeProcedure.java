
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.mcreator.ssc.network.Ssc14ModVariables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.Identifier;
import net.ssc14.content.communication.StationAlertService;

public class CommConsBLUEcodeProcedure {
	public static void execute(LevelAccessor world) {
		if (world instanceof ServerLevel level) {
			var state = Ssc14ModVariables.MapVariables.get(level);
			if (StationAlertService.announce(level, state.station_code, state.station_name, 3, 0x1755e6,
					Identifier.parse("ssc_14:blue_code"), "Код СИНИЙ!\nНа станции выявлена угроза безопасности I уровня. "
							+ "Служба безопасности усиливает патрулирование. Дополнительные инструкции указаны в КПК.")) {
				state.station_code = 3;
				state.markSyncDirty();
			}
		}
	}
}
