package net.ssc14.content.medical;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.ssc14.content.attribute.Ssc14Attributes;

public final class ActionProgressService {
	public static boolean execute(Entity entity) {
		return isAtStep(entity, 1);
	}

	public static boolean isAtStep(Entity entity, double step) {
		if (entity == null)
			return false;
		if ((entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Ssc14Attributes.PROGRESS_BAR_ATRB) ? _livingEntity0.getAttribute(Ssc14Attributes.PROGRESS_BAR_ATRB).getValue() : 0) == step) {
			return true;
		}
		return false;
	}
}
