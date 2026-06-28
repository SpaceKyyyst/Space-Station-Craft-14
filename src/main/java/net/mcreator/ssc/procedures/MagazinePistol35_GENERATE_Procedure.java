package net.mcreator.ssc.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;

public class MagazinePistol35_GENERATE_Procedure {
	public static void execute(ItemStack itemstack) {
		if (!itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("GenEraTE", false)) {
			{
				final String _tagName = "GenEraTE";
				final boolean _tagValue = true;
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
			}
			{
				final String _tagName = "Bullets";
				final double _tagValue = 10;
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
			}
		}
	}
}