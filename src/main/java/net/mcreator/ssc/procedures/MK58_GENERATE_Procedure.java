package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;

import net.mcreator.ssc.init.Ssc14ModItems;

public class MK58_GENERATE_Procedure {
	public static void execute(ItemStack itemstack) {
		if (!itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("GenEraTE", false)) {
			{
				final String _tagName = "GenEraTE";
				final boolean _tagValue = true;
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
			}
			if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
				ItemStack _setstack = new ItemStack(Ssc14ModItems.BULLET_35ITEM.get()).copy();
				_setstack.setCount(1);
				_modHandlerItemSetSlot.setStackInSlot(0, _setstack);
			}
			if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
				ItemStack _setstack = new ItemStack(Ssc14ModItems.MAGAZINE_PISTOL_35.get()).copy();
				_setstack.setCount(1);
				_modHandlerItemSetSlot.setStackInSlot(1, _setstack);
			}
			{
				final String _tagName = "Bullets";
				final double _tagValue = 10;
				CustomData.update(DataComponents.CUSTOM_DATA, (getItemStackFromItemStackSlot(1, itemstack)), tag -> tag.putDouble(_tagName, _tagValue));
			}
		}
	}

	private static ItemStack getItemStackFromItemStackSlot(int slotID, ItemStack itemStack) {
		IItemHandler itemHandler = itemStack.getCapability(Capabilities.ItemHandler.ITEM, null);
		if (itemHandler != null)
			return itemHandler.getStackInSlot(slotID).copy();
		return ItemStack.EMPTY;
	}
}