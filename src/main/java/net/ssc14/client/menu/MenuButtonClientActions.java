package net.ssc14.client.menu;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.ssc14.integration.neoforge.menu.network.MenuButtonDispatchBridge;
import net.ssc14.integration.neoforge.menu.network.MenuButtonPayload;

/** Sends an authoritative button payload and preserves the generated local feedback path. */
public final class MenuButtonClientActions {
    private MenuButtonClientActions() { }

    public static void send(Player player, MenuButtonPayload payload) {
        ClientPacketDistributor.sendToServer(payload);
        MenuButtonDispatchBridge.dispatch(player, payload);
    }
}
