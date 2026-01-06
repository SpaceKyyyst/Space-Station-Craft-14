package net.mcreator.ssc.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;

import net.mcreator.ssc.procedures.IDCard_Spawn_IdentificateProcedure;

import javax.annotation.Nullable;

public class IDCardChimicItem extends Item {
	public IDCardChimicItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}

	@Override
	public void inventoryTick(ItemStack itemstack, ServerLevel world, Entity entity, @Nullable EquipmentSlot equipmentSlot) {
		super.inventoryTick(itemstack, world, entity, equipmentSlot);
		IDCard_Spawn_IdentificateProcedure.execute(entity, itemstack);
	}
}