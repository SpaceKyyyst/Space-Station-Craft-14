package net.mcreator.ssc.integration;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.ssc14.content.player.action.WorldObjectCheckService;

/** Compatibility boundary for world-check overlay visibility. */
public final class LegacyWorldObjectCheckAdapter {
    private LegacyWorldObjectCheckAdapter() {
    }

    public static boolean isVisible(Level level, Player player) {
        return WorldObjectCheckService.execute(level, player);
    }
}
