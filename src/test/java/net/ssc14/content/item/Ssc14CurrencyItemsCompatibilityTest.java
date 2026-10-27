package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14CurrencyItemsCompatibilityTest {
    private static final List<ExpectedItem> ITEMS = List.of(
            item("CREDITS", "credits", "CreditsItem"),
            item("CREDITS_50", "credits_50", "Credits50Item"),
            item("DEBU_GCREDITS_DELETER", "debu_gcredits_deleter",
                    "DEBUGcreditsDELETERItem"));

    @Test
    void canonicalRegistryOwnsCurrencyIdsAndLegacyHoldersRemainAliases()
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
    void legacyTypesDelegateToCanonicalCurrencyRole() throws Exception {
        Class<?> canonicalType = Class.forName(
                "net.ssc14.content.item.currency.CurrencyItem");
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
