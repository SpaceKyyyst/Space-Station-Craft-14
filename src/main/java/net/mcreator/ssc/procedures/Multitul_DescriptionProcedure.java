package net.mcreator.ssc.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;

public class Multitul_DescriptionProcedure {
	public static String execute(ItemStack itemstack) {
		if (1 == itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Mode", 0)) {
			return "\u0422\u0435\u043A\u0443\u0449\u0438\u0439 \u0440\u0435\u0436\u0438\u043C:  \u041D\u0412";
		} else if (1 == itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Mode", 0)) {
			return "\u0422\u0435\u043A\u0443\u0449\u0438\u0439 \u0440\u0435\u0436\u0438\u043C:  \u0421\u0412";
		} else if (1 == itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Mode", 0)) {
			return "\u0422\u0435\u043A\u0443\u0449\u0438\u0439 \u0440\u0435\u0436\u0438\u043C:  \u0412\u0412";
		}
		return "";
	}
}