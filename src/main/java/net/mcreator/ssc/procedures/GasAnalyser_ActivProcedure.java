package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.ssc14.content.item.tool.GasAnalyserOverlayService;

/** @deprecated Use {@link GasAnalyserOverlayService}. */
@Deprecated(forRemoval = false)
public class GasAnalyser_ActivProcedure {
    public static void execute(Entity entity) {
        GasAnalyserOverlayService.activate(entity);
    }
}
