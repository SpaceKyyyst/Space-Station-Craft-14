package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;

public class DEBUG_item_PR_Procedure {
	public static void execute(LevelAccessor world) {
		if (world instanceof ServerLevel _level) {
			_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("\u041B\u041A\u041C"), false);
		}
	}
}