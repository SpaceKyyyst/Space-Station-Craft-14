package net.mcreator.ssc.init;

import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.ssc14.client.Ssc14CuriosRenderers;

/** @deprecated Use {@link Ssc14CuriosRenderers}. */
@Deprecated(forRemoval = true)
public final class Ssc14ModCuriosRenderers {
    private Ssc14ModCuriosRenderers() {
    }

    public static void registerRenderers(FMLClientSetupEvent event) {
        Ssc14CuriosRenderers.registerRenderers(event);
    }
}
