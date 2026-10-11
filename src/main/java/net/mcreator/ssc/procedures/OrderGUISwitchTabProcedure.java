
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;

public class OrderGUISwitchTabProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, int buttonID) {
		if (world == null) return;

		// Проверяем, что это серверная сторона и ID кнопки принадлежит вкладкам (0 - 14)
		if (!world.isClientSide() && buttonID >= 0 && buttonID <= 14) {
			BlockPos _bp = BlockPos.containing(x, y, z);
			BlockEntity _blockEntity = world.getBlockEntity(_bp);
			BlockState _bs = world.getBlockState(_bp);
			
			if (_blockEntity != null) {
				// Записываем номер вкладки: ID кнопки + 1 (кнопка 0 -> TAB 1, кнопка 9 -> TAB 10)
				_blockEntity.getPersistentData().putDouble("TAB", buttonID + 1);
			}
			
			if (world instanceof Level _level) {
				_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
		}
	}
}
