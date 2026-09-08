package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.ResourceHandler;
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
import net.minecraft.resources.Identifier;

import net.mcreator.ssc.init.Ssc14ModItems;

public class IDRemovePRProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (!((getItemStackFromItemStackSlot(0, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))).getItem() == ItemStack.EMPTY.getItem())) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == ItemStack.EMPTY.getItem()) {
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack9 = (getItemStackFromItemStackSlot(0, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))).copy();
					_setstack9.setCount(1);
					_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack9);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				ItemStack _itemStack11 = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
				if (_itemStack11.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack11)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
					setStackInSlot(_resourceHandler, 0, ItemResource.of(new ItemStack(Ssc14ModItems.ID_CARD_PASSANGER.get())), -1);
				}
			} else {
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY() + 0.05), (entity.getZ()),
							(getItemStackFromItemStackSlot(0, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))));
					entityToSpawn.setPickUpDelay(0);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				ItemStack _itemStack19 = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
				if (_itemStack19.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack19)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
					setStackInSlot(_resourceHandler, 0, ItemResource.of(new ItemStack(Ssc14ModItems.ID_CARD_PASSANGER.get())), -1);
				}
			}
		} else {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).is(ItemTags.create(Identifier.parse("ssc14:id")))) {
				ItemStack _itemStack24 = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
				if (_itemStack24.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack24)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
					setStackInSlot(_resourceHandler, 0, ItemResource.of((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY)), 1);
				}
				(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).shrink(1);
			}
		}
	}

	private static ItemStack getItemStackFromItemStackSlot(int slotID, ItemStack itemStack) {
		ResourceHandler<ItemResource> itemHandler = itemStack.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(itemStack));
		if (itemHandler != null)
			return ItemUtil.getStack(itemHandler, slotID);
		return ItemStack.EMPTY;
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