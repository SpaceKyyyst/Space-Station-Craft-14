package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;

public class PDAtextid2Procedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		String bufer_t = "";
		if (!(ItemStack.EMPTY.getItem() == (getItemStackFromItemStackSlot(0, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))).getItem())) {
			if (("").equals(
					(getItemStackFromItemStackSlot(0, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("ID_NBT_username", ""))
					|| ("").equals((getItemStackFromItemStackSlot(0, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag()
							.getStringOr("ID_NBT_jobname", ""))) {
				bufer_t = "N/D";
			} else {
				bufer_t = ((getItemStackFromItemStackSlot(0, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("ID_NBT_username",
						"")) + ", "
						+ ((getItemStackFromItemStackSlot(0, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("ID_NBT_jobname",
								""));
			}
		} else {
			bufer_t = "N/D";
		}
		return bufer_t;
	}

	private static ItemStack getItemStackFromItemStackSlot(int slotID, ItemStack itemStack) {
		IItemHandler itemHandler = itemStack.getCapability(Capabilities.ItemHandler.ITEM, null);
		if (itemHandler != null)
			return itemHandler.getStackInSlot(slotID).copy();
		return ItemStack.EMPTY;
	}
}