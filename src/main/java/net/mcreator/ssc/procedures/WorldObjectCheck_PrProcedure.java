package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.ssc.init.Ssc14ModAttributes;

public class WorldObjectCheck_PrProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Ssc14ModAttributes.OPEN_WORLD_CHECK_MENU))
			_livingEntity0.getAttribute(Ssc14ModAttributes.OPEN_WORLD_CHECK_MENU).setBaseValue(1);
	}
}