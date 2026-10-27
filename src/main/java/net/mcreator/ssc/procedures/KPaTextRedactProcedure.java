package net.mcreator.ssc.procedures;

import net.ssc14.client.atmosphere.AtmosphereReadings;
import net.minecraft.world.level.LevelAccessor;

public final class KPaTextRedactProcedure {
    private KPaTextRedactProcedure() { }

    public static String execute(LevelAccessor world, double x, double y, double z) {
        return AtmosphereReadings.read(world, x, y, z).map(sample -> String.format(java.util.Locale.ROOT, "%.1f (кПа)", sample.pressurePa() / 1000d)).orElse("-");
    }
}
