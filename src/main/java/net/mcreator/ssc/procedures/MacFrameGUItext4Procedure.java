package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;

public class MacFrameGUItext4Procedure {
	public static String execute(LevelAccessor world, double x, double y, double z) {
		String buftext = "";
		if (Ssc14ModItems.SUBSTATION_BOARD.get() == (itemFromBlockInventory(world, BlockPos.containing(x, y, z), 0).copy()).getItem()) {
			buftext = "x1 \u0411\u0430\u0442\u0430\u0440\u0435\u044F \u043C\u0430\u043B.";
		} else if (Ssc14ModItems.SMES_BOARD.get() == (itemFromBlockInventory(world, BlockPos.containing(x, y, z), 0).copy()).getItem()) {
			buftext = "x4 \u0411\u0430\u0442\u0430\u0440\u0435\u044F \u043C\u0430\u043B.";
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