package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.block.furniture.ChairInteractionService;

/** @deprecated Chair blocks delegate directly to {@link ChairInteractionService}. */
@Deprecated(forRemoval = false)
public final class Sit_On_Chair_PRProcedure {
    private Sit_On_Chair_PRProcedure() {
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        ChairInteractionService.seat(world, x, y, z, entity);
    }
}
