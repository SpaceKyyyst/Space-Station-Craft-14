package net.ssc14.content.tab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Field;
import java.util.List;
import net.mcreator.ssc.init.Ssc14ModTabs;
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
    void canonicalRegistryOwnsTabsAndLegacyHoldersRemainAliases()
            throws ReflectiveOperationException {
        assertSame(Ssc14CreativeTabs.REGISTRY, Ssc14ModTabs.REGISTRY);
        for (Expected expected : EXPECTED) {
            Field field = Ssc14CreativeTabs.class.getField(expected.field());
            Object canonical = field.get(null);
            assertSame(canonical, Ssc14ModTabs.class.getField(expected.field()).get(null));
            assertEquals("ssc_14:" + expected.path(),
                    ((DeferredHolder<?, ?>) canonical).getId().toString());
        }
    }

    private record Expected(String field, String path) { }
}
