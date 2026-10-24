package net.ssc14.content.block.debug;

import java.util.Objects;

/** Boundary between canonical debug content and the legacy global gravity state. */
public final class DebugGravityStateService {
    private static Adapter adapter = enabled -> { };

    private DebugGravityStateService() { }

    public static void install(Adapter newAdapter) {
        adapter = Objects.requireNonNull(newAdapter, "newAdapter");
    }

    public static void enable() {
        adapter.setEnabled(true);
    }

    public static void disable() {
        adapter.setEnabled(false);
    }

    @FunctionalInterface
    public interface Adapter {
        void setEnabled(boolean enabled);
    }
}
