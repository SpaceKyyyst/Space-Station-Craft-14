
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModMenus;
import net.mcreator.ssc.CargoRegistry;

import java.util.List;

public class OrderGUI_update_TICKProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;

		int currentTab = (int) getBlockNBTNumber(world, BlockPos.containing(x, y, z), "TAB");

		if (entity instanceof Player _player && _player.containerMenu instanceof Ssc14ModMenus.MenuAccessor _menu) {
			List<CargoRegistry.CargoItem> tabItems = CargoRegistry.getItemsForTab(currentTab);

			for (int slotId = 0; slotId < 10; slotId++) {
				if (slotId < tabItems.size()) {
					ItemStack guiStack = tabItems.get(slotId).getStack();
					guiStack.setCount(1);
					_menu.getSlots().get(slotId).set(guiStack);
				} else {
					_menu.getSlots().get(slotId).set(ItemStack.EMPTY);
				}
			}
			// Возвращаем каноничное мгновенное обновление слотов
			_player.containerMenu.broadcastChanges();
		}
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDoubleOr(tag, 0);
		return -1;
	}
}
