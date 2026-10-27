package net.ssc14.content.item.currency;

import java.util.Objects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;

/** Boundary for currency interactions that still depend on the legacy economy runtime. */
public final class CurrencyInteractionService {
    public interface Adapter {
        void use(CurrencyKind kind, Player player);

        void useOn(CurrencyKind kind, UseOnContext context);
    }

    private static Adapter adapter = new Adapter() {
        @Override
        public void use(CurrencyKind kind, Player player) { }

        @Override
        public void useOn(CurrencyKind kind, UseOnContext context) { }
    };

    private CurrencyInteractionService() { }

    public static Adapter install(Adapter replacement) {
        Adapter previous = adapter;
        adapter = Objects.requireNonNull(replacement, "replacement");
        return previous;
    }

    public static void use(CurrencyKind kind, Player player) {
        adapter.use(kind, player);
    }

    public static void useOn(CurrencyKind kind, UseOnContext context) {
        adapter.useOn(kind, context);
    }
}
