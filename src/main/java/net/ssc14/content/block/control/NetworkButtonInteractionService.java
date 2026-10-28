package net.ssc14.content.block.control;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/** Integration boundary for the legacy network-button activation flow. */
public final class NetworkButtonInteractionService {
    private static Adapter adapter = (level, pos) -> { };

    private NetworkButtonInteractionService() { }

    public static Adapter install(Adapter replacement) {
        Adapter previous = adapter;
        adapter = Objects.requireNonNull(replacement, "replacement");
        return previous;
    }

    public static void activate(Level level, BlockPos pos) {
        if (level != null && level.isClientSide()) return;
        adapter.activate(level, pos);
    }

    @FunctionalInterface
    public interface Adapter {
        void activate(Level level, BlockPos pos);
    }
}
