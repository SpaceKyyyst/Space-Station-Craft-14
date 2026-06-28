package net.mcreator.ssc.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;

public class MagazinePistol35_Description_Procedure {
	public static String execute(ItemStack itemstack) {
		return "" + "\u041F\u0430\u0442\u0440\u043E\u043D\u044B:  " + Math.round(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Bullets", 0)) + "/10";
	}
}