package net.ssc14.common.airlock;

import net.minecraft.nbt.CompoundTag;

/** Stable persisted representation of temporary airlock wire pulses. */
public final class AirlockWireState {
    public static final int FUNCTION_COUNT = 8;
    public static final int DURATION_TICKS = 200;
    private static final String PULSE_PREFIX = "_ssc14_wire_pulse_";

    private AirlockWireState() { }

    public static boolean validFunction(int function) {
        return function >= 0 && function < FUNCTION_COUNT;
    }

    public static String deadlineKey(int function) {
        if (!validFunction(function)) throw new IllegalArgumentException("Airlock wire function must be 0..7");
        return PULSE_PREFIX + function;
    }

    public static boolean active(CompoundTag data, int function, long now) {
        return validFunction(function) && data.getLongOr(deadlineKey(function), 0) > now;
    }
}
