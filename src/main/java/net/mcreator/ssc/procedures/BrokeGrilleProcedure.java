package net.mcreator.ssc.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.block.grille.GrilleBreakService;

/** @deprecated Use {@link GrilleBreakService}. */
@Deprecated(forRemoval = false)
public final class BrokeGrilleProcedure {
    private BrokeGrilleProcedure() {
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        GrilleBreakService.replaceWithBroken(world, BlockPos.containing(x, y, z), entity);
    }
}
