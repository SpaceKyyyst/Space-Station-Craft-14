package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.network.chat.Component;

import net.mcreator.ssc.network.Ssc14ModVariables;

public class PDAtextDangerLevel2RProcedure {
	public static String execute(LevelAccessor world) {
		String bufer_t = "";
		if (4 == Ssc14ModVariables.MapVariables.get(world).station_code) {
			bufer_t = Component.translatable("translation.key.pda_text_danger_level_red").getString();
		}
		return bufer_t;
	}
}