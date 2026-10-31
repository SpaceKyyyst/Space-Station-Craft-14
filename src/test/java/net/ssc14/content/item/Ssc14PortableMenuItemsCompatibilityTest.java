package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14PortableMenuItemsCompatibilityTest {
    private static final List<ExpectedItem> ITEMS = List.of(
            item("ACCESS_CONFIG", "access_config", "AccessConfigItem"),
            item("BACKPACK_2", "backpack_2", "Backpack2Item"),
            item("PD_APASSANGER", "pd_apassanger", "PDApassangerItem"),
            item("EGG_CARTON", "egg_carton", "EggCartonItem"));

    @Test
    void canonicalRegistryOwnsPortableMenuIdsAndPreservesCanonicalIds()
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
    void legacyTypesDelegateToCanonicalPortableMenuRole() throws Exception {
        Class<?> canonicalType = Class.forName(
                "net.ssc14.content.item.container.PortableMenuItem");
        for (ExpectedItem expected : ITEMS.subList(2, ITEMS.size())) {
            Class<?> legacyType = Class.forName(
                    "net.mcreator.ssc.item." + expected.legacyClassName());
            assertTrue(canonicalType.isAssignableFrom(legacyType),
                    expected.legacyClassName());
        }
    }

    @Test
    void canonicalKindsExposeStableInventorySlotCounts() throws Exception {
        Class<?> kindType = Class.forName(
                "net.ssc14.content.item.container.PortableMenuItemKind");
        var inventorySlots = kindType.getMethod("inventorySlots");
        assertEquals(1, inventorySlots.invoke(enumValue(kindType, "ACCESS_CONFIG")));
        assertEquals(21, inventorySlots.invoke(enumValue(kindType, "BACKPACK")));
        assertEquals(2, inventorySlots.invoke(enumValue(kindType, "PASSENGER_PDA")));
        assertEquals(11, inventorySlots.invoke(enumValue(kindType, "EGG_CARTON")));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Object enumValue(Class<?> enumType, String name) {
        return Enum.valueOf((Class<? extends Enum>) enumType.asSubclass(Enum.class), name);
    }

    private static ExpectedItem item(String fieldName, String id, String legacyClassName) {
        return new ExpectedItem(fieldName, id, legacyClassName);
    }

    private record ExpectedItem(String fieldName, String id, String legacyClassName) { }
}
