package net.mcreator.ssc.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.block.machinery.MachineFrameAssemblyService;

/** @deprecated Canonical machine-frame assembly is owned by {@link MachineFrameAssemblyService}. */
@Deprecated(forRemoval = false)
public final class MachineFrame2_craftProcedure {
    private MachineFrame2_craftProcedure() { }

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        MachineFrameAssemblyService.assemble(world, BlockPos.containing(x, y, z), entity);
    }
}
