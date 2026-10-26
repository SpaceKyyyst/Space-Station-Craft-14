package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.item.food.DigestionService;

/** @deprecated Use {@link DigestionService}. */
@Deprecated(forRemoval = false)
public final class Digestion20PrProcedure {
    private Digestion20PrProcedure() { }

    public static void execute(LevelAccessor world, Entity entity) {
        DigestionService.schedule(world, entity);
    }
}
