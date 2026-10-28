package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14FoodPropItemsCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.ofEntries(
            Map.entry("BAGEL", "bagel"),
            Map.entry("BAGUETTE", "baguette"),
            Map.entry("BANANA_NUT_BREAD", "banana_nut_bread"),
            Map.entry("CHEESE_WHEEL", "cheese_wheel"),
            Map.entry("CREAM_CHEESE_BREAD", "cream_cheese_bread"),
            Map.entry("FLY_AMANITA", "fly_amanita"),
            Map.entry("MEATLOAF", "meatloaf"),
            Map.entry("MOLDY_BREAD_PIECE", "moldy_bread_piece"),
            Map.entry("POPPY_SEED_BAGEL", "poppy_seed_bagel"),
            Map.entry("POTATO", "potato"),
            Map.entry("SAUSAGE_BREAD", "sausage_bread"),
            Map.entry("SPACE_FRIES", "space_fries"),
            Map.entry("TACO_FLATBREAD", "taco_flatbread"),
            Map.entry("TOAST_WITH_BUTTER", "toast_with_butter"),
            Map.entry("TOFU_BREAD", "tofu_bread"));

    private static final String[] LEGACY_TYPES = {
        "BagelItem", "BaguetteItem", "BananaNutBreadItem", "CheeseWheelItem",
        "CreamCheeseBreadItem", "FlyAmanitaItem", "MeatloafItem", "MoldyBreadPieceItem",
        "PoppySeedBagelItem", "PotatoItem", "SausageBreadItem", "SpaceFriesItem",
        "TacoFlatbreadItem", "ToastWithButterItem", "TofuBreadItem"
    };

    @Test
    void canonicalRegistryOwnsFoodPropIdsAndLegacyHoldersRemainAliases() throws Exception {
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
    void legacyFoodPropTypesDelegateToCanonicalImplementation() throws Exception {
        Class<?> canonicalType = Class.forName("net.ssc14.content.item.food.FoodPropItem");
        for (String legacyType : LEGACY_TYPES) {
            Class<?> type = Class.forName("net.mcreator.ssc.item." + legacyType);
            assertTrue(canonicalType.isAssignableFrom(type), legacyType);
        }
    }
}
