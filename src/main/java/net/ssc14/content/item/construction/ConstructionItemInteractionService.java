package net.ssc14.content.item.construction;

import java.util.Objects;
import net.minecraft.world.item.context.UseOnContext;

/** Boundary for construction actions that still execute in the legacy runtime. */
public final class ConstructionItemInteractionService {
    @FunctionalInterface
    public interface Adapter {
        void use(ConstructionItemKind kind, UseOnContext context);
    }

    private static Adapter adapter = (kind, context) -> { };

    private ConstructionItemInteractionService() { }

    public static Adapter install(Adapter replacement) {
        Adapter previous = adapter;
        adapter = Objects.requireNonNull(replacement, "replacement");
        return previous;
    }

    public static void use(ConstructionItemKind kind, UseOnContext context) {
        adapter.use(kind, context);
    }
}
