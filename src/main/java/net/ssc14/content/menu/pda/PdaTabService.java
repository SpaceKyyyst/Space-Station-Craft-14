package net.ssc14.content.menu.pda;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;

public final class PdaTabService {
	public static void execute(Entity entity) {
		setTab(entity, 2);
	}

	public static void setTab(Entity entity, double tab) {
		if (entity == null)
			return;
		{
			final String _tagName = "Tab";
			final double _tagValue = tab;
			CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
		}
	}
}
