package net.mcreator.ssc.integration;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.ssc14.content.medical.HealthAnalyzerTargetService;

/** Compatibility boundary for the health-analyzer preview entity lookup. */
public final class LegacyHealthAnalyzerDisplayAdapter {
    private LegacyHealthAnalyzerDisplayAdapter() {
    }

    public static Entity resolve(Player player) {
        return HealthAnalyzerTargetService.execute(player);
    }
}
