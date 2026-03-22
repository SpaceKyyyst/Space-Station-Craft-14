package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;

public class MacFrameGUItext2Procedure {
	public static String execute(LevelAccessor world, double x, double y, double z) {
		String buftext = "";
		if (Ssc14ModItems.SUBSTATION_BOARD.get() == (itemFromBlockInventory(world, BlockPos.containing(x, y, z), 0).copy()).getItem()) {
			buftext = "x5 \u0421\u0412 \u043A\u0430\u0431\u0435\u043B\u044C";
		} else if (Ssc14ModItems.SMES_BOARD.get() == (itemFromBlockInventory(world, BlockPos.containing(x, y, z), 0).copy()).getItem()) {
			buftext = "x1 \u041A\u043E\u043D\u0434\u0435\u043D\u0441\u0430\u0442\u043E\u0440";
		} else {
			buftext = "";
		}
		return buftext;
	}

	private static ItemStack itemFromBlockInventory(LevelAccessor world, BlockPos pos, int slot) {
		if (world instanceof ILevelExtension ext) {
			IItemHandler itemHandler = ext.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
			if (itemHandler != null)
				return itemHandler.getStackInSlot(slot);
		}
		return ItemStack.EMPTY;
	}
}