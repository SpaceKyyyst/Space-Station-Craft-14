
package net.mcreator.ssc.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;

public class NetworkConfigurator_DROP_Procedure {
	public static void execute(ItemStack itemstack) {
		// Полностью удаляем все теги при выбрасывании
		CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> {
			tag.remove("First_Target_X");
			tag.remove("First_Target_Y");
			tag.remove("First_Target_Z");
			tag.remove("First_Target_Type");
			tag.remove("Sec_Target_X");
			tag.remove("Sec_Target_Y");
			tag.remove("Sec_Target_Z");
		});
	}
}
