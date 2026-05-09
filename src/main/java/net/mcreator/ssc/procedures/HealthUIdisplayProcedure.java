package net.mcreator.ssc.procedures;

import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

public class HealthUIdisplayProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!(entity instanceof Player _plr0 && _plr0.gameMode() == GameType.CREATIVE || entity instanceof Player _plr1 && _plr1.gameMode() == GameType.SPECTATOR)) {
			return true;
		}
		return false;
	}
}