package net.mcreator.ssc.procedures;

import net.mcreator.ssc.gravity.GravityGeneratorService;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

/** Compatibility adapter for the generator block ticker. */
public final class GravGenTICprocedureProcedure {
    private GravGenTICprocedureProcedure() { }
    public static void execute(LevelAccessor world, double x, double y, double z, BlockState state) {
        if (world instanceof ServerLevel server) GravityGeneratorService.tick(server, BlockPos.containing(x, y, z));
    }
}
