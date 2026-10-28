package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.airlock.AirlockLampStateService;

/** Compatibility facade for generated lamp-state callers. */
public final class CPlogLampPrProcedure {
    private CPlogLampPrProcedure() {
    }

    public static void execute(LevelAccessor world, double x, double y, double z, BlockState state) {
        AirlockLampStateService.execute(world, x, y, z, state);
    }
}
