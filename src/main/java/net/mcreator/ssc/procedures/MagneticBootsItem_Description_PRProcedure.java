package net.mcreator.ssc.procedures;

import net.minecraft.world.item.ItemStack;
import net.ssc14.content.item.equipment.MagneticBootsItem;

/** @deprecated Use {@link MagneticBootsItem#description(ItemStack)}. */
@Deprecated(forRemoval = false)
public class MagneticBootsItem_Description_PRProcedure {
	public static String execute(ItemStack itemstack) {
		return MagneticBootsItem.description(itemstack);
	}
}
