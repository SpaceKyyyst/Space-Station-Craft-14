package net.mcreator.ssc.procedures;

import net.mcreator.ssc.block.ShutterController;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public final class ShuttersClosingPRProcedure {
    private ShuttersClosingPRProcedure() { }
    public static void execute(LevelAccessor world, double x, double y, double z, BlockState state, Entity entity) {
        ShutterController.setOpen(world, BlockPos.containing(x, y, z), false);
    }
}
