package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14EdibleFoodItemsCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.ofEntries(
            Map.entry("BREAD", "bread"),
            Map.entry("BREAD_SLICE", "bread_slice"),
            Map.entry("BUN", "bun"),
            Map.entry("TOFU", "tofu"),
            Map.entry("TOFU_BURGER", "tofu_burger"),
            Map.entry("TOFU_SLICE", "tofu_slice"),
            Map.entry("CHEESE", "cheese"),
            Map.entry("CUTLET", "cutlet"),
            Map.entry("FRIED_CUTLET", "fried_cutlet"),
            Map.entry("MEAT", "meat"),
            Map.entry("FRIED_MEAT", "fried_meat"),
            Map.entry("ROTTEN_MEAT", "rotten_meat"));

    private static final String[] LEGACY_TYPES = {
        "BreadItem", "BreadSliceItem", "BunItem", "TofuItem", "TofuBurgerItem",
        "TofuSliceItem", "CheeseItem", "CutletItem", "FriedCutletItem", "MeatItem",
        "FriedMeatItem", "RottenMeatItem"
    };

    @Test
    void canonicalRegistryOwnsFoodIdsAndLegacyHoldersRemainAliases() throws Exception {
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
    void legacyFoodTypesDelegateToCanonicalImplementation() throws Exception {
        Class<?> canonicalType = Class.forName("net.ssc14.content.item.food.EdibleFoodItem");
        for (String legacyType : LEGACY_TYPES) {
            Class<?> type = Class.forName("net.mcreator.ssc.item." + legacyType);
            assertTrue(canonicalType.isAssignableFrom(type), legacyType);
        }
    }
}
