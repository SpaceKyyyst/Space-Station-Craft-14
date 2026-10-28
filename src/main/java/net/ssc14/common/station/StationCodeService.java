package net.ssc14.common.station;

import java.util.Objects;
import java.util.function.ToIntFunction;
import java.util.function.BiConsumer;
import net.minecraft.world.level.LevelAccessor;

/** Legacy-neutral read port for the station-wide alert code. */
public final class StationCodeService {
    private static volatile ToIntFunction<LevelAccessor> provider = level -> 0;
    private static volatile BiConsumer<LevelAccessor, Integer> mutator = (level, value) -> { };

    private StationCodeService() { }

    public static void install(ToIntFunction<LevelAccessor> source) {
        provider = Objects.requireNonNull(source, "source");
    }

    public static int current(LevelAccessor level) {
        return provider.applyAsInt(level);
    }

    public static void installMutator(BiConsumer<LevelAccessor, Integer> replacement) {
        mutator = Objects.requireNonNull(replacement, "replacement");
    }

    public static void set(LevelAccessor level, int value) {
        mutator.accept(level, value);
    }

    public static void advanceOrReset(LevelAccessor level, boolean reset) {
        set(level, reset ? 0 : current(level) + 1);
    }
}
