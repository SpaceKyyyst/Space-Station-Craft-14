package net.mcreator.ssc.init;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.ssc14.client.Ssc14Screens;

/** @deprecated Use {@link Ssc14Screens}. */
@Deprecated(forRemoval = true)
public final class Ssc14ModScreens {
    private Ssc14ModScreens() {
    }

    /** Legacy type alias retained for compatibility tests and integrations. */
    public interface ScreenAccessor extends Ssc14Screens.ScreenAccessor {
    }

    public static void clientLoad(RegisterMenuScreensEvent event) {
        Ssc14Screens.clientLoad(event);
    }
}
