package net.mcreator.ssc.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerLevel;

import net.mcreator.ssc.procedures.Headset_Generate_Procedure;
import net.mcreator.ssc.procedures.HeadsetBase_CLICK_PR_Procedure;

import javax.annotation.Nullable;

public class HeadsetBaseItem extends Item {
	public HeadsetBaseItem(Item.Properties properties) {
		super(properties.stacksTo(1).fireResistant());
	}

	@Override
	public InteractionResult use(Level world, Player entity, InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		HeadsetBase_CLICK_PR_Procedure.execute(world, entity);
		return ar;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, ServerLevel world, Entity entity, @Nullable EquipmentSlot equipmentSlot) {
		super.inventoryTick(itemstack, world, entity, equipmentSlot);
		Headset_Generate_Procedure.execute(itemstack);
	}
}