package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.item.lighting.LightingComponentItem;
import org.junit.jupiter.api.Test;

class Ssc14LightingComponentItemsCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.of(
            "LED_LIGHT_TUBE", "led_light_tube",
            "LIGHT_BULB", "light_bulb",
            "LED_LIGHT_TUBE_BROKEN", "led_light_tube_broken",
            "LIGHT_BULB_BROKEN", "light_bulb_broken");

    private static final Class<?>[] LEGACY_TYPES = {
            net.mcreator.ssc.item.LedLightTubeItem.class,
            net.mcreator.ssc.item.LightBulbItem.class,
            net.mcreator.ssc.item.LedLightTubeBrokenItem.class,
            net.mcreator.ssc.item.LightBulbBrokenItem.class
    };

    @Test
    void canonicalRegistryOwnsLightingIdsAndPreservesCanonicalIds() throws Exception {
        for (Map.Entry<String, String> expected : EXPECTED_HOLDERS.entrySet()) {
            Field canonicalField = Ssc14Items.class.getField(expected.getKey());
            Object canonicalHolder = canonicalField.get(null);
            assertEquals("ssc_14:" + expected.getValue(),
                    ((DeferredHolder<?, ?>) canonicalHolder).getId().toString(), expected.getKey());
        }
    }

    @Test
    void legacyLightingTypesDelegateToCanonicalImplementation() throws Exception {
        for (Class<?> legacyType : LEGACY_TYPES) {
            assertTrue(LightingComponentItem.class.isAssignableFrom(legacyType), legacyType.getName());
            legacyType.getConstructor(Item.Properties.class);
        }
    }
}
