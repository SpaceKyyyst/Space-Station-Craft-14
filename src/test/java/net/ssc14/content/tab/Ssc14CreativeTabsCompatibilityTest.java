package net.ssc14.content.tab;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.util.List;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14CreativeTabsCompatibilityTest {
    private static final List<Expected> EXPECTED = List.of(
            new Expected("SSC_14_COATING", "ssc_14_coating"),
            new Expected("SSC_14_WALLS", "ssc_14_walls"),
            new Expected("SSC_14_OBJECTS", "ssc_14_objects"),
            new Expected("SSC_14_ITEMS", "ssc_14_items"),
            new Expected("SSC_14MOBS", "ssc_14mobs"),
            new Expected("DECALS", "decals"));

    @Test
    void canonicalRegistryOwnsTabs()
            throws ReflectiveOperationException {
        for (Expected expected : EXPECTED) {
            Field field = Ssc14CreativeTabs.class.getField(expected.field());
            Object canonical = field.get(null);
            assertEquals("ssc_14:" + expected.path(),
                    ((DeferredHolder<?, ?>) canonical).getId().toString());
        }
    }

    private record Expected(String field, String path) { }
}
