package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.ssc.init.Ssc14ModItems;

public class PenRemovePRProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (!((getItemStackFromItemStackSlot(1, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))).getItem() == ItemStack.EMPTY.getItem())) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == ItemStack.EMPTY.getItem()) {
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack9 = (getItemStackFromItemStackSlot(1, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))).copy();
					_setstack9.setCount(1);
					_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack9);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
					ItemStack _setstack = new ItemStack(Ssc14ModItems.PEN.get()).copy();
					_setstack.setCount(-1);
					_modHandlerItemSetSlot.setStackInSlot(1, _setstack);
				}
			} else {
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY() + 0.05), (entity.getZ()),
							(getItemStackFromItemStackSlot(1, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))));
					entityToSpawn.setPickUpDelay(0);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
					ItemStack _setstack = new ItemStack(Ssc14ModItems.PEN.get()).copy();
					_setstack.setCount(-1);
					_modHandlerItemSetSlot.setStackInSlot(1, _setstack);
				}
			}
		} else {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).is(ItemTags.create(ResourceLocation.parse("ssc14:pens")))) {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
					ItemStack _setstack = (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).copy();
					_setstack.setCount(1);
					_modHandlerItemSetSlot.setStackInSlot(1, _setstack);
				}
				(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).shrink(1);
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