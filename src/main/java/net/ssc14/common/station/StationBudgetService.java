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

    public interface Editor {
        void credit(LevelAccessor level, double amount);

        void reset(LevelAccessor level);
    }

    private static volatile ToDoubleFunction<LevelAccessor> provider = level -> 0;
    private static volatile Spender spender = (level, amount) -> false;
    private static volatile Editor editor = new Editor() {
        @Override public void credit(LevelAccessor level, double amount) { }
        @Override public void reset(LevelAccessor level) { }
    };

    private StationBudgetService() { }

    public static void install(ToDoubleFunction<LevelAccessor> source) {
        provider = Objects.requireNonNull(source, "source");
    }

    public static void installSpender(Spender source) {
        spender = Objects.requireNonNull(source, "source");
    }

    public static void installEditor(Editor source) {
        editor = Objects.requireNonNull(source, "source");
    }

    public static double current(LevelAccessor level) {
        return provider.applyAsDouble(level);
    }

    public static boolean trySpend(LevelAccessor level, double amount) {
        return amount > 0 && Double.isFinite(amount) && spender.trySpend(level, amount);
    }

    public static void credit(LevelAccessor level, double amount) {
        if (amount > 0 && Double.isFinite(amount)) editor.credit(level, amount);
    }

    public static void reset(LevelAccessor level) {
        editor.reset(level);
    }
}
