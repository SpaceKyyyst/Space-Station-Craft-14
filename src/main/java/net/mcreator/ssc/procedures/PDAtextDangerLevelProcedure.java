package net.mcreator.ssc.procedures;

import net.minecraft.network.chat.Component;

public class PDAtextDangerLevelProcedure {
	public static String execute() {
		String bufer_t = "";
		return Component.translatable("translation.key.pda_text_danger_level").getString();
	}
}