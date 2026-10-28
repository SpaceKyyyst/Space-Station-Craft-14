package net.ssc14.client.menu;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.ssc14.core.network.MenuStateAccess;
import net.ssc14.integration.neoforge.menu.network.MenuStateUpdateMessage;

/** Updates local menu state and sends its validated value to the server. */
public final class MenuStateClientActions {
    private MenuStateClientActions() { }

    public static void send(MenuStateAccess menu, Player player, int elementType,
            String name, Object value) {
        menu.getMenuState().put(elementType + ":" + name, value);
        if (player.level().isClientSide()) {
            ClientPacketDistributor.sendToServer(
                    new MenuStateUpdateMessage(elementType, name, value));
        }
    }
}
