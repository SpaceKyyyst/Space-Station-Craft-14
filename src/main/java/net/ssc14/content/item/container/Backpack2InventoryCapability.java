package net.ssc14.content.item.container;

import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemAccessItemHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.core.component.DataComponents;

import net.ssc14.content.menu.storage.BackpackGUIMenu;
import net.ssc14.content.item.Ssc14Items;
import net.ssc14.content.item.container.PortableMenuItemKind;

@EventBusSubscriber
public class Backpack2InventoryCapability extends ItemAccessItemHandler {
	@SubscribeEvent
	public static void onItemDropped(ItemTossEvent event) {
		if (event.getEntity().getItem().getItem() == Ssc14Items.BACKPACK_2.get()) {
			Player player = event.getPlayer();
			if (player.containerMenu instanceof BackpackGUIMenu)
				player.closeContainer();
		}
	}

	public Backpack2InventoryCapability(ItemAccess access) {
		super(access, DataComponents.CONTAINER,
				PortableMenuItemKind.BACKPACK.inventorySlots());
	}

	@Override
	protected int getCapacity(int index, ItemResource resource) {
		return Math.min(64, super.getCapacity(index, resource));
	}

	@Override
	public boolean isValid(int index, ItemResource resource) {
		return super.isValid(index, resource) && resource.getItem() != Ssc14Items.BACKPACK_2.get();
	}
}

