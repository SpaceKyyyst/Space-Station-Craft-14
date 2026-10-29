package net.mcreator.ssc.access;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.airlock.AirlockPowerService;

/** Compatibility facade for canonical airlock power handling. */
public final class AirlockPower {
    private AirlockPower() {
    }

    public static boolean isDoor(BlockState state) {
        return AirlockPowerService.isDoor(state);
    }

    public static void supply(ServerLevel level, BlockPos pos, boolean powered) {
        AirlockPowerService.supply(level, pos, powered);
    }
}
