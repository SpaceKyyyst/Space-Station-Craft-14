package net.mcreator.ssc.client.screens;

import net.neoforged.neoforge.client.event.RenderGuiEvent;

/** @deprecated Use {@link net.ssc14.client.overlay.WorldCheckUIOverlay}. */
@Deprecated(forRemoval = false)
public class WorldCheckUIOverlay {
    public static void eventHandler(RenderGuiEvent.Pre event) {
        net.ssc14.client.overlay.WorldCheckUIOverlay.eventHandler(event);
    }
}
