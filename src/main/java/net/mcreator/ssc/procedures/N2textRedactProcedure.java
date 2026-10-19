package net.mcreator.ssc.procedures;

import net.mcreator.ssc.AtmosphereReadings;
import net.minecraft.world.level.LevelAccessor;

public final class N2textRedactProcedure {
    private N2textRedactProcedure() { }

    public static String execute(LevelAccessor world, double x, double y, double z) {
        return AtmosphereReadings.read(world, x, y, z).map(sample -> String.format(java.util.Locale.ROOT, "%.1f", sample.nitrogenMoles())).orElse("-");
    }
}
