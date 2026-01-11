package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerLevel;

import net.mcreator.ssc.init.Ssc14ModItems;

public class EnergyBar_OpeningProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		itemstack.shrink(1);
		if (entity instanceof LivingEntity _entity) {
			ItemStack _setstack2 = new ItemStack(Ssc14ModItems.ENERGY_BAR_2.get()).copy();
			_setstack2.setCount(1);
			_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack2);
			if (_entity instanceof Player _player)
				_player.getInventory().setChanged();
		}
		if (world instanceof ServerLevel _level) {
			ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(Ssc14ModItems.ENERGY_BAR_TRASH.get()));
			entityToSpawn.setPickUpDelay(1);
			entityToSpawn.setUnlimitedLifetime();
			_level.addFreshEntity(entityToSpawn);
		}
	}
}