package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.item.clothing.ScarfItem;
import org.junit.jupiter.api.Test;

class Ssc14ScarfItemsCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.ofEntries(
            Map.entry("RED_SCARF", "red_scarf"),
            Map.entry("BLACK_SCARF", "black_scarf"),
            Map.entry("BLACK_GREEN_SCARF", "black_green_scarf"),
            Map.entry("BLUE_SCARF", "blue_scarf"),
            Map.entry("BROWN_SCARF", "brown_scarf"),
            Map.entry("GREEN_SCARF", "green_scarf"),
            Map.entry("LIGHT_BLUE_SCARF", "light_blue_scarf"),
            Map.entry("LIGHT_BLUE_WHITE_SCARF", "light_blue_white_scarf"),
            Map.entry("ORANGE_SCARF", "orange_scarf"),
            Map.entry("PURPLE_SCARF", "purple_scarf"),
            Map.entry("SINDY_SCARF", "sindy_scarf"),
            Map.entry("TRNS_SCARF", "trns_scarf"),
            Map.entry("YELLOW_SCARF", "yellow_scarf"));

    private static final Class<?>[] LEGACY_TYPES = {
            net.mcreator.ssc.item.RedScarfItem.class,
            net.mcreator.ssc.item.BlackScarfItem.class,
            net.mcreator.ssc.item.BlackGreenScarfItem.class,
            net.mcreator.ssc.item.BlueScarfItem.class,
            net.mcreator.ssc.item.BrownScarfItem.class,
            net.mcreator.ssc.item.GreenScarfItem.class,
            net.mcreator.ssc.item.LightBlueScarfItem.class,
            net.mcreator.ssc.item.LightBlueWhiteScarfItem.class,
            net.mcreator.ssc.item.OrangeScarfItem.class,
            net.mcreator.ssc.item.PurpleScarfItem.class,
            net.mcreator.ssc.item.SindyScarfItem.class,
            net.mcreator.ssc.item.TrnsScarfItem.class,
            net.mcreator.ssc.item.YellowScarfItem.class
    };

    @Test
    void canonicalRegistryOwnsScarfIdsAndLegacyHoldersRemainAliases() throws Exception {
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
    void legacyScarfTypesDelegateToCanonicalImplementation() throws Exception {
        for (Class<?> legacyType : LEGACY_TYPES) {
            assertTrue(ScarfItem.class.isAssignableFrom(legacyType), legacyType.getName());
            legacyType.getConstructor(Item.Properties.class);
        }
    }
}
