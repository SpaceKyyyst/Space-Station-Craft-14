package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14InertItemsCompatibilityTest {
    private static final List<ExpectedItem> ITEMS = List.of(
            item("ROOD", "rood", "RoodItem",
                    "net.ssc14.content.item.material.MaterialItem"),
            item("WOOD", "wood", "WoodItem",
                    "net.ssc14.content.item.material.MaterialItem"),
            item("PEN", "pen", "PenItem",
                    "net.ssc14.content.item.utility.UtilityItem"),
            item("HEALTH_ANALYZER", "health_analyzer", "HealthAnalyzerItem",
                    "net.ssc14.content.item.utility.UtilityItem"),
            item("BULLET_35ITEM", "bullet_35item", "Bullet35itemItem",
                    "net.ssc14.content.item.combat.AmmunitionItem"),
            item("FOAM_DART", "foam_dart", "FoamDartItem",
                    "net.ssc14.content.item.combat.AmmunitionItem"),
            item("BULLET_35_CARTRIDGE", "bullet_35_cartridge", "Bullet35CartridgeItem",
                    "net.ssc14.content.item.combat.AmmunitionItem"));

    @Test
    void canonicalRegistryOwnsInertItemIdsAndPreservesCanonicalIds() throws Exception {
        for (ExpectedItem expected : ITEMS) {
            Field canonicalField = Ssc14Items.class.getField(expected.fieldName());
            Object canonicalHolder = canonicalField.get(null);
            assertEquals("ssc_14:" + expected.id(),
                    ((DeferredHolder<?, ?>) canonicalHolder).getId().toString(),
                    expected.fieldName());
        }
    }

    @Test
    void legacyTypesDelegateToTheirCanonicalRole() throws Exception {
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
