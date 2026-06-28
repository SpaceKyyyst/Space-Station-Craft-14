
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.ModReagents;

public class Container_Opening_PR_Procedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;

		// ===== МУКА =====
		if (Ssc14ModItems.FLOUR.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()) {
			if (!(world instanceof Level level) || level.isClientSide()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:package_opening")), SoundSource.MASTER, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:package_opening")), SoundSource.MASTER, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
					}
				}
			}
			itemstack.shrink(1);
			if (entity instanceof LivingEntity _entity) {
				ItemStack _setstack6 = new ItemStack(Ssc14ModItems.FLOUR_OPEN.get()).copy();
				_setstack6.setCount(1);
				_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack6);
				if (_entity instanceof Player _player)
					_player.getInventory().setChanged();

				// === ЗАПОЛНЕНИЕ ЖИДКОСТЬЮ (внутри блока if) ===
				ModReagents.addReagent(_setstack6, "flour", 50);
			}
		}
		// ===== САХАР =====
		else if (Ssc14ModItems.SUGAR.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()) {
			if (!(world instanceof Level level) || level.isClientSide()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:package_opening")), SoundSource.MASTER, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:package_opening")), SoundSource.MASTER, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
					}
				}
			}
			itemstack.shrink(1);
			if (entity instanceof LivingEntity _entity) {
				ItemStack _setstack13 = new ItemStack(Ssc14ModItems.SUGAR_OPEN.get()).copy();
				_setstack13.setCount(1);
				_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack13);
				if (_entity instanceof Player _player)
					_player.getInventory().setChanged();

				// === ЗАПОЛНЕНИЕ ЖИДКОСТЬЮ (внутри блока if) ===
				ModReagents.addReagent(_setstack13, "sugar", 50);
			}
		}
		// ===== МОЛОКО =====
		else if (Ssc14ModItems.MILK.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()) {
			if (!(world instanceof Level level) || level.isClientSide()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:items_bottle_open")), SoundSource.MASTER, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:items_bottle_open")), SoundSource.MASTER, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
					}
				}
			}
			itemstack.shrink(1);
			if (entity instanceof LivingEntity _entity) {
				ItemStack _setstack20 = new ItemStack(Ssc14ModItems.MILK_OPEN.get()).copy();
				_setstack20.setCount(1);
				_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack20);
				if (_entity instanceof Player _player)
					_player.getInventory().setChanged();

				// === ЗАПОЛНЕНИЕ ЖИДКОСТЬЮ (внутри блока if) ===
				ModReagents.addReagent(_setstack20, "milk", 100);
			}
		}
		// ===== УНИВЕРСАЛЬНЫЙ ФЕРМЕНТ =====
		else if (Ssc14ModItems.ALL_PURPOSE_SAUCE.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()) {
			if (!(world instanceof Level level) || level.isClientSide()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:items_bottle_open")), SoundSource.MASTER, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:items_bottle_open")), SoundSource.MASTER, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
					}
				}
			}
			itemstack.shrink(1);
			if (entity instanceof LivingEntity _entity) {
				ItemStack _setstack27 = new ItemStack(Ssc14ModItems.ALL_PURPOSE_SAUCE_OPEN.get()).copy();
				_setstack27.setCount(1);
				_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack27);
				if (_entity instanceof Player _player)
					_player.getInventory().setChanged();

				// === ЗАПОЛНЕНИЕ ЖИДКОСТЬЮ (внутри блока if) ===
				ModReagents.addReagent(_setstack27, "enzyme", 30);
			}
		}
	}
}
