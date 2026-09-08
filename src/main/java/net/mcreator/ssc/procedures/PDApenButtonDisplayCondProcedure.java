package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

public class PDApenButtonDisplayCondProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!((getItemStackFromItemStackSlot(1, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))).getItem() == ItemStack.EMPTY.getItem())) {
			return true;
		}
		return false;
	}

	private static ItemStack getItemStackFromItemStackSlot(int slotID, ItemStack itemStack) {
		ResourceHandler<ItemResource> itemHandler = itemStack.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(itemStack));
		if (itemHandler != null)
			return ItemUtil.getStack(itemHandler, slotID);
		return ItemStack.EMPTY;
	}
}