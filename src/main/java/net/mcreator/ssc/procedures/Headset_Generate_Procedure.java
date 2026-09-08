package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.Identifier;
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
			if (itemstack.is(ItemTags.create(Identifier.parse("ssc14:headsets")))) {
				ItemStack _itemStack7 = itemstack;
				if (_itemStack7.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack7)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
					setStackInSlot(_resourceHandler, 1, ItemResource.of(new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_PASSANGER.get())), 1);
				}
				if (Ssc14ModItems.HEADSET_CENTCOM.get() == itemstack.getItem() || Ssc14ModItems.HEADSET_COMMAND.get() == itemstack.getItem()) {
					ItemStack _itemStack13 = itemstack;
					if (_itemStack13.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack13)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 2, ItemResource.of(new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_COMMAND.get())), 1);
					}
					ItemStack _itemStack15 = itemstack;
					if (_itemStack15.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack15)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 3, ItemResource.of(new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_ENGENEER.get())), 1);
					}
					ItemStack _itemStack17 = itemstack;
					if (_itemStack17.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack17)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 4, ItemResource.of(new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_MEDICAL.get())), 1);
					}
					ItemStack _itemStack19 = itemstack;
					if (_itemStack19.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack19)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 5, ItemResource.of(new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_RND.get())), 1);
					}
					ItemStack _itemStack21 = itemstack;
					if (_itemStack21.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack21)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 6, ItemResource.of(new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_SECURITY.get())), 1);
					}
					ItemStack _itemStack23 = itemstack;
					if (_itemStack23.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack23)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 7, ItemResource.of(new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_SERVICE.get())), 1);
					}
					ItemStack _itemStack25 = itemstack;
					if (_itemStack25.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack25)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 8, ItemResource.of(new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_CARGO.get())), 1);
					}
				} else if (Ssc14ModItems.HEADSET_BRIGMEDIC.get() == itemstack.getItem()) {
					ItemStack _itemStack29 = itemstack;
					if (_itemStack29.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack29)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 4, ItemResource.of(new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_MEDICAL.get())), 1);
					}
					ItemStack _itemStack31 = itemstack;
					if (_itemStack31.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack31)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 6, ItemResource.of(new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_SECURITY.get())), 1);
					}
				} else if (Ssc14ModItems.HEADSET_MEDICAL_SCIENCE.get() == itemstack.getItem()) {
					ItemStack _itemStack35 = itemstack;
					if (_itemStack35.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack35)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 4, ItemResource.of(new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_MEDICAL.get())), 1);
					}
					ItemStack _itemStack37 = itemstack;
					if (_itemStack37.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack37)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 5, ItemResource.of(new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_RND.get())), 1);
					}
				} else if (Ssc14ModItems.HEADSET_SECURITY.get() == itemstack.getItem()) {
					ItemStack _itemStack41 = itemstack;
					if (_itemStack41.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack41)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 6, ItemResource.of(new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_SECURITY.get())), 1);
					}
				} else if (Ssc14ModItems.HEADSET_MINING.get() == itemstack.getItem() || Ssc14ModItems.HEADSET_CARGO.get() == itemstack.getItem()) {
					ItemStack _itemStack47 = itemstack;
					if (_itemStack47.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack47)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 8, ItemResource.of(new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_CARGO.get())), 1);
					}
				} else if (Ssc14ModItems.HEADSET_SCIENCE.get() == itemstack.getItem() || Ssc14ModItems.HEADSET_ROBOTICS.get() == itemstack.getItem()) {
					ItemStack _itemStack53 = itemstack;
					if (_itemStack53.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack53)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 5, ItemResource.of(new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_RND.get())), 1);
					}
				} else if (Ssc14ModItems.HEADSET_ENGINEERING.get() == itemstack.getItem()) {
					ItemStack _itemStack57 = itemstack;
					if (_itemStack57.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack57)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 3, ItemResource.of(new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_ENGENEER.get())), 1);
					}
				} else if (Ssc14ModItems.HEADSET_MEDICAL.get() == itemstack.getItem()) {
					ItemStack _itemStack61 = itemstack;
					if (_itemStack61.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack61)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 4, ItemResource.of(new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_MEDICAL.get())), 1);
					}
				} else if (Ssc14ModItems.HEADSET_SERVICE.get() == itemstack.getItem()) {
					ItemStack _itemStack65 = itemstack;
					if (_itemStack65.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack65)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 7, ItemResource.of(new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_SERVICE.get())), 1);
					}
				}
			}
		}
	}

	private static void setStackInSlot(ResourceHandler<ItemResource> handler, int index, ItemResource resource, int amount) {
		try (var tx = Transaction.openRoot()) {
			if (!handler.getResource(index).isEmpty())
				handler.extract(index, handler.getResource(index), handler.getAmountAsInt(index), tx);
			if (!resource.isEmpty() && amount > 0)
				handler.insert(index, resource, amount, tx);
			tx.commit();
		}
	}
}