package net.ssc14.common.network;

import java.util.Objects;
import net.minecraft.world.entity.player.Player;
import net.ssc14.core.network.MenuStateAccess;

/** Side-neutral port used by menu-state producers without depending on NeoForge transport. */
public final class MenuStateTransportPort {
    private static Handler handler = (menu, player, type, name, value) ->
            menu.getMenuState().put(type + ":" + name, value);

    private MenuStateTransportPort() { }

    public static void install(Handler transport) {
        handler = Objects.requireNonNull(transport, "transport");
    }

    public static void send(MenuStateAccess menu, Player player, int elementType,
            String name, Object value) {
        handler.send(menu, player, elementType, name, value);
    }

    @FunctionalInterface
    public interface Handler {
        void send(MenuStateAccess menu, Player player, int elementType, String name, Object value);
    }
}
