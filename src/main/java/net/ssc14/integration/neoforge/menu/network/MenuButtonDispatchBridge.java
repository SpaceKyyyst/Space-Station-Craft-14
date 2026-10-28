package net.ssc14.integration.neoforge.menu.network;

import net.minecraft.world.entity.player.Player;

/** Process-wide bridge from migrated screens to the transitional generated dispatcher. */
public final class MenuButtonDispatchBridge {
    private static final LocalMenuButtonDispatcher DISPATCHER = new LocalMenuButtonDispatcher();

    private MenuButtonDispatchBridge() { }

    public static void install(LocalMenuButtonDispatcher.Handler handler) {
        DISPATCHER.install(handler);
    }

    public static void dispatch(Player player, MenuButtonPayload payload) {
        DISPATCHER.dispatch(player, payload);
    }
}
