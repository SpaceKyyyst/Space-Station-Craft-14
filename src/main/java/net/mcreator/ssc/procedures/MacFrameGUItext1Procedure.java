package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;

public class MacFrameGUItext1Procedure {
	public static String execute(LevelAccessor world, double x, double y, double z) {
		String buftext = "";
		if (Ssc14ModItems.SUBSTATION_BOARD.get() == (itemFromBlockInventory(world, BlockPos.containing(x, y, z), 0).copy()).getItem()) {
			buftext = "\u041F\u043E\u0434\u0441\u0442\u0430\u043D\u0446\u0438\u044F";
		} else if (Ssc14ModItems.SMES_BOARD.get() == (itemFromBlockInventory(world, BlockPos.containing(x, y, z), 0).copy()).getItem()) {
			buftext = "\u0421\u041C\u042D\u0421";
		} else {
			buftext = "\u0412\u0421\u0422\u0410\u0412\u042C\u0422\u0415 \u041F\u041B\u0410\u0422\u0423";
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