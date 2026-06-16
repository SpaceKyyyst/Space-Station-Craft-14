
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;
import net.mcreator.ssc.AtmosphereManager;
import net.mcreator.ssc.AtmosCell;
import net.mcreator.ssc.GasType;

public class GasProducerO2_tick_PR_Procedure {
    
    // ⚙️ НАСТРАИВАЕМЫЕ ПАРАМЕТРЫ ГЕНЕРАТОРА
    /** Максимальное количество молей газа в ячейке (лимит заполнения) */
    private static final float MAX_MOLES_PER_CELL = 1000f;
    
    /** Количество молей O2, производимых за один тик (вызов процедуры) */
    private static final float O2_PRODUCTION_RATE = 100f;
    
    public static void execute(LevelAccessor world, double x, double y, double z) {
        // ✅ Логика атмосферы работает только на сервере
        if (world == null || world.isClientSide()) {
            return;
        }
        
        // Получаем позицию блока
        BlockPos pos = BlockPos.containing(x, y, z);
        
        // Получаем менеджер атмосферы и ячейку
        AtmosphereManager manager = AtmosphereManager.get(
            (net.minecraft.server.level.ServerLevel) world
        );
        AtmosCell cell = manager.getCellAt(pos);
        
        if (cell == null) {
            return;
        }
        
        // Проверяем общее количество молей в ячейке
        float totalMoles = cell.getTotalMoles();
        
        // Если не превышен лимит — производим кислород
        if (totalMoles < MAX_MOLES_PER_CELL) {
            // Добавляем кислород (GasType.OXYGEN.ordinal() = 0)
            cell.addMoles(GasType.OXYGEN.ordinal(), O2_PRODUCTION_RATE);
        }
    }
}
