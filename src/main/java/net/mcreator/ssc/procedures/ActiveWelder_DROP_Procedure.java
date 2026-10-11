
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;

import net.mcreator.ssc.init.Ssc14ModItems;

public class ActiveWelder_DROP_Procedure {
	public static void execute(LevelAccessor world, double x, double y, double z, ItemStack itemstack) {
		if (itemstack.isEmpty()) return;

		// Запоминаем и вытаскиваем точный остаток топлива и все кастомные теги выброшенного предмета
		CustomData oldData = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
		CompoundTag tagCopy = oldData.copyTag();
		double savedFuel = tagCopy.getDoubleOr("fuel", 10001.0);

		// Создаем выключенный сварочный аппарат для спавна на полу
		ItemStack droppedDeactivatedWelder = new ItemStack(Ssc14ModItems.WELDING.get());
		
		// Переносим абсолютно весь слепок NBT данных (чтобы улучшения и кастомные теги не стерлись)
		if (!oldData.isEmpty()) {
			droppedDeactivatedWelder.set(DataComponents.CUSTOM_DATA, CustomData.of(tagCopy));
		}
		droppedDeactivatedWelder.setCount(1);

		// Уничтожаем зажжённую сварку в руках/инвентаре, предотвращая дюпы
		itemstack.shrink(1);

		// Спавним выключенную сварку со всеми сохраненными данными на пол космической станции
		if (world instanceof ServerLevel serverLevel) {
			ItemEntity entityToSpawn = new ItemEntity(serverLevel, x, y, z, droppedDeactivatedWelder);
			entityToSpawn.setPickUpDelay(10); // Стандартная задержка поднятия предмета игроком
			entityToSpawn.setUnlimitedLifetime();
			serverLevel.addFreshEntity(entityToSpawn);
		}
	}
}
