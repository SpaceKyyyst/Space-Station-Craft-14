package net.mcreator.ssc.access;

import net.minecraft.nbt.CompoundTag;

/** Shared persisted geometry contract for airlocks and their client renderer. */
public final class AirlockGeometry {
    public static final String WIDTH_KEY = "airlock_width";
    public static final int MIN_WIDTH = 1;
    public static final int MAX_WIDTH = 3;

    private AirlockGeometry() { }

    public static int width(CompoundTag data) {
        return Math.clamp(data.getIntOr(WIDTH_KEY, MIN_WIDTH), MIN_WIDTH, MAX_WIDTH);
    }

    public static void initialize(CompoundTag data) {
        if (!data.contains(WIDTH_KEY)) data.putInt(WIDTH_KEY, MIN_WIDTH);
    }
}
