package net.ssc14.content.item.utility;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;

/** Server-side broadcast operation used by the legacy debug item. */
public final class DebugBroadcastService {
    public static final String MESSAGE_TEXT = "ЛКМ";

    private DebugBroadcastService() { }

    public static void broadcast(LevelAccessor level) {
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.getServer().getPlayerList()
                    .broadcastSystemMessage(Component.literal(MESSAGE_TEXT), false);
        }
    }
}
