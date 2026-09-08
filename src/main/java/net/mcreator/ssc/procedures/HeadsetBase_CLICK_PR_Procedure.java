package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.Identifier;

import net.mcreator.ssc.init.Ssc14ModItems;

public class HeadsetBase_CLICK_PR_Procedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double i = 0;
		i = 0;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ItemTags.create(Identifier.parse("ssc14:headsets")))
				&& (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).is(ItemTags.create(Identifier.parse("ssc14:encryption_keys")))) {
			if (Ssc14ModItems.ENCRYPTION_KEY_PASSANGER.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem()) {
				if (!(Ssc14ModItems.ENCRYPTION_KEY_PASSANGER.get() == (getItemStackFromItemStackSlot(1, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))).getItem())) {
					ItemStack _itemStack11 = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
					if (_itemStack11.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack11)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 1, ItemResource.of((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY)), 1);
					}
					(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).shrink(1);
				}
			} else if (Ssc14ModItems.ENCRYPTION_KEY_COMMAND.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem()) {
				if (!(Ssc14ModItems.ENCRYPTION_KEY_COMMAND.get() == (getItemStackFromItemStackSlot(2, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))).getItem())) {
					ItemStack _itemStack21 = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
					if (_itemStack21.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack21)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 2, ItemResource.of((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY)), 1);
					}
					(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).shrink(1);
				}
			} else if (Ssc14ModItems.ENCRYPTION_KEY_ENGENEER.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem()) {
				if (!(Ssc14ModItems.ENCRYPTION_KEY_ENGENEER.get() == (getItemStackFromItemStackSlot(3, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))).getItem())) {
					ItemStack _itemStack31 = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
					if (_itemStack31.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack31)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 3, ItemResource.of((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY)), 1);
					}
					(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).shrink(1);
				}
			} else if (Ssc14ModItems.ENCRYPTION_KEY_MEDICAL.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem()) {
				if (!(Ssc14ModItems.ENCRYPTION_KEY_MEDICAL.get() == (getItemStackFromItemStackSlot(4, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))).getItem())) {
					ItemStack _itemStack41 = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
					if (_itemStack41.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack41)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 4, ItemResource.of((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY)), 1);
					}
					(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).shrink(1);
				}
			} else if (Ssc14ModItems.ENCRYPTION_KEY_RND.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem()) {
				if (!(Ssc14ModItems.ENCRYPTION_KEY_RND.get() == (getItemStackFromItemStackSlot(5, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))).getItem())) {
					ItemStack _itemStack51 = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
					if (_itemStack51.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack51)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 5, ItemResource.of((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY)), 1);
					}
					(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).shrink(1);
				}
			} else if (Ssc14ModItems.ENCRYPTION_KEY_SECURITY.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem()) {
				if (!(Ssc14ModItems.ENCRYPTION_KEY_SECURITY.get() == (getItemStackFromItemStackSlot(6, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))).getItem())) {
					ItemStack _itemStack61 = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
					if (_itemStack61.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack61)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 6, ItemResource.of((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY)), 1);
					}
					(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).shrink(1);
				}
			} else if (Ssc14ModItems.ENCRYPTION_KEY_SERVICE.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem()) {
				if (!(Ssc14ModItems.ENCRYPTION_KEY_SERVICE.get() == (getItemStackFromItemStackSlot(7, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))).getItem())) {
					ItemStack _itemStack71 = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
					if (_itemStack71.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack71)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 7, ItemResource.of((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY)), 1);
					}
					(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).shrink(1);
				}
			} else if (Ssc14ModItems.ENCRYPTION_KEY_CARGO.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem()) {
				if (!(Ssc14ModItems.ENCRYPTION_KEY_CARGO.get() == (getItemStackFromItemStackSlot(8, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))).getItem())) {
					ItemStack _itemStack81 = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
					if (_itemStack81.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack81)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, 8, ItemResource.of((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY)), 1);
					}
					(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).shrink(1);
				}
			}
		} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ItemTags.create(Identifier.parse("ssc14:headsets")))
				&& Ssc14ModItems.SCREWDRIVER.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem()) {
			for (int index1 = 0; index1 < 15; index1++) {
				i = i + 1;
				if (0 != (getItemStackFromItemStackSlot((int) i, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))).getCount()) {
					if (world instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY()), (entity.getZ()),
								(getItemStackFromItemStackSlot((int) i, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY))));
						entityToSpawn.setPickUpDelay(0);
						entityToSpawn.setUnlimitedLifetime();
						_level.addFreshEntity(entityToSpawn);
					}
					ItemStack _itemStack98 = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
					if (_itemStack98.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(_itemStack98)) instanceof ResourceHandler<ItemResource> _resourceHandler) {
						setStackInSlot(_resourceHandler, (int) i, ItemResource.of(new ItemStack(Ssc14ModItems.ENCRYPTION_KEY_PASSANGER.get())), -1);
					}
				}
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