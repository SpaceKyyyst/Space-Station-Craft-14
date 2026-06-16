
package net.mcreator.ssc.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;

import net.mcreator.ssc.init.Ssc14ModItems;

public class NetConfigGUI_CLOSE_Procedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		
		ItemStack mainHand = entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
		
		if (Ssc14ModItems.NETWORK_CONFIGURATOR.get() == mainHand.getItem()) {
			// Полностью удаляем все теги при закрытии GUI
			CustomData.update(DataComponents.CUSTOM_DATA, mainHand, tag -> {
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
}
