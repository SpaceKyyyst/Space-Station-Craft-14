package net.ssc14.common.station;

import java.util.Objects;
import java.util.function.ToIntFunction;
import net.minecraft.world.level.LevelAccessor;

/** Legacy-neutral read port for the station-wide alert code. */
public final class StationCodeService {
    private static volatile ToIntFunction<LevelAccessor> provider = level -> 0;

    private StationCodeService() { }

    public static void install(ToIntFunction<LevelAccessor> source) {
        provider = Objects.requireNonNull(source, "source");
    }

    public static int current(LevelAccessor level) {
        return provider.applyAsInt(level);
    }
}
