package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.item.clothing.UniformAccessoryItem;
import org.junit.jupiter.api.Test;

class Ssc14UniformAccessoryItemsCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.ofEntries(
            Map.entry("TIE_BLACK", "tie_black"),
            Map.entry("TIE_RED", "tie_red"),
            Map.entry("TIE_SCIENCE", "tie_science"),
            Map.entry("STOCKINGS_BLUE", "stockings_blue"),
            Map.entry("STOCKINGS_CYAN", "stockings_cyan"),
            Map.entry("STOCKINGS_D_PINK", "stockings_d_pink"),
            Map.entry("STOCKINGS_GREEN", "stockings_green"),
            Map.entry("STOCKINGS_L_PINK", "stockings_l_pink"),
            Map.entry("STOCKINGS_ORANGE", "stockings_orange"),
            Map.entry("STOCKINGS_PURPLE", "stockings_purple"),
            Map.entry("STOCKINGS_YELLOW", "stockings_yellow"));

    private static final Class<?>[] LEGACY_TYPES = {
            net.mcreator.ssc.item.TieBlackItem.class,
            net.mcreator.ssc.item.TieRedItem.class,
            net.mcreator.ssc.item.TieScienceItem.class,
            net.mcreator.ssc.item.StockingsBlueItem.class,
            net.mcreator.ssc.item.StockingsCyanItem.class,
            net.mcreator.ssc.item.StockingsDPinkItem.class,
            net.mcreator.ssc.item.StockingsGreenItem.class,
            net.mcreator.ssc.item.StockingsLPinkItem.class,
            net.mcreator.ssc.item.StockingsOrangeItem.class,
            net.mcreator.ssc.item.StockingsPurpleItem.class,
            net.mcreator.ssc.item.StockingsYellowItem.class
    };

    @Test
    void canonicalRegistryOwnsAccessoryIdsAndLegacyHoldersRemainAliases() throws Exception {
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
    void legacyAccessoryTypesDelegateToCanonicalImplementation() throws Exception {
        for (Class<?> legacyType : LEGACY_TYPES) {
            assertTrue(UniformAccessoryItem.class.isAssignableFrom(legacyType), legacyType.getName());
            legacyType.getConstructor(Item.Properties.class);
        }
    }
}
