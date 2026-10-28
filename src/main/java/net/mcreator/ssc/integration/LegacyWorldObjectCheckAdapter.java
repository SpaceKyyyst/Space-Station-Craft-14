package net.mcreator.ssc.integration;

import net.mcreator.ssc.procedures.WorldObjectCheckUIProcedure;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/** Compatibility boundary for world-check overlay visibility. */
public final class LegacyWorldObjectCheckAdapter {
    private LegacyWorldObjectCheckAdapter() {
    }

    public static boolean isVisible(Level level, Player player) {
        return WorldObjectCheckUIProcedure.execute(level, player);
    }
}
