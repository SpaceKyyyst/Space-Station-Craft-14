package net.mcreator.ssc.access;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.ssc14.content.block.airlock.AirlockAiControlService;

/** Compatibility facade for the canonical server-side AI control contract. */
@Deprecated(forRemoval = false)
public final class AirlockAiControl {
    private AirlockAiControl() {
    }

    public interface Authority extends AirlockAiControlService.Authority {
    }

    public enum Action {
        OPEN, CLOSE, BOLTS_DOWN, BOLTS_UP, EMERGENCY_ON, EMERGENCY_OFF
    }

    public static boolean request(ServerLevel level, BlockPos clicked, Authority authority, Action action) {
        return AirlockAiControlService.request(level, clicked, authority,
                AirlockAiControlService.Action.valueOf(action.name()));
    }
}
