package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.item.food.EdibleFoodItem;
import org.junit.jupiter.api.Test;

class Ssc14PackagedFoodItemsCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.of(
            "ENERGY_BAR_2", "energy_bar_2",
            "CATBURGER", "catburger",
            "CHEESEBURGER", "cheeseburger",
            "CHEESE_HONKERS", "cheese_honkers",
            "CHIPS", "chips",
            "BORITOS", "boritos",
            "POPCORN", "popcorn");

    private static final String[] LEGACY_TYPES = {
        "EnergyBar2Item", "CatburgerItem", "CheeseburgerItem", "CheeseHonkersItem",
        "ChipsItem", "BoritosItem", "PopcornItem"
    };

    @Test
    void canonicalRegistryOwnsPackagedFoodIdsAndLegacyHoldersRemainAliases() throws Exception {
        for (Map.Entry<String, String> expected : EXPECTED_HOLDERS.entrySet()) {
            Field canonicalField = Ssc14Items.class.getField(expected.getKey());
            Field legacyField = Ssc14ModItems.class.getField(expected.getKey());
            Object canonicalHolder = canonicalField.get(null);
            assertSame(canonicalHolder, legacyField.get(null), expected.getKey());
            assertEquals("ssc_14:" + expected.getValue(),
                    ((DeferredHolder<?, ?>) canonicalHolder).getId().toString(), expected.getKey());
        }
    }

    @Test
    void legacyPackagedFoodTypesDelegateToCanonicalImplementation() throws Exception {
        for (String legacyType : LEGACY_TYPES) {
            Class<?> type = Class.forName("net.mcreator.ssc.item." + legacyType);
            assertTrue(EdibleFoodItem.class.isAssignableFrom(type), legacyType);
        }
    }
}
