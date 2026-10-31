package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.item.material.MaterialItem;
import org.junit.jupiter.api.Test;

class Ssc14MaterialItemsCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.ofEntries(
            Map.entry("SILVER", "silver"),
            Map.entry("SSC_14_GOLD", "ssc_14_gold"),
            Map.entry("URAN", "uran"),
            Map.entry("PLASMA", "plasma"),
            Map.entry("PLASTIC", "plastic"),
            Map.entry("GLASS", "glass"),
            Map.entry("ARM_GLASS", "arm_glass"),
            Map.entry("RAW_IRON", "raw_iron"),
            Map.entry("RAW_QUARTZ", "raw_quartz"),
            Map.entry("RAW_COAL", "raw_coal"),
            Map.entry("RAW_GOLD", "raw_gold"),
            Map.entry("RAW_PLASMA", "raw_plasma"),
            Map.entry("RAW_SILVER", "raw_silver"),
            Map.entry("RAW_URAN", "raw_uran"),
            Map.entry("RAW_DIAMOND", "raw_diamond"));

    private static final Class<?>[] LEGACY_TYPES = {
            net.mcreator.ssc.item.SilverItem.class,
            net.mcreator.ssc.item.SSC14GoldItem.class,
            net.mcreator.ssc.item.UranItem.class,
            net.mcreator.ssc.item.PlasmaItem.class,
            net.mcreator.ssc.item.PlasticItem.class,
            net.mcreator.ssc.item.GlassItem.class,
            net.mcreator.ssc.item.ArmGlassItem.class,
            net.mcreator.ssc.item.RawIronItem.class,
            net.mcreator.ssc.item.RawQuartzItem.class,
            net.mcreator.ssc.item.RawCoalItem.class,
            net.mcreator.ssc.item.RawGoldItem.class,
            net.mcreator.ssc.item.RawPlasmaItem.class,
            net.mcreator.ssc.item.RawSilverItem.class,
            net.mcreator.ssc.item.RawUranItem.class,
            net.mcreator.ssc.item.RawDiamondItem.class
    };

    @Test
    void canonicalRegistryOwnsMaterialIdsAndPreservesCanonicalIds() throws Exception {
        for (Map.Entry<String, String> expected : EXPECTED_HOLDERS.entrySet()) {
            Field canonicalField = Ssc14Items.class.getField(expected.getKey());
            Object canonicalHolder = canonicalField.get(null);
            assertEquals("ssc_14:" + expected.getValue(),
                    ((DeferredHolder<?, ?>) canonicalHolder).getId().toString(), expected.getKey());
        }
    }

    @Test
    void legacyMaterialTypesDelegateToCanonicalImplementation() throws Exception {
        for (Class<?> legacyType : LEGACY_TYPES) {
            assertTrue(MaterialItem.class.isAssignableFrom(legacyType), legacyType.getName());
            legacyType.getConstructor(Item.Properties.class);
        }
    }
}
