package net.mcreator.ssc.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;

public class PDA3TABcondProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (3 == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Tab", 0)) {
			return true;
		}
		return false;
	}
}