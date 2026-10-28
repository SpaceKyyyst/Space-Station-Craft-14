package net.ssc14.content.block.debug;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

/** Handles the deliberate debug disconnect triggered by the failure sentinel. */
public final class FailureSentinelInteractionService {
    private static final String DISCONNECT_MESSAGE =
            "SSC14 Debug: Критический сбой в работе мода. Reason: Костыли.";

    private FailureSentinelInteractionService() { }

    public static Component disconnectMessage() {
        return Component.literal(DISCONNECT_MESSAGE);
    }

    public static void disconnect(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.disconnect(disconnectMessage());
        }
    }
}
