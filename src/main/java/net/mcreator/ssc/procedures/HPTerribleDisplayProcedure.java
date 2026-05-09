package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.ssc.init.Ssc14ModAttributes;

public class HPTerribleDisplayProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if ((entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Ssc14ModAttributes.HEALTH_U_IATTRIBUTE) ? _livingEntity0.getAttribute(Ssc14ModAttributes.HEALTH_U_IATTRIBUTE).getValue() : 0) == 4) {
			return true;
		}
		return false;
	}
}