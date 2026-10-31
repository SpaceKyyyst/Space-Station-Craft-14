package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14CookingIngredientItemsCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.of(
            "DOUGH", "dough",
            "DOUGH_PIECE", "dough_piece",
            "EGG", "egg",
            "EGGSHELL", "eggshell");

    @Test
    void canonicalRegistryOwnsIngredientIdsAndPreservesCanonicalIds() throws Exception {
        for (Map.Entry<String, String> expected : EXPECTED_HOLDERS.entrySet()) {
            Field canonicalField = Ssc14Items.class.getField(expected.getKey());
            Object canonicalHolder = canonicalField.get(null);
            assertEquals("ssc_14:" + expected.getValue(),
                    ((DeferredHolder<?, ?>) canonicalHolder).getId().toString(), expected.getKey());
        }
    }

    @Test
    void legacyIngredientTypesDelegateToCanonicalImplementation() throws Exception {
        Class<?> canonicalType = Class.forName("net.ssc14.content.item.food.CookingIngredientItem");
        assertTrue(canonicalType.isAssignableFrom(net.mcreator.ssc.item.DoughItem.class));
        assertTrue(canonicalType.isAssignableFrom(net.mcreator.ssc.item.DoughPieceItem.class));
        assertTrue(canonicalType.isAssignableFrom(net.mcreator.ssc.item.EggItem.class));
        assertTrue(canonicalType.isAssignableFrom(net.mcreator.ssc.item.EggshellItem.class));
    }
}
