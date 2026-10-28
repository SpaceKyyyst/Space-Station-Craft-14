package net.mcreator.ssc.integration;

import net.mcreator.ssc.procedures.SpannerEntUseProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

/** Transitional boundary for generated spanner entity interaction. */
public final class LegacySpannerInteractionAdapter {
    private LegacySpannerInteractionAdapter() {
    }

    public static void execute(LevelAccessor level, double x, double y, double z,
            Entity target, Entity source) {
        SpannerEntUseProcedure.execute(level, x, y, z, target, source);
    }
}
