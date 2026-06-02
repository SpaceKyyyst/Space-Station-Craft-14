package net.mcreator.ssc.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;

public class RSUNBTtextProcedure {
	public static String execute(ItemStack itemstack) {
		return "\u0417\u0430\u0440\u044F\u0434\u044B: " + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Charges", 0);
	}
}