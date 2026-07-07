package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;

public class MK58_Breech_Open_Procedure {
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		double BulletChamber = 0;
		double MagazBullets = 0;
		if (itemstack.getItem() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()) {
			BulletChamber = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("BulletChamber", 0);
			MagazBullets = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("MagazBullets", 0);
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack, 20);
			if (Ssc14ModItems.MK_58.get() == itemstack.getItem() && !entity.isShiftKeyDown()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:guns_bolt_open")), SoundSource.MASTER, 1,
								(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05));
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:guns_bolt_open")), SoundSource.MASTER, 1,
								(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05), false);
					}
				}
				itemstack.shrink(1);
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack19 = new ItemStack(Ssc14ModItems.MK_58OPEN.get()).copy();
					_setstack19.setCount(1);
					_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack19);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				{
					final String _tagName = "GenEraTE";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putBoolean(_tagName, _tagValue));
				}
				{
					final String _tagName = "MagazBullets";
					final double _tagValue = MagazBullets;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
				}
				if (0 < BulletChamber) {
					if (world instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY() + entity.getBbHeight() / 3d), (entity.getZ()), new ItemStack(Ssc14ModItems.BULLET_35ITEM.get()));
						entityToSpawn.setPickUpDelay(0);
						entityToSpawn.setUnlimitedLifetime();
						_level.addFreshEntity(entityToSpawn);
					}
				}
			} else if (Ssc14ModItems.MK_58.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() && entity.isShiftKeyDown()) {
				if (ItemStack.EMPTY.getItem() == (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem()) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:items_pistol_mag_out")), SoundSource.MASTER, 1,
									(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05));
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:items_pistol_mag_out")), SoundSource.MASTER, 1,
									(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05), false);
						}
					}
					if (0 < itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("MagazBullets", 0)) {
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack42 = new ItemStack(Ssc14ModItems.MAGAZINE_PISTOL_35.get()).copy();
							_setstack42.setCount(1);
							_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack42);
							if (_entity instanceof Player _player)
								_player.getInventory().setChanged();
						}
						{
							final String _tagName = "GenEraTE";
							final boolean _tagValue = true;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY), tag -> tag.putBoolean(_tagName, _tagValue));
						}
						{
							final String _tagName = "Bullets";
							final double _tagValue = MagazBullets;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
						}
					} else {
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack47 = new ItemStack(Ssc14ModItems.MAGAZINE_PISTOL_35EMPTY.get()).copy();
							_setstack47.setCount(1);
							_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack47);
							if (_entity instanceof Player _player)
								_player.getInventory().setChanged();
						}
					}
					itemstack.shrink(1);
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack50 = new ItemStack(Ssc14ModItems.MK_58NOMAGZ.get()).copy();
						_setstack50.setCount(1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack50);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					{
						final String _tagName = "BulletChamber";
						final double _tagValue = BulletChamber;
						CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
					}
					{
						final String _tagName = "GenEraTE";
						final boolean _tagValue = true;
						CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putBoolean(_tagName, _tagValue));
					}
				}
			} else if (Ssc14ModItems.MK_58OPEN.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() && !entity.isShiftKeyDown()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:guns_bolt_closed")), SoundSource.MASTER, 1,
								(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05));
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:guns_bolt_closed")), SoundSource.MASTER, 1,
								(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05), false);
					}
				}
				itemstack.shrink(1);
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack65 = new ItemStack(Ssc14ModItems.MK_58.get()).copy();
					_setstack65.setCount(1);
					_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack65);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				{
					final String _tagName = "GenEraTE";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putBoolean(_tagName, _tagValue));
				}
				if (0 < MagazBullets) {
					{
						final String _tagName = "BulletChamber";
						final double _tagValue = 1;
						CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
					}
					{
						final String _tagName = "MagazBullets";
						final double _tagValue = (MagazBullets - 1);
						CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
					}
				}
			} else if (Ssc14ModItems.MK_58OPEN.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() && entity.isShiftKeyDown()) {
				if (ItemStack.EMPTY.getItem() == (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem()) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:items_pistol_mag_out")), SoundSource.MASTER, 1,
									(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05));
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:items_pistol_mag_out")), SoundSource.MASTER, 1,
									(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05), false);
						}
					}
					if (0 < itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("MagazBullets", 0)) {
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack85 = new ItemStack(Ssc14ModItems.MAGAZINE_PISTOL_35.get()).copy();
							_setstack85.setCount(1);
							_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack85);
							if (_entity instanceof Player _player)
								_player.getInventory().setChanged();
						}
						{
							final String _tagName = "Bullets";
							final double _tagValue = MagazBullets;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
						}
					} else {
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack88 = new ItemStack(Ssc14ModItems.MAGAZINE_PISTOL_35EMPTY.get()).copy();
							_setstack88.setCount(1);
							_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack88);
							if (_entity instanceof Player _player)
								_player.getInventory().setChanged();
						}
					}
					itemstack.shrink(1);
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack91 = new ItemStack(Ssc14ModItems.MK_58OPEN_NO_MAGZ.get()).copy();
						_setstack91.setCount(1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack91);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					{
						final String _tagName = "BulletChamber";
						final double _tagValue = BulletChamber;
						CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
					}
					{
						final String _tagName = "GenEraTE";
						final boolean _tagValue = true;
						CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putBoolean(_tagName, _tagValue));
					}
				}
			} else if (Ssc14ModItems.MK_58NOMAGZ.get() == itemstack.getItem() && !entity.isShiftKeyDown()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:guns_bolt_open")), SoundSource.MASTER, 1,
								(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05));
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:guns_bolt_open")), SoundSource.MASTER, 1,
								(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05), false);
					}
				}
				itemstack.shrink(1);
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack106 = new ItemStack(Ssc14ModItems.MK_58OPEN_NO_MAGZ.get()).copy();
					_setstack106.setCount(1);
					_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack106);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				{
					final String _tagName = "GenEraTE";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putBoolean(_tagName, _tagValue));
				}
				if (0 < BulletChamber) {
					if (world instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY() + entity.getBbHeight() / 3d), (entity.getZ()), new ItemStack(Ssc14ModItems.BULLET_35ITEM.get()));
						entityToSpawn.setPickUpDelay(0);
						entityToSpawn.setUnlimitedLifetime();
						_level.addFreshEntity(entityToSpawn);
					}
				}
			} else if (Ssc14ModItems.MK_58NOMAGZ.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() && entity.isShiftKeyDown()) {
				if (Ssc14ModItems.MAGAZINE_PISTOL_35.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem()
						|| Ssc14ModItems.MAGAZINE_PISTOL_35EMPTY.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem()) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:items_pistol_mag_in")), SoundSource.MASTER, 1,
									(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05));
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:items_pistol_mag_in")), SoundSource.MASTER, 1,
									(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05), false);
						}
					}
					itemstack.shrink(1);
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack128 = new ItemStack(Ssc14ModItems.MK_58.get()).copy();
						_setstack128.setCount(1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack128);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					{
						final String _tagName = "GenEraTE";
						final boolean _tagValue = true;
						CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putBoolean(_tagName, _tagValue));
					}
					{
						final String _tagName = "BulletChamber";
						final double _tagValue = BulletChamber;
						CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
					}
					{
						final String _tagName = "MagazBullets";
						final double _tagValue = ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Bullets", 0));
						CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
					}
					(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).shrink(1);
				}
			} else if (Ssc14ModItems.MK_58OPEN_NO_MAGZ.get() == itemstack.getItem() && !entity.isShiftKeyDown()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:guns_bolt_closed")), SoundSource.MASTER, 1,
								(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05));
					} else {
						_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:guns_bolt_closed")), SoundSource.MASTER, 1,
								(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05), false);
					}
				}
				itemstack.shrink(1);
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack149 = new ItemStack(Ssc14ModItems.MK_58NOMAGZ.get()).copy();
					_setstack149.setCount(1);
					_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack149);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				{
					final String _tagName = "GenEraTE";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putBoolean(_tagName, _tagValue));
				}
				if (0 < BulletChamber) {
					if (world instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY() + entity.getBbHeight() / 3d), (entity.getZ()), new ItemStack(Ssc14ModItems.BULLET_35ITEM.get()));
						entityToSpawn.setPickUpDelay(0);
						entityToSpawn.setUnlimitedLifetime();
						_level.addFreshEntity(entityToSpawn);
					}
				}
			} else if (Ssc14ModItems.MK_58OPEN_NO_MAGZ.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() && entity.isShiftKeyDown()) {
				if (Ssc14ModItems.MAGAZINE_PISTOL_35.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem()
						|| Ssc14ModItems.MAGAZINE_PISTOL_35EMPTY.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem()) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:items_pistol_mag_in")), SoundSource.MASTER, 1,
									(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05));
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:items_pistol_mag_in")), SoundSource.MASTER, 1,
									(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05), false);
						}
					}
					itemstack.shrink(1);
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack171 = new ItemStack(Ssc14ModItems.MK_58OPEN.get()).copy();
						_setstack171.setCount(1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack171);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					{
						final String _tagName = "GenEraTE";
						final boolean _tagValue = true;
						CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putBoolean(_tagName, _tagValue));
					}
					{
						final String _tagName = "BulletChamber";
						final double _tagValue = BulletChamber;
						CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
					}
					{
						final String _tagName = "MagazBullets";
						final double _tagValue = ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Bullets", 0));
						CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
					}
					(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).shrink(1);
				}
			}
		}
	}
}