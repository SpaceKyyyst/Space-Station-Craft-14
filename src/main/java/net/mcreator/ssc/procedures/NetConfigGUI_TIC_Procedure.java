
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;

import net.mcreator.ssc.init.Ssc14ModItems;

public class NetConfigGUI_TIC_Procedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		
		// Проверяем, держит ли игрок конфигуратор в руке
		ItemStack mainHand = entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
		
		if (Ssc14ModItems.NETWORK_CONFIGURATOR.get() != mainHand.getItem()) {
			// Если конфигуратор больше не в руке, закрываем GUI
			if (entity instanceof Player _player) {
				_player.closeContainer();
			}
		}
	}
}
