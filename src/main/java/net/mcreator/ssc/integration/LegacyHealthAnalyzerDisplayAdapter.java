package net.mcreator.ssc.integration;

import net.mcreator.ssc.procedures.HealthAnalyzerUIEntityDisplayProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/** Compatibility boundary for the health-analyzer preview entity lookup. */
public final class LegacyHealthAnalyzerDisplayAdapter {
    private LegacyHealthAnalyzerDisplayAdapter() {
    }

    public static Entity resolve(Player player) {
        return HealthAnalyzerUIEntityDisplayProcedure.execute(player);
    }
}
