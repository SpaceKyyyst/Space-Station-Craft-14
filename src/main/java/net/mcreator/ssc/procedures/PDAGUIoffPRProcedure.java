package net.mcreator.ssc.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.ssc.init.Ssc14ModItems;

public class PDAGUIoffPRProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (!(Ssc14ModItems.PD_APASSANGER.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem())) {
			if (entity instanceof Player _player)
				_player.closeContainer();
		}
	}
}