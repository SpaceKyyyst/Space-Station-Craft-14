package net.mcreator.ssc.access;

import net.minecraft.nbt.CompoundTag;

/** Centralizes SS14-style auto-close settings persisted by an airlock. */
public final class AirlockAutoClosePolicy {
    public static final String MODIFIER = "auto_close_modifier";
    public static final String KEEP_OPEN_ON_CLICK = "keep_open_on_click";
    private static final int DEFAULT_DELAY_TICKS = 100;

    private AirlockAutoClosePolicy() { }

    public static boolean enabled(CompoundTag data) {
        return data.getBooleanOr("timer", true)
                && data.getFloatOr(MODIFIER, 1.0f) > 0.0f;
    }

    public static int delayTicks(CompoundTag data) {
        if (!enabled(data)) return 0;
        return Math.max(1, Math.round(DEFAULT_DELAY_TICKS * data.getFloatOr(MODIFIER, 1.0f)));
    }

    public static boolean keepOpenOnClick(CompoundTag data) {
        return data.getBooleanOr(KEEP_OPEN_ON_CLICK, false);
    }
}
