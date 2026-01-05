package net.mcreator.ssc.item.inventory;

import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.component.DataComponents;

import net.mcreator.ssc.world.inventory.IDcodeMenu;
import net.mcreator.ssc.init.Ssc14ModItems;

import javax.annotation.Nonnull;

@EventBusSubscriber
public class IDCardPassangerInventoryCapability extends ComponentItemHandler {
	@SubscribeEvent
	public static void onItemDropped(ItemTossEvent event) {
		if (event.getEntity().getItem().getItem() == Ssc14ModItems.ID_CARD_PASSANGER.get()) {
			Player player = event.getPlayer();
			if (player.containerMenu instanceof IDcodeMenu)
				player.closeContainer();
		}
	}

	public IDCardPassangerInventoryCapability(MutableDataComponentHolder parent) {
		super(parent, DataComponents.CONTAINER, 0);
	}

	@Override
	public int getSlotLimit(int slot) {
		return 1;
	}

	@Override
	public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
		return stack.getItem() != Ssc14ModItems.ID_CARD_PASSANGER.get();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return super.getStackInSlot(slot).copy();
	}
}