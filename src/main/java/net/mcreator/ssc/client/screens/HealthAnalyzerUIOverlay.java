package net.mcreator.ssc.client.screens;

import net.neoforged.neoforge.client.event.RenderGuiEvent;

/** @deprecated Use {@link net.ssc14.client.overlay.HealthAnalyzerUIOverlay}. */
@Deprecated(forRemoval = false)
public class HealthAnalyzerUIOverlay {
    public static void eventHandler(RenderGuiEvent.Pre event) {
        net.ssc14.client.overlay.HealthAnalyzerUIOverlay.eventHandler(event);
    }
}
