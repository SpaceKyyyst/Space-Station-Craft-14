package net.mcreator.ssc.client.screens;

import net.neoforged.neoforge.client.event.RenderGuiEvent;

/** @deprecated Use {@link net.ssc14.client.overlay.Hunger2Overlay}. */
@Deprecated(forRemoval = false)
public class Hunger2Overlay {
    public static void eventHandler(RenderGuiEvent.Pre event) {
        net.ssc14.client.overlay.Hunger2Overlay.eventHandler(event);
    }
}
