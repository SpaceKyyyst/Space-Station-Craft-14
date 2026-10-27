package net.ssc14.content.item.container;

import java.util.Objects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;

/** Boundary for portable menus that still use legacy menu implementations. */
public final class PortableMenuInteractionService {
    @FunctionalInterface
    public interface Adapter {
        void open(PortableMenuItemKind kind, ServerPlayer player, InteractionHand hand);
    }

    private static Adapter adapter = (kind, player, hand) -> { };

    private PortableMenuInteractionService() { }

    public static Adapter install(Adapter replacement) {
        Adapter previous = adapter;
        adapter = Objects.requireNonNull(replacement, "replacement");
        return previous;
    }

    public static void open(
            PortableMenuItemKind kind, ServerPlayer player, InteractionHand hand) {
        adapter.open(kind, player, hand);
    }
}
