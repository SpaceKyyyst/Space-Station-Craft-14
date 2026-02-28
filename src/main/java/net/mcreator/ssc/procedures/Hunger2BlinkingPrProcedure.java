package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.ssc.init.Ssc14ModAttributes;

public class Hunger2BlinkingPrProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		long tick = entity.level().getGameTime();
		long phase = tick % 20;
		if ((tick % 20) < 10) {
			return 0;
		} else if (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS)) {
			return 1;
		}
		return 0;
	}
}