package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.item.food.FoodPackagingWasteItem;
import org.junit.jupiter.api.Test;

class Ssc14FoodPackagingWasteItemsCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.of(
            "ENERGY_BAR_TRASH", "energy_bar_trash",
            "CHEESE_HONKERS_TRASH", "cheese_honkers_trash",
            "CHIPS_TRASH", "chips_trash",
            "BORITOS_TRASH", "boritos_trash",
            "POPCORN_TRASH", "popcorn_trash");

    private static final Class<?>[] LEGACY_TYPES = {
            net.mcreator.ssc.item.EnergyBarTrashItem.class,
            net.mcreator.ssc.item.CheeseHonkersTrashItem.class,
            net.mcreator.ssc.item.ChipsTrashItem.class,
            net.mcreator.ssc.item.BoritosTrashItem.class,
            net.mcreator.ssc.item.PopcornTrashItem.class
    };

    @Test
    void canonicalRegistryOwnsFoodWasteIdsAndPreservesCanonicalIds() throws Exception {
        for (Map.Entry<String, String> expected : EXPECTED_HOLDERS.entrySet()) {
            Field canonicalField = Ssc14Items.class.getField(expected.getKey());
            Object canonicalHolder = canonicalField.get(null);
            assertEquals("ssc_14:" + expected.getValue(),
                    ((DeferredHolder<?, ?>) canonicalHolder).getId().toString(), expected.getKey());
        }
    }

    @Test
    void legacyFoodWasteTypesDelegateToCanonicalImplementation() throws Exception {
        for (Class<?> legacyType : LEGACY_TYPES) {
            assertTrue(FoodPackagingWasteItem.class.isAssignableFrom(legacyType), legacyType.getName());
            legacyType.getConstructor(Item.Properties.class);
        }
    }
}
