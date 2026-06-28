
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.mcreator.ssc.GasType;
import net.mcreator.ssc.AtmosphereManager;
import net.mcreator.ssc.AtmosCell;

import javax.annotation.Nullable;

public class O2textRedactProcedure {

	// 📝 Этот метод вызывается из UI/оверлея. 
	// ⚠️ ВАЖНО: UI рендерится на КЛИЕНТЕ. Атмосфера считается только на СЕРВЕРЕ.
	// Без сетевого пакета этот код на клиенте не увидит реальные значения.
	public static String execute(LevelAccessor world, double x, double y, double z) {
		BlockPos pos = BlockPos.containing(x, y, z);
		
		// Если вызов идёт из клиентского интерфейса → данные недоступны напрямую
		if (world.isClientSide()) {
			return "Sync: N/A"; // Для UI нужен сетевой пакет (см. пояснение ниже)
		}

		if (world instanceof ServerLevel serverLevel) {
			// getCellAt автоматически создаст регион, если его ещё нет
			AtmosCell cell = AtmosphereManager.get(serverLevel).getCellAt(pos);
			
			if (cell != null) {
				float o2 = cell.getMoles(GasType.OXYGEN.ordinal());
				// Красивое форматирование: 2.5, 0.0, 15.3 и т.д.
				return String.format("%.1f", o2);
			}
		}
		return "-";
	}
}
