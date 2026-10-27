package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14ReagentContainerItemsCompatibilityTest {
    private static final String CONTAINER_TYPE =
            "net.ssc14.content.item.reagent.ReagentContainerItem";
    private static final String SEALED_TYPE =
            "net.ssc14.content.item.reagent.SealedReagentContainerItem";
    private static final List<ExpectedItem> ITEMS = List.of(
            item("REAGENT_CONTAINER", "reagent_container", "ReagentContainerItem",
                    CONTAINER_TYPE),
            item("FLOUR_OPEN", "flour_open", "FlourOpenItem", CONTAINER_TYPE),
            item("SUGAR_OPEN", "sugar_open", "SugarOpenItem", CONTAINER_TYPE),
            item("MILK_OPEN", "milk_open", "MilkOpenItem", CONTAINER_TYPE),
            item("ALL_PURPOSE_SAUCE_OPEN", "all_purpose_sauce_open",
                    "AllPurposeSauceOpenItem", CONTAINER_TYPE),
            item("FLOUR", "flour", "FlourItem", SEALED_TYPE),
            item("SUGAR", "sugar", "SugarItem", SEALED_TYPE),
            item("MILK", "milk", "MilkItem", SEALED_TYPE),
            item("ALL_PURPOSE_SAUCE", "all_purpose_sauce",
                    "AllPurposeSauceItem", SEALED_TYPE));

    @Test
    void canonicalRegistryOwnsReagentContainerIdsAndLegacyHoldersRemainAliases()
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
    void legacyTypesDelegateToCanonicalReagentContainerRoles() throws Exception {
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
