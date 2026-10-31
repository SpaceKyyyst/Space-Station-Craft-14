package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14DecalToolItemsCompatibilityTest {
    private static final List<ExpectedItem> ITEMS = List.of(
            item("DECAL_1SPAWNER", "decal_1spawner", "DECAL1spawnerItem"),
            item("DECAL_1DELETION", "decal_1deletion", "DECAL1deletionItem"),
            item("DECAL_2SPAWNER", "decal_2spawner", "DECAL2spawnerItem"),
            item("DECAL_3SPAWNER", "decal_3spawner", "DECAL3spawnerItem"),
            item("DECAL_4SPAWNER", "decal_4spawner", "DECAL4spawnerItem"),
            item("DECAL_5SPAWNER", "decal_5spawner", "DECAL5spawnerItem"),
            item("DECAL_6SPAWNER", "decal_6spawner", "DECAL6spawnerItem"));

    @Test
    void canonicalRegistryOwnsDecalToolIdsAndPreservesCanonicalIds()
            throws Exception {
        for (ExpectedItem expected : ITEMS) {
            Field canonicalField = Ssc14Items.class.getField(expected.fieldName());
            Object canonicalHolder = canonicalField.get(null);
            assertEquals("ssc_14:" + expected.id(),
                    ((DeferredHolder<?, ?>) canonicalHolder).getId().toString(),
                    expected.fieldName());
        }
    }

    @Test
    void legacyTypesDelegateToCanonicalDecalToolRole() throws Exception {
        Class<?> canonicalType = Class.forName(
                "net.ssc14.content.item.decal.DecalToolItem");
        for (ExpectedItem expected : ITEMS) {
            Class<?> legacyType = Class.forName(
                    "net.mcreator.ssc.item." + expected.legacyClassName());
            assertTrue(canonicalType.isAssignableFrom(legacyType),
                    expected.legacyClassName());
        }
    }

    private static ExpectedItem item(String fieldName, String id, String legacyClassName) {
        return new ExpectedItem(fieldName, id, legacyClassName);
    }

    private record ExpectedItem(String fieldName, String id, String legacyClassName) { }
}
