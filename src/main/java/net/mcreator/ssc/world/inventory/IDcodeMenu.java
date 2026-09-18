package net.mcreator.ssc.world.inventory;

import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.item.VanillaContainerWrapper;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.Container;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.procedures.IDconsole_OPENMENUProcedure;
import net.mcreator.ssc.procedures.ID_Code_CHECKProcedure;
import net.mcreator.ssc.network.IDcodeSlotMessage;
import net.mcreator.ssc.init.Ssc14ModMenus;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

@EventBusSubscriber
public class IDcodeMenu extends AbstractContainerMenu implements Ssc14ModMenus.MenuAccessor {
	public final Map<String, Object> menuState = new HashMap<>() {
		@Override
		public Object put(String key, Object value) {
			if (!this.containsKey(key) && this.size() >= 155)
				return null;
			return super.put(key, value);
		}
	};
	public final Level world;
	public final Player entity;
	public int x, y, z;
	private ContainerLevelAccess access = ContainerLevelAccess.NULL;
	private ResourceHandler<ItemResource> internal;
	private final Map<Integer, Slot> customSlots = new HashMap<>();
	private boolean bound = false;
	private Supplier<Boolean> boundItemMatcher = null;
	private Entity boundEntity = null;
	private BlockEntity boundBlockEntity = null;

	public IDcodeMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		super(Ssc14ModMenus.I_DCODE.get(), id);
		this.entity = inv.player;
		this.world = inv.player.level();
		this.internal = new ItemStacksResourceHandler(2);
		BlockPos pos = null;
		if (extraData != null) {
			pos = extraData.readBlockPos();
			this.x = pos.getX();
			this.y = pos.getY();
			this.z = pos.getZ();
			access = ContainerLevelAccess.create(world, pos);
		}
		if (pos != null) {
			if (extraData.readableBytes() == 1) { // bound to item
				byte hand = extraData.readByte();
				ItemStack itemstack = hand == 0 ? this.entity.getMainHandItem() : this.entity.getOffhandItem();
				this.boundItemMatcher = () -> itemstack == (hand == 0 ? this.entity.getMainHandItem() : this.entity.getOffhandItem());
				ResourceHandler<ItemResource> cap = itemstack.getCapability(Capabilities.Item.ITEM, ItemAccess.forPlayerSlot(this.entity, hand == 0 ? this.entity.getInventory().getSelectedSlot() : Inventory.SLOT_OFFHAND));
				if (cap != null) {
					this.internal = cap;
					this.bound = true;
				}
			} else if (extraData.readableBytes() > 1) { // bound to entity
				extraData.readByte(); // drop padding
				boundEntity = world.getEntity(extraData.readVarInt());
				if (boundEntity != null) {
					ResourceHandler<ItemResource> cap = boundEntity.getCapability(Capabilities.Item.ENTITY);
					if (cap != null) {
						this.internal = cap;
						this.bound = true;
					}
				}
			} else { // might be bound to block
				boundBlockEntity = this.world.getBlockEntity(pos);
				if (boundBlockEntity instanceof BaseContainerBlockEntity baseContainerBlockEntity) {
					this.internal = VanillaContainerWrapper.of(baseContainerBlockEntity);
					this.bound = true;
				}
			}
		}
		this.customSlots.put(0, this.addSlot(new ResourceHandlerSlot(internal, this::setItemInSlot, 0, 87, 163) {
			private final int slot = 0;
			private int x = IDcodeMenu.this.x;
			private int y = IDcodeMenu.this.y;

			@Override
			protected void setStackCopy(ItemStack stack) {
				super.setStackCopy(stack);
				slotChanged(0, 0, 0);
			}

			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.is(ItemTags.create(Identifier.parse("ssc14:id")));
			}
		}));
		this.customSlots.put(1, this.addSlot(new ResourceHandlerSlot(internal, this::setItemInSlot, 1, 87, 199) {
			private final int slot = 1;
			private int x = IDcodeMenu.this.x;
			private int y = IDcodeMenu.this.y;

			@Override
			protected void setStackCopy(ItemStack stack) {
				super.setStackCopy(stack);
				slotChanged(1, 0, 0);
			}

			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.is(ItemTags.create(Identifier.parse("ssc14:id")));
			}
		}));
		for (int si = 0; si < 3; ++si)
			for (int sj = 0; sj < 9; ++sj)
				this.addSlot(new Slot(inv, sj + (si + 1) * 9, 240 + 8 + sj * 18, 71 + 84 + si * 18));
		for (int si = 0; si < 9; ++si)
			this.addSlot(new Slot(inv, si, 240 + 8 + si * 18, 71 + 142));
	}

	private void setItemInSlot(int index, ItemResource resource, int amount) {
		if (internal instanceof ItemStacksResourceHandler handler) {
			handler.set(index, resource, amount);
		} else if (boundBlockEntity instanceof Container container) {
			container.setItem(index, resource.toStack(Math.max(0, amount)));
		} else {
			try (var tx = Transaction.openRoot()) {
				if (!internal.getResource(index).isEmpty())
					internal.extract(index, internal.getResource(index), internal.getAmountAsInt(index), tx);
				if (!resource.isEmpty() && amount > 0)
					internal.insert(index, resource, amount, tx);
				tx.commit();
			}
		}
	}

	@Override
	public boolean stillValid(Player player) {
		if (this.bound) {
			if (this.boundItemMatcher != null)
				return this.boundItemMatcher.get();
			else if (this.boundBlockEntity != null)
				return AbstractContainerMenu.stillValid(this.access, player, this.boundBlockEntity.getBlockState().getBlock());
			else if (this.boundEntity != null)
				return this.boundEntity.isAlive();
		}
		return true;
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (index < 2) {
				if (!this.moveItemStackTo(itemstack1, 2, this.slots.size(), true))
					return ItemStack.EMPTY;
				slot.onQuickCraft(itemstack1, itemstack);
			} else if (!this.moveItemStackTo(itemstack1, 0, 2, false)) {
				if (index < 2 + 27) {
					if (!this.moveItemStackTo(itemstack1, 2 + 27, this.slots.size(), true))
						return ItemStack.EMPTY;
				} else {
					if (!this.moveItemStackTo(itemstack1, 2, 2 + 27, false))
						return ItemStack.EMPTY;
				}
				return ItemStack.EMPTY;
			}
			if (itemstack1.isEmpty()) {
				slot.setByPlayer(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(playerIn, itemstack1);
		}
		return itemstack;
	}

	@Override
	protected boolean moveItemStackTo(ItemStack itemStack, int startSlot, int endSlot, boolean backwards) {
		boolean anythingChanged = false;
		int destSlot = startSlot;
		if (backwards) {
			destSlot = endSlot - 1;
		}
		if (itemStack.isStackable()) {
			while (!itemStack.isEmpty() && (backwards ? destSlot >= startSlot : destSlot < endSlot)) {
				Slot slot = this.slots.get(destSlot);
				ItemStack target = slot.getItem();
				if (slot.mayPlace(target) && !target.isEmpty() && ItemStack.isSameItemSameComponents(itemStack, target)) {
					int totalStack = target.getCount() + itemStack.getCount();
					int maxStackSize = slot.getMaxStackSize(target);
					if (totalStack <= maxStackSize) {
						itemStack.setCount(0);
						target.setCount(totalStack);
						slot.set(target);
						anythingChanged = true;
					} else if (target.getCount() < maxStackSize) {
						itemStack.shrink(maxStackSize - target.getCount());
						target.setCount(maxStackSize);
						slot.set(target);
						anythingChanged = true;
					}
				}
				if (backwards) {
					destSlot--;
				} else {
					destSlot++;
				}
			}
		}
		if (!itemStack.isEmpty()) {
			if (backwards) {
				destSlot = endSlot - 1;
			} else {
				destSlot = startSlot;
			}
			while (backwards ? destSlot >= startSlot : destSlot < endSlot) {
				Slot slotx = this.slots.get(destSlot);
				ItemStack targetx = slotx.getItem();
				if (targetx.isEmpty() && slotx.mayPlace(itemStack)) {
					int maxStackSize = slotx.getMaxStackSize(itemStack);
					slotx.setByPlayer(itemStack.split(Math.min(itemStack.getCount(), maxStackSize)));
					slotx.setChanged();
					anythingChanged = true;
					break;
				}
				if (backwards) {
					destSlot--;
				} else {
					destSlot++;
				}
			}
		}
		return anythingChanged;
	}

	@Override
	public void removed(Player playerIn) {
		super.removed(playerIn);
		if (!bound && playerIn instanceof ServerPlayer serverPlayer) {
			if (!serverPlayer.isAlive() || serverPlayer.hasDisconnected()) {
				for (int j = 0; j < internal.size(); ++j) {
					if (j == 0)
						continue;
					if (j == 1)
						continue;
					playerIn.drop(ItemUtil.getStack(internal, j), false);
					setItemInSlot(j, ItemResource.EMPTY, 0);
				}
			} else {
				for (int i = 0; i < internal.size(); ++i) {
					if (i == 0)
						continue;
					if (i == 1)
						continue;
					playerIn.getInventory().placeItemBackInInventory(ItemUtil.getStack(internal, i));
					setItemInSlot(i, ItemResource.EMPTY, 0);
				}
			}
		}
	}

	private void slotChanged(int slotid, int ctype, int meta) {
		if (this.world != null && this.world.isClientSide()) {
			ClientPacketDistributor.sendToServer(new IDcodeSlotMessage(slotid, x, y, z, ctype, meta));
			IDcodeSlotMessage.handleSlotAction(entity, slotid, ctype, meta, x, y, z);
		}
	}

	@Override
	public Map<Integer, Slot> getSlots() {
		return Collections.unmodifiableMap(customSlots);
	}

	@Override
	public Map<String, Object> getMenuState() {
		return menuState;
	}

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		Player entity = event.getEntity();
		if (entity.containerMenu instanceof IDcodeMenu menu) {
			Level world = menu.world;
			double x = menu.x;
			double y = menu.y;
			double z = menu.z;
			ID_Code_CHECKProcedure.execute(world, x, y, z, entity);
		}
	}

	@SubscribeEvent
	public static void onContainerOpen(PlayerContainerEvent.Open event) {
		Player entity = event.getEntity();
		if (event.getContainer() instanceof IDcodeMenu menu) {
			Level world = menu.world;
			double x = menu.x;
			double y = menu.y;
			double z = menu.z;
			IDconsole_OPENMENUProcedure.execute(world, x, y, z);
		}
	}
}