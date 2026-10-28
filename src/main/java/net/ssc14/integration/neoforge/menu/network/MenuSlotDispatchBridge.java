package net.ssc14.integration.neoforge.menu.network;

import java.util.Objects;
import java.util.function.BiConsumer;
import net.minecraft.world.entity.player.Player;

/** Process-wide bridge from migrated menus to the transitional slot dispatcher. */
public final class MenuSlotDispatchBridge {
    private static BiConsumer<Player, MenuSlotPayload> dispatcher = (player, payload) -> { };

    private MenuSlotDispatchBridge() { }

    public static void install(BiConsumer<Player, MenuSlotPayload> handler) {
        dispatcher = Objects.requireNonNull(handler, "handler");
    }

    public static void dispatch(Player player, MenuSlotPayload payload) {
        dispatcher.accept(player, payload);
    }
}
