package net.mcreator.ssc.access;

import net.minecraft.nbt.CompoundTag;

/** Presentation of the seven panel indicators from synchronized server state. */
public final class AirlockPanelIndicators {
    private static final long BLINK_HALF_PERIOD_TICKS = 10;

    private AirlockPanelIndicators() { }

    public static boolean lit(CompoundTag data, String function, long gameTime) {
        if (!data.getBooleanOr("powered", false)) return false;
        if (function.equals("powered")) {
            boolean first = data.getBooleanOr("energy_cabel_1", true);
            boolean second = data.getBooleanOr("energy_cabel_2", true);
            return first && second || (first != second && (gameTime / BLINK_HALF_PERIOD_TICKS) % 2 == 0);
        }
        return data.getBooleanOr(function, false);
    }
}
