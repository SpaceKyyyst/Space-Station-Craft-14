package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.ResourceHandler;
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
		ResourceHandler<ItemResource> itemHandler = itemStack.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(itemStack));
		if (itemHandler != null)
			return ItemUtil.getStack(itemHandler, slotID);
		return ItemStack.EMPTY;
	}
}