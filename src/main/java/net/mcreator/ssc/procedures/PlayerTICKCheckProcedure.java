package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.ssc14.content.item.tool.GasAnalyserOverlayService;

/** @deprecated Gas-analyser held-state updates are owned by {@link GasAnalyserOverlayService}. */
@Deprecated(forRemoval = false)
public class PlayerTICKCheckProcedure {
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        execute(event.getEntity());
    }

    public static void execute(Entity entity) {
        GasAnalyserOverlayService.refreshHeldState(entity);
    }
}
