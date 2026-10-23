package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.block.furniture.ChairInteractionService;

/** @deprecated Chair blocks delegate directly to {@link ChairInteractionService}. */
@Deprecated(forRemoval = false)
public final class OficeChair_AutorotateProcedure {
    private OficeChair_AutorotateProcedure() {
    }

    public static void execute(LevelAccessor world, double x, double y, double z) {
        ChairInteractionService.synchronizeRotation(world, x, y, z);
    }
}
