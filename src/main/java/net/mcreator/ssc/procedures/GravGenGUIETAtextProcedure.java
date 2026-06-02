
package net.mcreator.ssc.procedures;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class GravGenGUIETAtextProcedure {
	public static String execute(LevelAccessor world, double x, double y, double z) {
		BlockPos pos = BlockPos.containing(x, y, z);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity == null) {
			return "00:00";
		}

		CompoundTag tag = blockEntity.getPersistentData();
		
		int energy = tag.getInt("b_energy").orElse(0);
		boolean active = tag.getBoolean("active").orElse(false);

		// Ограничиваем энергию диапазоном [0, 2000]
		energy = Math.max(0, Math.min(2000, energy));

		int totalSeconds;
		if (active) {
			// РАЗРЯДКА: показываем, сколько времени УЖЕ прошло с начала разрядки
			// Началось с 2000, сейчас energy → потрачено (2000 - energy)
			totalSeconds = (2000 - energy) / 20;
		} else {
			// ЗАРЯДКА: показываем, сколько времени УЖЕ прошло с начала зарядки
			// Началось с 0, сейчас energy → накоплено energy
			totalSeconds = energy / 20;
		}

		// Защита от выхода за пределы 0-100 секунд
		totalSeconds = Math.max(0, Math.min(100, totalSeconds));

		// Показываем N/D на границах (по желанию можно убрать)
		if (energy == 0 || energy == 2000) {
			return "N/D";
		}

		int minutes = totalSeconds / 60;
		int seconds = totalSeconds % 60;

		return String.format("%02d:%02d", minutes, seconds);
	}
}
