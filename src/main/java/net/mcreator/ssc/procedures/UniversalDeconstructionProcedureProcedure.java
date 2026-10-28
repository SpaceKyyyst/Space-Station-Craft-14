package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.airlock.AirlockDeconstructionService;

/** Compatibility facade for generated deconstruction callers. */
public final class UniversalDeconstructionProcedureProcedure {
    private UniversalDeconstructionProcedureProcedure() {
    }

    public static void execute(LevelAccessor world, double x, double y, double z, BlockState state, Entity entity) {
        AirlockDeconstructionService.execute(world, x, y, z, state, entity);
    }
}
