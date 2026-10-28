package net.ssc14.integration.neoforge.menu.network;

import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.ssc14.core.network.MenuStateAccess;

/** Side-neutral transport boundary for validated menu-state changes. */
public final class MenuStateNetworkActions {
    private static Consumer<MenuStateUpdateMessage> clientSender = message -> { };

    private MenuStateNetworkActions() {
    }

    public static void installClientSender(Consumer<MenuStateUpdateMessage> sender) {
        clientSender = Objects.requireNonNull(sender);
    }

    public static void send(MenuStateAccess menu, Player player, int elementType,
            String name, Object value) {
        menu.getMenuState().put(elementType + ":" + name, value);
        var message = new MenuStateUpdateMessage(elementType, name, value);
        if (player instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, message);
        } else if (player.level().isClientSide()) {
            clientSender.accept(message);
        }
    }
}
