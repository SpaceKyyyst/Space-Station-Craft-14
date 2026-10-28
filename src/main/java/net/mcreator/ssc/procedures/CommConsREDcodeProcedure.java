
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.ssc14.content.communication.StationAlertService;

public class CommConsREDcodeProcedure {
	public static void execute(LevelAccessor world) {
		if (world instanceof ServerLevel level) StationAlertService.announceRed(level);
	}
}
