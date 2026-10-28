package net.mcreator.ssc.integration;

import net.mcreator.ssc.procedures.MicrwGUIbutEProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

/** Transitional boundary for the generated microwave runtime. */
public final class LegacyMicrowaveAdapter {
    private LegacyMicrowaveAdapter() {
    }

    public static void start(LevelAccessor level, double x, double y, double z, Entity entity) {
        MicrwGUIbutEProcedure.execute(level, x, y, z, entity);
    }
}
