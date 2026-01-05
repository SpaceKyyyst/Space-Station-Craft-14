package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.ssc.init.Ssc14ModAttributes;

public class GasAnalyserGUI_pr_1Procedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if ((entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Ssc14ModAttributes.GAS_AN_GU_IATRIB) ? _livingEntity0.getAttribute(Ssc14ModAttributes.GAS_AN_GU_IATRIB).getValue() : 0) == 1) {
			return true;
		}
		return false;
	}
}