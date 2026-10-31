package net.mcreator.ssc.client.screens;

import net.neoforged.neoforge.client.event.RenderGuiEvent;

/** @deprecated Use {@link net.ssc14.client.overlay.GasAnalyserGUIOverlay}. */
@Deprecated(forRemoval = false)
public final class GasAnalyserGUIOverlay {
    private GasAnalyserGUIOverlay() {
    }

    public static void render(RenderGuiEvent.Pre event) {
        net.ssc14.client.overlay.GasAnalyserGUIOverlay.render(event);
    }
}
