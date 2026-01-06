package net.mcreator.ssc.procedures;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;

public class BaseUplinkItemDiamondSwordProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		double Price = 0;
		double Balance = 0;
		Price = 4;
		if (entity instanceof LivingEntity _entity) {
			ItemStack _setstack0 = new ItemStack(Items.DIAMOND_SWORD).copy();
			_setstack0.setCount(1);
			_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack0);
			if (_entity instanceof Player _player)
				_player.getInventory().setChanged();
		}
		entity.getPersistentData().putDouble("UplinkBalance", (Balance - Price));
		return "Price: " + Price;
	}
}