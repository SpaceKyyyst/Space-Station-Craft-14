package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.component.DataComponents;

import net.mcreator.ssc.init.Ssc14ModItems;

public class Headset_Generate_Procedure {
	public static void execute(ItemStack itemstack) {
		double i = 0;
		if (false == itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("generated", false)) {
			{
				final String _tagName = "generated";
				final boolean _tagValue = true;
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
			}
			if (itemstack.is(ItemTags.create(ResourceLocation.parse("ssc14:headsets")))) {
				if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
					ItemStack _setstack = new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_PASSANGER.get()).copy();
					_setstack.setCount(1);
					_modHandlerItemSetSlot.setStackInSlot(1, _setstack);
				}
				if (Ssc14ModItems.HEADSET_CENTCOM.get() == itemstack.getItem() || Ssc14ModItems.HEADSET_COMMAND.get() == itemstack.getItem()) {
					if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
						ItemStack _setstack = new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_COMMAND.get()).copy();
						_setstack.setCount(1);
						_modHandlerItemSetSlot.setStackInSlot(2, _setstack);
					}
					if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
						ItemStack _setstack = new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_ENGENEER.get()).copy();
						_setstack.setCount(1);
						_modHandlerItemSetSlot.setStackInSlot(3, _setstack);
					}
					if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
						ItemStack _setstack = new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_MEDICAL.get()).copy();
						_setstack.setCount(1);
						_modHandlerItemSetSlot.setStackInSlot(4, _setstack);
					}
					if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
						ItemStack _setstack = new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_RND.get()).copy();
						_setstack.setCount(1);
						_modHandlerItemSetSlot.setStackInSlot(5, _setstack);
					}
					if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
						ItemStack _setstack = new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_SECURITY.get()).copy();
						_setstack.setCount(1);
						_modHandlerItemSetSlot.setStackInSlot(6, _setstack);
					}
					if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
						ItemStack _setstack = new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_SERVICE.get()).copy();
						_setstack.setCount(1);
						_modHandlerItemSetSlot.setStackInSlot(7, _setstack);
					}
					if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
						ItemStack _setstack = new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_CARGO.get()).copy();
						_setstack.setCount(1);
						_modHandlerItemSetSlot.setStackInSlot(8, _setstack);
					}
				} else if (Ssc14ModItems.HEADSET_BRIGMEDIC.get() == itemstack.getItem()) {
					if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
						ItemStack _setstack = new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_MEDICAL.get()).copy();
						_setstack.setCount(1);
						_modHandlerItemSetSlot.setStackInSlot(4, _setstack);
					}
					if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
						ItemStack _setstack = new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_SECURITY.get()).copy();
						_setstack.setCount(1);
						_modHandlerItemSetSlot.setStackInSlot(6, _setstack);
					}
				} else if (Ssc14ModItems.HEADSET_MEDICAL_SCIENCE.get() == itemstack.getItem()) {
					if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
						ItemStack _setstack = new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_MEDICAL.get()).copy();
						_setstack.setCount(1);
						_modHandlerItemSetSlot.setStackInSlot(4, _setstack);
					}
					if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
						ItemStack _setstack = new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_RND.get()).copy();
						_setstack.setCount(1);
						_modHandlerItemSetSlot.setStackInSlot(5, _setstack);
					}
				} else if (Ssc14ModItems.HEADSET_SECURITY.get() == itemstack.getItem()) {
					if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
						ItemStack _setstack = new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_SECURITY.get()).copy();
						_setstack.setCount(1);
						_modHandlerItemSetSlot.setStackInSlot(6, _setstack);
					}
				} else if (Ssc14ModItems.HEADSET_MINING.get() == itemstack.getItem() || Ssc14ModItems.HEADSET_CARGO.get() == itemstack.getItem()) {
					if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
						ItemStack _setstack = new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_CARGO.get()).copy();
						_setstack.setCount(1);
						_modHandlerItemSetSlot.setStackInSlot(8, _setstack);
					}
				} else if (Ssc14ModItems.HEADSET_SCIENCE.get() == itemstack.getItem() || Ssc14ModItems.HEADSET_ROBOTICS.get() == itemstack.getItem()) {
					if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
						ItemStack _setstack = new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_RND.get()).copy();
						_setstack.setCount(1);
						_modHandlerItemSetSlot.setStackInSlot(5, _setstack);
					}
				} else if (Ssc14ModItems.HEADSET_ENGINEERING.get() == itemstack.getItem()) {
					if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
						ItemStack _setstack = new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_ENGENEER.get()).copy();
						_setstack.setCount(1);
						_modHandlerItemSetSlot.setStackInSlot(3, _setstack);
					}
				} else if (Ssc14ModItems.HEADSET_MEDICAL.get() == itemstack.getItem()) {
					if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
						ItemStack _setstack = new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_MEDICAL.get()).copy();
						_setstack.setCount(1);
						_modHandlerItemSetSlot.setStackInSlot(4, _setstack);
					}
				} else if (Ssc14ModItems.HEADSET_SERVICE.get() == itemstack.getItem()) {
					if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
						ItemStack _setstack = new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_SERVICE.get()).copy();
						_setstack.setCount(1);
						_modHandlerItemSetSlot.setStackInSlot(7, _setstack);
					}
				}
			}
		}
	}
}