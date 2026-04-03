package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

public class KostilniblokPKMProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player) {
			if (entity instanceof net.minecraft.server.level.ServerPlayer) {
				net.minecraft.server.level.ServerPlayer serverPlayer = (net.minecraft.server.level.ServerPlayer) entity;
				net.minecraft.network.chat.Component message = net.minecraft.network.chat.Component.literal("SSC14 Debug: Критический сбой в работе мода. Reason: Костыли.");
				serverPlayer.connection.disconnect(message);
			}
		}
	}
}