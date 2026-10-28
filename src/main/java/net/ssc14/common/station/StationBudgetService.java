package net.ssc14.common.station;

import java.util.Objects;
import java.util.function.ToDoubleFunction;
import net.minecraft.world.level.LevelAccessor;

/** Legacy-neutral read port for the synchronized station budget. */
public final class StationBudgetService {
    @FunctionalInterface
    public interface Spender {
        boolean trySpend(LevelAccessor level, double amount);
    }

    private static volatile ToDoubleFunction<LevelAccessor> provider = level -> 0;
    private static volatile Spender spender = (level, amount) -> false;

    private StationBudgetService() { }

    public static void install(ToDoubleFunction<LevelAccessor> source) {
        provider = Objects.requireNonNull(source, "source");
    }

    public static void installSpender(Spender source) {
        spender = Objects.requireNonNull(source, "source");
    }

    public static double current(LevelAccessor level) {
        return provider.applyAsDouble(level);
    }

    public static boolean trySpend(LevelAccessor level, double amount) {
        return amount > 0 && Double.isFinite(amount) && spender.trySpend(level, amount);
    }
}
