package net.ssc14.client.chemistry;

import net.ssc14.content.item.reagent.ReagentContainerItem;
import net.ssc14.content.item.reagent.ReagentContainerAccess;
import net.ssc14.integration.neoforge.chemistry.network.ProcessReactionsMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

/** Handles the client-side middle-click transfer gesture for reagent containers. */
@EventBusSubscriber(modid = "ssc_14", value = Dist.CLIENT)
public final class ReagentTransferHandler {
    private static final int MIDDLE_MOUSE_BUTTON = 2;

    private ReagentTransferHandler() { }

    @SubscribeEvent
    public static void onScreenMouseClick(ScreenEvent.MouseButtonPressed.Pre event) {
        if (event.getButton() != MIDDLE_MOUSE_BUTTON
                || !(event.getScreen() instanceof AbstractContainerScreen<?> containerScreen)) {
            return;
        }

        Slot slot = containerScreen.getSlotUnderMouse();
        Player player = Minecraft.getInstance().player;
        if (slot == null || player == null) return;

        ItemStack targetStack = slot.getItem();
        ItemStack mainHandStack = player.getMainHandItem();
        if (!(targetStack.getItem() instanceof ReagentContainerItem)
                || !(mainHandStack.getItem() instanceof ReagentContainerItem)
                || ReagentContainerAccess.contents(mainHandStack).isEmpty()) {
            return;
        }

        if (Minecraft.getInstance().getConnection() != null) {
            Minecraft.getInstance().getConnection().send(
                    new ProcessReactionsMessage(slot.index, ItemStack.EMPTY, "hotbar_transfer"));
            event.setCanceled(true);
        }
    }
}
