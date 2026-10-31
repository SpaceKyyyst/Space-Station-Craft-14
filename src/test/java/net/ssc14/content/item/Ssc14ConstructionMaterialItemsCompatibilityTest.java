package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14ConstructionMaterialItemsCompatibilityTest {
    private static final List<ExpectedItem> ITEMS = List.of(
            item("STEEL", "steel", "SteelItem",
                    "net.ssc14.content.item.construction.ConstructionSheetItem"),
            item("PLASTEEL", "plasteel", "PlasteelItem",
                    "net.ssc14.content.item.construction.ConstructionSheetItem"),
            item("LOW_VOLTAGE_CABLE", "low_voltage_cable", "LowVoltageCableItem",
                    "net.ssc14.content.item.construction.CableCoilItem"),
            item("MEDIUM_VOLTAGE_CABLE", "medium_voltage_cable",
                    "MediumVoltageCableItem",
                    "net.ssc14.content.item.construction.CableCoilItem"),
            item("HIGH_VOLTAGE_CABLE", "high_voltage_cable", "HighVoltageCableItem",
                    "net.ssc14.content.item.construction.CableCoilItem"));

    @Test
    void canonicalRegistryOwnsConstructionMaterialIdsAndPreservesCanonicalIds()
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
    void legacyTypesDelegateToCanonicalConstructionRoles() throws Exception {
        for (ExpectedItem expected : ITEMS) {
            Class<?> canonicalType = Class.forName(expected.canonicalClassName());
            Class<?> legacyType = Class.forName(
                    "net.mcreator.ssc.item." + expected.legacyClassName());
            assertTrue(canonicalType.isAssignableFrom(legacyType), expected.legacyClassName());
        }
    }

    private static ExpectedItem item(
            String fieldName, String id, String legacyClassName, String canonicalClassName) {
        return new ExpectedItem(fieldName, id, legacyClassName, canonicalClassName);
    }

    private record ExpectedItem(
            String fieldName, String id, String legacyClassName, String canonicalClassName) { }
}
