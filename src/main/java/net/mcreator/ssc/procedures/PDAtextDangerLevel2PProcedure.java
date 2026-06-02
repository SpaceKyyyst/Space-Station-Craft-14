package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.network.chat.Component;

import net.mcreator.ssc.network.Ssc14ModVariables;

public class PDAtextDangerLevel2PProcedure {
	public static String execute(LevelAccessor world) {
		String bufer_t = "";
		if (5 == Ssc14ModVariables.MapVariables.get(world).station_code) {
			bufer_t = Component.translatable("translation.key.pda_text_danger_level_purple").getString();
		}
		return bufer_t;
	}
}