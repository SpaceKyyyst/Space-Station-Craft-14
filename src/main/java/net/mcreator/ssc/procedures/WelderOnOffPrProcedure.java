package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.item.tool.WelderStateService;

/** @deprecated Use {@link WelderStateService}. */
@Deprecated(forRemoval = false)
public class WelderOnOffPrProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z,
            Entity entity) {
        WelderStateService.toggle(world, x, y, z, entity);
    }
}
