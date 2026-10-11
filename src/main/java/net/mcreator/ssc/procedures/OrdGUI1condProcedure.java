
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class OrdGUI1condProcedure {
	// Теперь мы передаем третьим аргументом ID вкладки, которую проверяем!
	public static boolean checkTab(LevelAccessor world, double x, double y, double z, int tabId) {
		BlockEntity blockEntity = world.getBlockEntity(BlockPos.containing(x, y, z));
		if (blockEntity != null) {
			return blockEntity.getPersistentData().getDoubleOr("TAB", 0) == tabId;
		}
		return false;
	}
}
