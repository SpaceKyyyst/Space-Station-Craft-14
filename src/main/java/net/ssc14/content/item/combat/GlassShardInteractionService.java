package net.ssc14.content.item.combat;

import java.util.Objects;
import net.minecraft.world.entity.LivingEntity;

/** Boundary for shard-hit behavior that still depends on legacy damage procedures. */
public final class GlassShardInteractionService {
    public interface Adapter {
        void hurt(GlassShardKind kind, LivingEntity target);
    }

    private static Adapter adapter = (kind, target) -> { };

    private GlassShardInteractionService() { }

    public static Adapter install(Adapter replacement) {
        Adapter previous = adapter;
        adapter = Objects.requireNonNull(replacement, "replacement");
        return previous;
    }

    public static void hurt(GlassShardKind kind, LivingEntity target) {
        adapter.hurt(kind, target);
    }
}
