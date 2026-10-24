package net.ssc14.content.block.atmosphere;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

/** Migration boundary for the generated oxygen-production procedure. */
public final class GasProducerO2TickService {
    @FunctionalInterface
    public interface Adapter {
        void tick(ServerLevel level, BlockPos pos);
    }

    private static Adapter adapter = (level, pos) -> { };

    private GasProducerO2TickService() { }

    public static void install(Adapter replacement) {
        adapter = Objects.requireNonNull(replacement, "replacement");
    }

    public static void tick(ServerLevel level, BlockPos pos) {
        adapter.tick(level, pos);
    }
}
