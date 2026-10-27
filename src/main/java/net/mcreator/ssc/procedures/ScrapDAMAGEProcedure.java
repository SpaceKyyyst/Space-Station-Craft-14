package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.item.tool.MechanicalToolDamageService;
import net.ssc14.content.item.tool.MechanicalToolProfile;

/** @deprecated Use {@link MechanicalToolDamageService}. */
@Deprecated(forRemoval = false)
public final class ScrapDAMAGEProcedure {
    private ScrapDAMAGEProcedure() { }

    public static void execute(LevelAccessor level, Entity entity) {
        MechanicalToolDamageService.apply(level, entity, MechanicalToolProfile.CROWBAR);
    }
}
