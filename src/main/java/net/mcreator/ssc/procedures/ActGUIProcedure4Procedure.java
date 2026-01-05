package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.ssc.init.Ssc14ModAttributes;

public class ActGUIProcedure4Procedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if ((entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB) ? _livingEntity0.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).getValue() : 0) == 4) {
			return true;
		}
		return false;
	}
}