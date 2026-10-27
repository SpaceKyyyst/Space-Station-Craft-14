package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.ssc14.content.item.tool.GasAnalyserOverlayService;

/** @deprecated Use {@link GasAnalyserOverlayService}. */
@Deprecated(forRemoval = false)
public class GasAnalyserGUI_pr_1Procedure {
    public static boolean execute(Entity entity) {
        return GasAnalyserOverlayService.isVisible(entity);
    }
}
