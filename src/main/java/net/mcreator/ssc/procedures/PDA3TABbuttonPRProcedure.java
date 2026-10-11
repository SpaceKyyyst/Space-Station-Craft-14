package net.mcreator.ssc.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;

public class PDA3TABbuttonPRProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			final String _tagName = "Tab";
			final double _tagValue = 3;
			CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
		}
	}
}