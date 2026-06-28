package net.mcreator.ssc.procedures;

import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.ssc.init.Ssc14ModAttributes;

public class Hunger1PrProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if ((entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS) ? _livingEntity0.getAttribute(Ssc14ModAttributes.NUTRIENTS).getValue() : 0) >= 50
				&& (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS) ? _livingEntity1.getAttribute(Ssc14ModAttributes.NUTRIENTS).getValue() : 0) < 100
				&& !(entity instanceof Player _plr2 && _plr2.gameMode() == GameType.CREATIVE || entity instanceof Player _plr3 && _plr3.gameMode() == GameType.SPECTATOR)) {
			return true;
		}
		return false;
	}
}