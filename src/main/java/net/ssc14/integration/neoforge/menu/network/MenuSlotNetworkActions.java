package net.ssc14.integration.neoforge.menu.network;

import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.world.entity.player.Player;

/** Side-neutral transport boundary for client-originated menu-slot changes. */
public final class MenuSlotNetworkActions {
    private static Consumer<MenuSlotPayload> clientSender = payload -> { };

    private MenuSlotNetworkActions() { }

    public static void installClientSender(Consumer<MenuSlotPayload> sender) {
        clientSender = Objects.requireNonNull(sender, "sender");
    }

    public static void send(Player player, MenuSlotPayload payload) {
        if (!player.level().isClientSide()) return;
        clientSender.accept(payload);
        MenuSlotDispatchBridge.dispatch(player, payload);
    }
}
