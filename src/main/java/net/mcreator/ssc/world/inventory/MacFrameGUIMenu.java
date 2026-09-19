package net.mcreator.ssc.world.inventory;

import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.item.VanillaContainerWrapper;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.capabilities.Capabilities;

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

import net.mcreator.ssc.procedures.MacFrameGUIBlockUpdateProcedure;
import net.mcreator.ssc.network.MacFrameGUISlotMessage;
import net.mcreator.ssc.init.Ssc14ModMenus;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class MacFrameGUIMenu extends AbstractContainerMenu implements Ssc14ModMenus.MenuAccessor {
	public final Map<String, Object> menuState = new HashMap<>() {
		@Override
		public Object put(String key, Object value) {
			if (!this.containsKey(key) && this.size() >= 18)
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

	public MacFrameGUIMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		super(Ssc14ModMenus.MAC_FRAME_GUI.get(), id);
		this.entity = inv.player;
		this.world = inv.player.level();
		this.internal = new ItemStacksResourceHandler(9);
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
		this.customSlots.put(0, this.addSlot(new ResourceHandlerSlot(internal, this::setItemInSlot, 0, 142, 39) {
			private final int slot = 0;
			private int x = MacFrameGUIMenu.this.x;
			private int y = MacFrameGUIMenu.this.y;

			@Override
			protected void setStackCopy(ItemStack stack) {
				super.setStackCopy(stack);
				slotChanged(0, 0, 0);
			}

			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.is(ItemTags.create(Identifier.parse("ssc14:mac_fr_boards")));
			}
		}));
		this.customSlots.put(1, this.addSlot(new ResourceHandlerSlot(internal, this::setItemInSlot, 1, 142, 13) {
			private final int slot = 1;
			private int x = MacFrameGUIMenu.this.x;
			private int y = MacFrameGUIMenu.this.y;

			@Override
			protected void setStackCopy(ItemStack stack) {
				super.setStackCopy(stack);
				slotChanged(1, 0, 0);
			}

			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.is(ItemTags.create(Identifier.parse("ssc14:mac_fr_components")));
			}
		}));
		this.customSlots.put(2, this.addSlot(new ResourceHandlerSlot(internal, this::setItemInSlot, 2, 168, 13) {
			private final int slot = 2;
			private int x = MacFrameGUIMenu.this.x;
			private int y = MacFrameGUIMenu.this.y;

			@Override
			protected void setStackCopy(ItemStack stack) {
				super.setStackCopy(stack);
				slotChanged(2, 0, 0);
			}

			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.is(ItemTags.create(Identifier.parse("ssc14:mac_fr_components")));
			}
		}));
		this.customSlots.put(3, this.addSlot(new ResourceHandlerSlot(internal, this::setItemInSlot, 3, 168, 39) {
			private final int slot = 3;
			private int x = MacFrameGUIMenu.this.x;
			private int y = MacFrameGUIMenu.this.y;

			@Override
			protected void setStackCopy(ItemStack stack) {
				super.setStackCopy(stack);
				slotChanged(3, 0, 0);
			}

			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.is(ItemTags.create(Identifier.parse("ssc14:mac_fr_components")));
			}
		}));
		this.customSlots.put(4, this.addSlot(new ResourceHandlerSlot(internal, this::setItemInSlot, 4, 168, 65) {
			private final int slot = 4;
			private int x = MacFrameGUIMenu.this.x;
			private int y = MacFrameGUIMenu.this.y;

			@Override
			protected void setStackCopy(ItemStack stack) {
				super.setStackCopy(stack);
				slotChanged(4, 0, 0);
			}

			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.is(ItemTags.create(Identifier.parse("ssc14:mac_fr_components")));
			}
		}));
		this.customSlots.put(5, this.addSlot(new ResourceHandlerSlot(internal, this::setItemInSlot, 5, 142, 65) {
			private final int slot = 5;
			private int x = MacFrameGUIMenu.this.x;
			private int y = MacFrameGUIMenu.this.y;

			@Override
			protected void setStackCopy(ItemStack stack) {
				super.setStackCopy(stack);
				slotChanged(5, 0, 0);
			}

			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.is(ItemTags.create(Identifier.parse("ssc14:mac_fr_components")));
			}
		}));
		this.customSlots.put(6, this.addSlot(new ResourceHandlerSlot(internal, this::setItemInSlot, 6, 116, 65) {
			private final int slot = 6;
			private int x = MacFrameGUIMenu.this.x;
			private int y = MacFrameGUIMenu.this.y;

			@Override
			protected void setStackCopy(ItemStack stack) {
				super.setStackCopy(stack);
				slotChanged(6, 0, 0);
			}

			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.is(ItemTags.create(Identifier.parse("ssc14:mac_fr_components")));
			}
		}));
		this.customSlots.put(7, this.addSlot(new ResourceHandlerSlot(internal, this::setItemInSlot, 7, 116, 39) {
			private final int slot = 7;
			private int x = MacFrameGUIMenu.this.x;
			private int y = MacFrameGUIMenu.this.y;

			@Override
			protected void setStackCopy(ItemStack stack) {
				super.setStackCopy(stack);
				slotChanged(7, 0, 0);
			}

			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.is(ItemTags.create(Identifier.parse("ssc14:mac_fr_components")));
			}
		}));
		this.customSlots.put(8, this.addSlot(new ResourceHandlerSlot(internal, this::setItemInSlot, 8, 116, 13) {
			private final int slot = 8;
			private int x = MacFrameGUIMenu.this.x;
			private int y = MacFrameGUIMenu.this.y;

			@Override
			protected void setStackCopy(ItemStack stack) {
				super.setStackCopy(stack);
				slotChanged(8, 0, 0);
			}

			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.is(ItemTags.create(Identifier.parse("ssc14:mac_fr_components")));
			}
		}));
		for (int si = 0; si < 3; ++si)
			for (int sj = 0; sj < 9; ++sj)
				this.addSlot(new Slot(inv, sj + (si + 1) * 9, 62 + 8 + sj * 18, 8 + 84 + si * 18));
		for (int si = 0; si < 9; ++si)
			this.addSlot(new Slot(inv, si, 62 + 8 + si * 18, 8 + 142));
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
			if (index < 9) {
				if (!this.moveItemStackTo(itemstack1, 9, this.slots.size(), true))
					return ItemStack.EMPTY;
				slot.onQuickCraft(itemstack1, itemstack);
			} else if (!this.moveItemStackTo(itemstack1, 0, 9, false)) {
				if (index < 9 + 27) {
					if (!this.moveItemStackTo(itemstack1, 9 + 27, this.slots.size(), true))
						return ItemStack.EMPTY;
				} else {
					if (!this.moveItemStackTo(itemstack1, 9, 9 + 27, false))
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
		MacFrameGUIBlockUpdateProcedure.execute(world, x, y, z);
		if (!bound && playerIn instanceof ServerPlayer serverPlayer) {
			if (!serverPlayer.isAlive() || serverPlayer.hasDisconnected()) {
				for (int j = 0; j < internal.size(); ++j) {
					if (j == 0)
						continue;
					if (j == 1)
						continue;
					if (j == 2)
						continue;
					if (j == 3)
						continue;
					if (j == 4)
						continue;
					if (j == 6)
						continue;
					if (j == 7)
						continue;
					if (j == 8)
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
					if (i == 2)
						continue;
					if (i == 3)
						continue;
					if (i == 4)
						continue;
					if (i == 6)
						continue;
					if (i == 7)
						continue;
					if (i == 8)
						continue;
					playerIn.getInventory().placeItemBackInInventory(ItemUtil.getStack(internal, i));
					setItemInSlot(i, ItemResource.EMPTY, 0);
				}
			}
		}
	}

	private void slotChanged(int slotid, int ctype, int meta) {
		if (this.world != null && this.world.isClientSide()) {
			ClientPacketDistributor.sendToServer(new MacFrameGUISlotMessage(slotid, x, y, z, ctype, meta));
			MacFrameGUISlotMessage.handleSlotAction(entity, slotid, ctype, meta, x, y, z);
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
}