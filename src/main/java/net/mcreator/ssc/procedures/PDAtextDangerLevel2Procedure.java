package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.ssc.network.Ssc14ModVariables;

public class PDAtextDangerLevel2Procedure {
	public static String execute(LevelAccessor world) {
		String bufer_t = "";
		if (0 == Ssc14ModVariables.MapVariables.get(world).station_code) {
			bufer_t = "N/D";
		}
		return bufer_t;
	}
}