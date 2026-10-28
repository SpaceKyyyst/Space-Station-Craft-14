package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.airlock.AirlockStateInitializer;

/** Compatibility facade for generated airlock state initialization callers. */
public final class BaseAirlockD1PutProcedure {
    private BaseAirlockD1PutProcedure() {
    }

    public static void execute(LevelAccessor world, double x, double y, double z, BlockState state) {
        AirlockStateInitializer.execute(world, x, y, z, state);
    }
}
