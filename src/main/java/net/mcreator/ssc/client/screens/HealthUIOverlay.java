package net.mcreator.ssc.client.screens;

import net.neoforged.neoforge.client.event.RenderGuiEvent;

/** @deprecated Use {@link net.ssc14.client.overlay.HealthUIOverlay}. */
@Deprecated(forRemoval = false)
public class HealthUIOverlay {
    public static void eventHandler(RenderGuiEvent.Pre event) {
        net.ssc14.client.overlay.HealthUIOverlay.eventHandler(event);
    }
}
