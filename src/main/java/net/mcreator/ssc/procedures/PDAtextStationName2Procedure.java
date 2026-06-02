package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.ssc.network.Ssc14ModVariables;

public class PDAtextStationName2Procedure {
	public static String execute(LevelAccessor world) {
		String bufer_t = "";
		if (("").equals(Ssc14ModVariables.MapVariables.get(world).station_name)) {
			bufer_t = "N/D";
		} else {
			bufer_t = Ssc14ModVariables.MapVariables.get(world).station_name;
		}
		return bufer_t;
	}
}