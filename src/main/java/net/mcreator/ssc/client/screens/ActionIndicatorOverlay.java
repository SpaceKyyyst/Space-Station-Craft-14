package net.mcreator.ssc.client.screens;

import net.neoforged.neoforge.client.event.RenderGuiEvent;

/** @deprecated Use {@link net.ssc14.client.overlay.ActionIndicatorOverlay}. */
@Deprecated(forRemoval = false)
public class ActionIndicatorOverlay {
    public static void eventHandler(RenderGuiEvent.Pre event) {
        net.ssc14.client.overlay.ActionIndicatorOverlay.eventHandler(event);
    }
}
