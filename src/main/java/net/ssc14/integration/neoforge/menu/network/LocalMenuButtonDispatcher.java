package net.ssc14.integration.neoforge.menu.network;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.world.entity.player.Player;

/** Single-install compatibility port for local client menu-button feedback. */
public final class LocalMenuButtonDispatcher {
    private final AtomicReference<Handler> handler = new AtomicReference<>();

    public void install(Handler candidate) {
        Objects.requireNonNull(candidate, "candidate");
        if (!handler.compareAndSet(null, candidate)) {
            throw new IllegalStateException("Local menu button handler is already installed");
        }
    }

    public void dispatch(Player player, MenuButtonPayload payload) {
        Handler installed = handler.get();
        if (installed == null) {
            throw new IllegalStateException("Local menu button handler is not installed");
        }
        installed.dispatch(player, Objects.requireNonNull(payload, "payload"));
    }

    @FunctionalInterface
    public interface Handler {
        void dispatch(Player player, MenuButtonPayload payload);
    }
}
