package net.mcreator.ssc.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

public class PDAGUIOpeningConditionsProcedure {
	public static boolean execute(Entity entity, ItemStack itemstack) {
		if (entity == null)
			return false;
		if (itemstack.getItem() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()) {
			return true;
		}
		return false;
	}
}