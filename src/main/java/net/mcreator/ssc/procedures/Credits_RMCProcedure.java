package net.mcreator.ssc.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.ssc.init.Ssc14ModItems;

public class Credits_RMCProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (Ssc14ModItems.CREDITS.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()) {
			if (50 == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount()) {
				(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount());
				if (entity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(Ssc14ModItems.CREDITS_50.get()).copy();
					_setstack.setCount(1);
					_player.getInventory().placeItemBackInInventory(_setstack);
				}
			}
		}
	}
}