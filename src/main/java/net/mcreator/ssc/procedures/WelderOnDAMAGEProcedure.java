package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.item.tool.WelderCombatService;
import net.ssc14.content.item.tool.WelderState;

/** @deprecated Use {@link WelderCombatService}. */
@Deprecated(forRemoval = false)
public class WelderOnDAMAGEProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        WelderCombatService.apply(world, entity, WelderState.ACTIVE);
    }
}
