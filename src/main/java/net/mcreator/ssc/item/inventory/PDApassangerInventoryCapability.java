package net.mcreator.ssc.item.inventory;

import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemAccessItemHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.core.component.DataComponents;

import net.mcreator.ssc.world.inventory.PDApassangerGUIMenu;
import net.mcreator.ssc.init.Ssc14ModItems;

@EventBusSubscriber
public class PDApassangerInventoryCapability extends ItemAccessItemHandler {
	@SubscribeEvent
	public static void onItemDropped(ItemTossEvent event) {
		if (event.getEntity().getItem().getItem() == Ssc14ModItems.PD_APASSANGER.get()) {
			Player player = event.getPlayer();
			if (player.containerMenu instanceof PDApassangerGUIMenu)
				player.closeContainer();
		}
	}

	public PDApassangerInventoryCapability(ItemAccess access) {
		super(access, DataComponents.CONTAINER, 2);
	}

	@Override
	protected int getCapacity(int index, ItemResource resource) {
		return Math.min(1, super.getCapacity(index, resource));
	}

	@Override
	public boolean isValid(int index, ItemResource resource) {
		return super.isValid(index, resource) && resource.getItem() != Ssc14ModItems.PD_APASSANGER.get();
	}
}