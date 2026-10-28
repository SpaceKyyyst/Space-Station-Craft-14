package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14FirearmItemsCompatibilityTest {
    private static final List<ExpectedItem> ITEMS = List.of(
            item("MK_58", "mk_58", "MK58Item", "Mk58Item"),
            item("MK_58OPEN", "mk_58open", "MK58openItem", "Mk58Item"),
            item("MK_58NOMAGZ", "mk_58nomagz", "MK58nomagzItem", "Mk58Item"),
            item("MK_58OPEN_NO_MAGZ", "mk_58open_no_magz",
                    "MK58openNoMagzItem", "Mk58Item"),
            item("MAGAZINE_PISTOL_35", "magazine_pistol_35",
                    "MagazinePistol35Item", "PistolMagazineItem"),
            item("MAGAZINE_PISTOL_35EMPTY", "magazine_pistol_35empty",
                    "MagazinePistol35emptyItem", "PistolMagazineItem"));

    @Test
    void canonicalRegistryOwnsFirearmIdsAndLegacyHoldersRemainAliases()
            throws Exception {
        for (ExpectedItem expected : ITEMS) {
            Field canonicalField = Ssc14Items.class.getField(expected.fieldName());
            Field legacyField = Ssc14ModItems.class.getField(expected.fieldName());
            Object canonicalHolder = canonicalField.get(null);
            assertSame(canonicalHolder, legacyField.get(null), expected.fieldName());
            assertEquals("ssc_14:" + expected.id(),
                    ((DeferredHolder<?, ?>) canonicalHolder).getId().toString(),
                    expected.fieldName());
        }
    }

    @Test
    void legacyTypesDelegateToCanonicalFirearmTypes() throws Exception {
        for (ExpectedItem expected : ITEMS) {
            Class<?> canonicalType = Class.forName(
                    "net.ssc14.content.item.weapon." + expected.canonicalClassName());
            Class<?> legacyType = Class.forName(
                    "net.mcreator.ssc.item." + expected.legacyClassName());
            assertTrue(canonicalType.isAssignableFrom(legacyType),
                    expected.legacyClassName());
        }
    }

    @Test
    void canonicalStateEnumsExposeEveryPersistedVisualState() throws Exception {
        Class<?> mk58State = Class.forName("net.ssc14.content.item.weapon.Mk58State");
        enumValue(mk58State, "CLOSED_WITH_MAGAZINE");
        enumValue(mk58State, "OPEN_WITH_MAGAZINE");
        enumValue(mk58State, "CLOSED_NO_MAGAZINE");
        enumValue(mk58State, "OPEN_NO_MAGAZINE");

        Class<?> magazineState = Class.forName(
                "net.ssc14.content.item.weapon.PistolMagazineState");
        enumValue(magazineState, "LOADED");
        enumValue(magazineState, "EMPTY");
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
