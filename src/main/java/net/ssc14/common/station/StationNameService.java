package net.ssc14.common.station;

import java.util.Objects;
import java.util.function.Function;
import net.minecraft.world.level.LevelAccessor;

/** Legacy-neutral read port for the station-wide display name. */
public final class StationNameService {
    private static volatile Function<LevelAccessor, String> provider = level -> "";

    private StationNameService() {
    }

    public static void install(Function<LevelAccessor, String> source) {
        provider = Objects.requireNonNull(source, "source");
    }

    public static String current(LevelAccessor level) {
        return provider.apply(level);
    }
}
