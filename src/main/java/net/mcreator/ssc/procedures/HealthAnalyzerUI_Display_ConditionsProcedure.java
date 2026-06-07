package net.mcreator.ssc.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.ssc.init.Ssc14ModItems;

public class HealthAnalyzerUI_Display_ConditionsProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (Ssc14ModItems.HEALTH_ANALYZER.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()
				|| Ssc14ModItems.HEALTH_ANALYZER.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem()) {
			return true;
		}
		return false;
	}
}