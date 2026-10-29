package net.mcreator.ssc.network;

import net.minecraft.world.entity.player.Player;
import net.ssc14.integration.neoforge.menu.network.MenuButtonPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/** @deprecated Use the canonical network configuration menu handler. */
@Deprecated(forRemoval = false)
public final class NetConfigGUIButtonRegistration {
    private NetConfigGUIButtonRegistration() {
    }

    public static void handleData(MenuButtonPayload message, IPayloadContext context) {
        net.ssc14.content.menu.network.NetConfigGUIButtonRegistration.handleData(message, context);
    }

    public static void handleButtonAction(Player entity, int buttonId, int x, int y, int z) {
        net.ssc14.content.menu.network.NetConfigGUIButtonRegistration.handleButtonAction(
                entity, buttonId, x, y, z);
    }
}
