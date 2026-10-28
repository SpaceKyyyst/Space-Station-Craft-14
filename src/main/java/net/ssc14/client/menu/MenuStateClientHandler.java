package net.ssc14.client.menu;

import net.minecraft.client.Minecraft;
import net.ssc14.integration.neoforge.menu.network.MenuStateUpdateMessage;

/** Applies a validated menu-state snapshot to the active client screen. */
public final class MenuStateClientHandler {
    private MenuStateClientHandler() { }

    public static void apply(MenuStateUpdateMessage message) {
        if (Minecraft.getInstance().screen instanceof MenuStateReceiver receiver) {
            receiver.updateMenuState(message.elementType(), message.name(), message.elementState());
        }
    }
}
