package net.mcreator.ssc.client.screens;

import net.neoforged.neoforge.client.event.RenderGuiEvent;

/** @deprecated Use {@link net.ssc14.client.overlay.MultitulUIOverlay}. */
@Deprecated(forRemoval = false)
public class MultitulUIOverlay {
    public static void eventHandler(RenderGuiEvent.Pre event) {
        net.ssc14.client.overlay.MultitulUIOverlay.eventHandler(event);
    }
}
