package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.item.medical.MedicalSupplyItem;
import org.junit.jupiter.api.Test;

class Ssc14MedicalSupplyItemsCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.of(
            "BRUTEPACK", "brutepack",
            "BLOODPACK", "bloodpack",
            "GAUZE", "gauze",
            "OINTMENT", "ointment");

    private static final Class<?>[] LEGACY_TYPES = {
            net.mcreator.ssc.item.BrutepackItem.class,
            net.mcreator.ssc.item.BloodpackItem.class,
            net.mcreator.ssc.item.GauzeItem.class,
            net.mcreator.ssc.item.OintmentItem.class
    };

    @Test
    void canonicalRegistryOwnsMedicalSupplyIdsAndLegacyHoldersRemainAliases() throws Exception {
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
    void legacyMedicalSupplyTypesDelegateToCanonicalImplementation() throws Exception {
        for (Class<?> legacyType : LEGACY_TYPES) {
            assertTrue(MedicalSupplyItem.class.isAssignableFrom(legacyType), legacyType.getName());
            legacyType.getConstructor(Item.Properties.class);
        }
    }
}
