
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.network.Ssc14ModVariables;
import net.mcreator.ssc.block.EmergencyLightBlock;

public class EmergLightCodeInstallProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		if (blockstate == null) return;
		
		int currentGlobalCode = (int) Ssc14ModVariables.MapVariables.get(world).station_code;
		// Вычисляем целевое состояние блока (код станции минус 1)
		int targetBlockValue = currentGlobalCode - 1;

		// Защита диапазона (чтобы не выйти за пределы IntegerProperty 0-7)
		if (targetBlockValue < 0) targetBlockValue = 0;
		if (targetBlockValue > 7) targetBlockValue = 7;

		// Напрямую проверяем свойство без ресурсоемкого поиска по строкам
		if (blockstate.hasProperty(EmergencyLightBlock.ST_CODE)) {
			int currentBlockValue = blockstate.getValue(EmergencyLightBlock.ST_CODE);
			
			if (currentBlockValue != targetBlockValue) {
				BlockPos _pos = BlockPos.containing(x, y, z);
				world.setBlock(_pos, blockstate.setValue(EmergencyLightBlock.ST_CODE, targetBlockValue), 3);
			}
		}
	}
}
