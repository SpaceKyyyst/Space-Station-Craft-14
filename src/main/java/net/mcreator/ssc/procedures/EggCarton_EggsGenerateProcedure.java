package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;

import net.mcreator.ssc.init.Ssc14ModItems;

public class EggCarton_EggsGenerateProcedure {
	public static void execute(ItemStack itemstack) {
		double i = 0;
		if (false == itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("generated", false)) {
			for (int index0 = 0; index0 < 10; index0++) {
				i = i + 1;
				if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
					ItemStack _setstack = new ItemStack(Ssc14ModItems.EGG.get()).copy();
					_setstack.setCount(1);
					_modHandlerItemSetSlot.setStackInSlot((int) i, _setstack);
				}
			}
			{
				final String _tagName = "generated";
				final boolean _tagValue = true;
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
			}
		}
	}
}