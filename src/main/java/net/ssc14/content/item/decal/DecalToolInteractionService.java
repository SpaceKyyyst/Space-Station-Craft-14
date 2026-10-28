package net.ssc14.content.item.decal;

import java.util.Objects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;

/** Boundary for decal GUI and world actions that still run in the legacy layer. */
public final class DecalToolInteractionService {
    public interface Adapter {
        void use(DecalToolKind kind, Player player, InteractionHand hand);

        void useOn(DecalToolKind kind, UseOnContext context);
    }

    private static Adapter adapter = new Adapter() {
        @Override
        public void use(DecalToolKind kind, Player player, InteractionHand hand) { }

        @Override
        public void useOn(DecalToolKind kind, UseOnContext context) { }
    };

    private DecalToolInteractionService() { }

    public static Adapter install(Adapter replacement) {
        Adapter previous = adapter;
        adapter = Objects.requireNonNull(replacement, "replacement");
        return previous;
    }

    public static void use(DecalToolKind kind, Player player, InteractionHand hand) {
        adapter.use(kind, player, hand);
    }

    public static void useOn(DecalToolKind kind, UseOnContext context) {
        adapter.useOn(kind, context);
    }
}
