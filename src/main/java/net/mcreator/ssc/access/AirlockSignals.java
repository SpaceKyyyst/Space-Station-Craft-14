package net.mcreator.ssc.access;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.ssc14.content.block.airlock.AirlockSignalHandler;

/** Compatibility facade for generated and legacy integration callers. */
public final class AirlockSignals {
    public static final String HOLD = AirlockSignalHandler.HOLD;

    private AirlockSignals() {
    }

    public static void changed(ServerLevel level, BlockPos pos) {
        AirlockSignalHandler.changed(level, pos);
    }
}
