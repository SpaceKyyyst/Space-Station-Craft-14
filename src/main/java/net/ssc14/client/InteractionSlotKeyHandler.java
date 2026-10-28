package net.ssc14.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.protocol.game.ServerboundChatCommandPacket;
import net.minecraft.world.inventory.Slot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

/** Sends the server-authoritative slot interaction command from the client GUI. */
@EventBusSubscriber(value = Dist.CLIENT)
public final class InteractionSlotKeyHandler {
    private InteractionSlotKeyHandler() { }

    @SubscribeEvent
    public static void onKeyPress(ScreenEvent.KeyPressed.Pre event) {
        if (event.getKeyCode() != InteractionKeyMappings.INTERACTION.getKey().getValue()) return;
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.screen == null
                || !(minecraft.screen instanceof AbstractContainerScreen<?> screen)) return;

        double scale = minecraft.getWindow().getGuiScale();
        double mouseX = minecraft.mouseHandler.xpos() / scale;
        double mouseY = minecraft.mouseHandler.ypos() / scale;
        for (int index = 0; index < screen.getMenu().slots.size(); index++) {
            Slot slot = screen.getMenu().slots.get(index);
            double slotX = slot.x + screen.getGuiLeft();
            double slotY = slot.y + screen.getGuiTop();
            if (mouseX >= slotX && mouseX < slotX + 16
                    && mouseY >= slotY && mouseY < slotY + 16) {
                minecraft.player.connection.send(
                        new ServerboundChatCommandPacket("ssc14_interact " + index));
                event.setCanceled(true);
                return;
            }
        }
    }
}
