package net.mcreator.ssc.procedures;

import net.minecraft.network.chat.Component;

public class PDAtextStationNameProcedure {
	public static String execute() {
		return Component.translatable("translation.key.pda_text_stationname").getString();
	}
}