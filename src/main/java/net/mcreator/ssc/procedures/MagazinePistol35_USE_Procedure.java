package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.component.DataComponents;

import net.mcreator.ssc.init.Ssc14ModItems;

public class MagazinePistol35_USE_Procedure {
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		double bufer = 0;
		if (Ssc14ModItems.MAGAZINE_PISTOL_35.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()
				|| Ssc14ModItems.MAGAZINE_PISTOL_35EMPTY.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()) {
			if (Ssc14ModItems.BULLET_35ITEM.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem()) {
				if (10 > itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Bullets", 0)) {
					(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).shrink(1);
					{
						final String _tagName = "Bullets";
						final double _tagValue = (1 + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Bullets", 0));
						CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
					}
					if (entity instanceof Player _player)
						_player.getCooldowns().addCooldown(itemstack, 5);
				}
			} else if (ItemStack.EMPTY.getItem() == (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem()) {
				if (0 < itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Bullets", 0)) {
					{
						final String _tagName = "Bullets";
						final double _tagValue = (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Bullets", 0) - 1);
						CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
					}
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack25 = new ItemStack(Ssc14ModItems.BULLET_35ITEM.get()).copy();
						_setstack25.setCount(1);
						_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack25);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					if (entity instanceof Player _player)
						_player.getCooldowns().addCooldown(itemstack, 5);
				}
			} else {
				if (0 < itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Bullets", 0)) {
					{
						final String _tagName = "Bullets";
						final double _tagValue = (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Bullets", 0) - 1);
						CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
					}
					if (world instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY() + entity.getBbHeight() / 3d), (entity.getZ()), new ItemStack(Ssc14ModItems.BULLET_35ITEM.get()));
						entityToSpawn.setPickUpDelay(0);
						entityToSpawn.setUnlimitedLifetime();
						_level.addFreshEntity(entityToSpawn);
					}
					if (entity instanceof Player _player)
						_player.getCooldowns().addCooldown(itemstack, 5);
				}
			}
			if (Ssc14ModItems.MAGAZINE_PISTOL_35.get() == itemstack.getItem() && 0 == itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Bullets", 0)) {
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack45 = new ItemStack(Ssc14ModItems.MAGAZINE_PISTOL_35EMPTY.get()).copy();
					_setstack45.setCount(1);
					_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack45);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
			} else if (Ssc14ModItems.MAGAZINE_PISTOL_35EMPTY.get() == itemstack.getItem() && 0 != itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Bullets", 0)) {
				itemstack.shrink(1);
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack52 = new ItemStack(Ssc14ModItems.MAGAZINE_PISTOL_35.get()).copy();
					_setstack52.setCount(1);
					_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack52);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				{
					final String _tagName = "GenEraTE";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
				}
				{
					final String _tagName = "Bullets";
					final double _tagValue = 1;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
				}
			}
		}
	}
}