package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14MechanicalToolItemsCompatibilityTest {
    private static final List<ExpectedItem> ITEMS = List.of(
            item("CROWBAR", "crowbar", "ScrapItem", "MechanicalHandToolItem"),
            item("NIPPERS", "nippers", "NippersItem", "MechanicalHandToolItem"),
            item("FIRE_AXE", "fire_axe", "FireAxeItem", "MechanicalHandToolItem"),
            item("SCREWDRIVER", "screwdriver", "ScrewdriverItem",
                    "MechanicalHandToolItem"),
            item("SPANNER", "spanner", "SpannerItem", "MechanicalHandToolItem"),
            item("PICK", "pick", "PickItem", "AsteroidPickItem"));

    @Test
    void canonicalRegistryOwnsMechanicalToolIdsAndPreservesCanonicalIds()
            throws Exception {
        for (ExpectedItem expected : ITEMS) {
            Field canonicalField = Ssc14Items.class.getField(expected.fieldName());
            Object canonicalHolder = canonicalField.get(null);
            assertTrue(((DeferredHolder<?, ?>) canonicalHolder).getId().toString()
                    .equals("ssc_14:" + expected.id()), expected.fieldName());
        }
    }

    @Test
    void legacyTypesDelegateToCanonicalMechanicalToolTypes() throws Exception {
        for (ExpectedItem expected : ITEMS) {
            Class<?> canonicalType = Class.forName(
                    "net.ssc14.content.item.tool." + expected.canonicalClassName());
            Class<?> legacyType = Class.forName(
                    "net.mcreator.ssc.item." + expected.legacyClassName());
            assertTrue(canonicalType.isAssignableFrom(legacyType),
                    expected.legacyClassName());
        }
    }

    @Test
    void canonicalProfilesCoverEveryMechanicalTool() throws Exception {
        Class<?> profile = Class.forName(
                "net.ssc14.content.item.tool.MechanicalToolProfile");
        for (String name : List.of(
                "CROWBAR", "NIPPERS", "FIRE_AXE", "SCREWDRIVER", "SPANNER",
                "PICK")) {
            enumValue(profile, name);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Object enumValue(Class<?> enumType, String name) {
        return Enum.valueOf((Class<? extends Enum>) enumType.asSubclass(Enum.class), name);
    }

    private static ExpectedItem item(
            String fieldName,
            String id,
            String legacyClassName,
            String canonicalClassName) {
        return new ExpectedItem(fieldName, id, legacyClassName, canonicalClassName);
    }

    private record ExpectedItem(
            String fieldName,
            String id,
            String legacyClassName,
            String canonicalClassName) { }
}
