package net.mcreator.ssc.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;

public class AstroAceFP_DescriptionProcedure {
	public static String execute(ItemStack itemstack) {
		return "" + "\u0414\u0440\u043E\u0442\u0438\u043A\u0438:  " + Math.round(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("DartBullets", 0)) + " / 10";
	}
}