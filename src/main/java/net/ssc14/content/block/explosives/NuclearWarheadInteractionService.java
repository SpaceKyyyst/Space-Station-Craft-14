package net.ssc14.content.block.explosives;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/** Migration boundary for the nuclear warhead activation procedure. */
public final class NuclearWarheadInteractionService {
    @FunctionalInterface
    public interface Adapter {
        void activate(Level level, BlockPos pos);
    }

    private static Adapter adapter = (level, pos) -> { };

    private NuclearWarheadInteractionService() { }

    public static void install(Adapter replacement) {
        adapter = Objects.requireNonNull(replacement, "replacement");
    }

    public static void activate(Level level, BlockPos pos) {
        adapter.activate(level, pos);
    }
}
