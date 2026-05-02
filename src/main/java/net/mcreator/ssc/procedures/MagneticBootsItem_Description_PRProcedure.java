package net.mcreator.ssc.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

import net.mcreator.ssc.init.Ssc14ModItems;

public class MagneticBootsItem_Description_PRProcedure {
	public static String execute(ItemStack itemstack) {
		String bufertext = "";
		if (itemstack.getItem() == Ssc14ModItems.MAGNETIC_BOOTS_ITEM.get()) {
			bufertext = Component.translatable("translation.key.magboots_description_off").getString();
		} else if (itemstack.getItem() == Ssc14ModItems.MAGNETIC_BOOTS_ACTIVE_ITEM.get()) {
			bufertext = Component.translatable("translation.key.magboots_description_on").getString();
		}
		return bufertext;
	}
}