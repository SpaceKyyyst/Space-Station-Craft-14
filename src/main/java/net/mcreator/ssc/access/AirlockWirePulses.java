package net.mcreator.ssc.access;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.ssc14.content.block.airlock.AirlockWirePulseService;

/** Compatibility facade for canonical wire pulse scheduling. */
@Deprecated(forRemoval = false)
public final class AirlockWirePulses {
    public static final int DURATION_TICKS = AirlockWirePulseService.DURATION_TICKS;

    private AirlockWirePulses() {
    }

    public static boolean active(CompoundTag data, int function, long now) {
        return AirlockWirePulseService.active(data, function, now);
    }

    public static void start(BlockEntity door, int function) {
        AirlockWirePulseService.start(door, function);
    }
}
