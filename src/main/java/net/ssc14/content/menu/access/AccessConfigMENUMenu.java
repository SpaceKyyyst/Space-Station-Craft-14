package net.ssc14.content.menu.access;

import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.ResourceHandler;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.Container;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.ssc14.content.access.AirlockDoorState;
import net.ssc14.content.item.Ssc14Items;
import net.ssc14.content.menu.Ssc14MenuTypes;
import net.ssc14.core.network.MenuStateAccess;
import net.ssc14.integration.neoforge.menu.network.MenuStateNetworkActions;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class AccessConfigMENUMenu extends AbstractContainerMenu implements MenuStateAccess {
	public final Map<String, Object> menuState = new HashMap<>() {
		@Override
		public Object put(String key, Object value) {
			if (!this.containsKey(key) && this.size() >= 65)
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
    private final ItemStack configurator;

	public AccessConfigMENUMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		super(Ssc14MenuTypes.ACCESS_CONFIG_MENU.get(), id);
		this.entity = inv.player;
        this.configurator = inv.player.getMainHandItem();
		this.world = inv.player.level();
		this.internal = new ItemStacksResourceHandler(0);
		BlockPos pos = null;
		if (extraData != null) {
			pos = extraData.readBlockPos();
			this.x = pos.getX();
			this.y = pos.getY();
			this.z = pos.getZ();
			access = ContainerLevelAccess.create(world, pos);
            boundBlockEntity = world.getBlockEntity(pos);
		}
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
        if (world.isClientSide()) return true;
        var pos = new BlockPos(x, y, z);
        return player.isAlive() && !player.isSpectator() && player.level() == world
                && player.getMainHandItem() == configurator
                && configurator.is(Ssc14Items.ACCESS_CONFIG.get())
                && world.hasChunkAt(pos) && world.getBlockEntity(pos) == boundBlockEntity && boundBlockEntity != null
                && world.mayInteract(player, pos)
                && player.distanceToSqr(pos.getCenter()) <= 36
                && AirlockDoorState.isDoor(world.getBlockState(pos));
    }
	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		return ItemStack.EMPTY;
	}

	public Map<Integer, Slot> getSlots() {
		return Collections.unmodifiableMap(customSlots);
	}

	@Override
	public Map<String, Object> getMenuState() {
		return menuState;
	}

	public void sendMenuStateUpdate(Player player, int elementType, String name,
			Object elementState, boolean needClientUpdate) {
		MenuStateNetworkActions.send(this, player, elementType, name, elementState);
	}
}
