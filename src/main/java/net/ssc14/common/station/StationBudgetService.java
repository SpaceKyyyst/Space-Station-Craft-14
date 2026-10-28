package net.ssc14.common.station;

import java.util.Objects;
import java.util.function.ToDoubleFunction;
import net.minecraft.world.level.LevelAccessor;

/** Legacy-neutral read port for the synchronized station budget. */
public final class StationBudgetService {
    private static volatile ToDoubleFunction<LevelAccessor> provider = level -> 0;

    private StationBudgetService() { }

    public static void install(ToDoubleFunction<LevelAccessor> source) {
        provider = Objects.requireNonNull(source, "source");
    }

    public static double current(LevelAccessor level) {
        return provider.applyAsDouble(level);
    }
}
