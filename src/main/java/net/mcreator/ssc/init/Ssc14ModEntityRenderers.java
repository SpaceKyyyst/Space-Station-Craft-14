package net.mcreator.ssc.init;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.ssc14.client.Ssc14EntityRenderers;

/** @deprecated Use {@link Ssc14EntityRenderers}. */
@Deprecated(forRemoval = true)
public final class Ssc14ModEntityRenderers {
    private Ssc14ModEntityRenderers() {
    }

    /**
     * Compatibility entry point for integrations that invoke the old method.
     * Event bus registration is owned solely by the canonical class.
     */
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        Ssc14EntityRenderers.registerEntityRenderers(event);
    }
}
