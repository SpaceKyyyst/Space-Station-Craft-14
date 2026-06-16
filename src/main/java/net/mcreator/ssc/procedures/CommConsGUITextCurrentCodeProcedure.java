package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.ssc.network.Ssc14ModVariables;

public class CommConsGUITextCurrentCodeProcedure {
	public static String execute(LevelAccessor world) {
		String b_text = "";
		if (Ssc14ModVariables.MapVariables.get(world).station_code == 1) {
			b_text = "\u0417\u0435\u043B\u0451\u043D\u044B\u0439";
		} else if (Ssc14ModVariables.MapVariables.get(world).station_code == 2) {
			b_text = "\u0416\u0451\u043B\u0442\u044B\u0439";
		} else if (Ssc14ModVariables.MapVariables.get(world).station_code == 3) {
			b_text = "\u0421\u0438\u043D\u0438\u0439";
		} else if (Ssc14ModVariables.MapVariables.get(world).station_code == 4) {
			b_text = "\u041A\u0440\u0430\u0441\u043D\u044B\u0439";
		} else if (Ssc14ModVariables.MapVariables.get(world).station_code == 5) {
			b_text = "\u0424\u0438\u043E\u043B\u0435\u0442\u043E\u0432\u044B\u0439";
		} else if (Ssc14ModVariables.MapVariables.get(world).station_code == 6) {
			b_text = "\u0413\u0430\u043C\u043C\u0430";
		} else if (Ssc14ModVariables.MapVariables.get(world).station_code == 7) {
			b_text = "\u0414\u0435\u043B\u044C\u0442\u0430";
		} else if (Ssc14ModVariables.MapVariables.get(world).station_code == 8) {
			b_text = "\u042D\u043F\u0441\u0438\u043B\u043E\u043D";
		} else {
			b_text = "N/D";
		}
		return "\u041A\u043E\u0434:  " + b_text;
	}
}