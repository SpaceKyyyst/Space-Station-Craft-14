package net.mcreator.ssc.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;

public class PDAlightButtonDisplayCondProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (true == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("flashlight", false)) {
			return true;
		}
		return false;
	}
}