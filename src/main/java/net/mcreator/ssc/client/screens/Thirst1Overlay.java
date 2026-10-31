package net.mcreator.ssc.client.screens;

import net.neoforged.neoforge.client.event.RenderGuiEvent;

/** @deprecated Use {@link net.ssc14.client.overlay.Thirst1Overlay}. */
@Deprecated(forRemoval = false)
public class Thirst1Overlay {
    public static void eventHandler(RenderGuiEvent.Pre event) {
        net.ssc14.client.overlay.Thirst1Overlay.eventHandler(event);
    }
}
