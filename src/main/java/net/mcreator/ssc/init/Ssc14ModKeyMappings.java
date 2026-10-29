package net.mcreator.ssc.init;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyMapping.Category;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.ssc14.client.Ssc14KeyMappings;

/** @deprecated Use {@link Ssc14KeyMappings}. */
@Deprecated(forRemoval = true)
public final class Ssc14ModKeyMappings {
    public static final Category CATEGORY_SSC14 = Ssc14KeyMappings.CATEGORY_SSC14;
    public static final KeyMapping DC_MOPEN = Ssc14KeyMappings.DC_MOPEN;
    public static final KeyMapping ROTATE = Ssc14KeyMappings.ROTATE;
    public static final KeyMapping PULLTHE_OBJECT = Ssc14KeyMappings.PULLTHE_OBJECT;
    public static final KeyMapping WORLD_OBJECT_CHECK = Ssc14KeyMappings.WORLD_OBJECT_CHECK;
    public static final KeyMapping CRAWL = Ssc14KeyMappings.CRAWL;
    public static final KeyMapping INTERACTION = Ssc14KeyMappings.INTERACTION;
    public static final KeyMapping SPECIFY = Ssc14KeyMappings.SPECIFY;

    private Ssc14ModKeyMappings() {
    }

    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        Ssc14KeyMappings.registerKeyMappings(event);
    }
}
