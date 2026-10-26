package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.item.communication.RadioEncryptionKeyItem;
import org.junit.jupiter.api.Test;

class Ssc14EncryptionKeyItemsCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.of(
            "ENCRYPTION_KEY_PASSANGER", "encryption_key_passanger",
            "ENCRYPTION_KEY_COMMAND", "encryption_key_command",
            "ENCRYPTION_KEY_ENGENEER", "encryption_key_engeneer",
            "ENCRYPTION_KEY_MEDICAL", "encryption_key_medical",
            "ENCRYPTION_KEY_RND", "encryption_key_rnd",
            "ENCRYPTION_KEY_SECURITY", "encryption_key_security",
            "ENCRYPTION_KEY_SERVICE", "encryption_key_service",
            "ENCRYPTION_KEY_CARGO", "encryption_key_cargo");

    private static final Class<?>[] LEGACY_TYPES = {
            net.mcreator.ssc.item.EncryptionKeyPassangerItem.class,
            net.mcreator.ssc.item.EncryptionKeyCommandItem.class,
            net.mcreator.ssc.item.EncryptionKeyEngeneerItem.class,
            net.mcreator.ssc.item.EncryptionKeyMedicalItem.class,
            net.mcreator.ssc.item.EncryptionKeyRNDItem.class,
            net.mcreator.ssc.item.EncryptionKeySecurityItem.class,
            net.mcreator.ssc.item.EncryptionKeyServiceItem.class,
            net.mcreator.ssc.item.EncryptionKeyCargoItem.class
    };

    @Test
    void canonicalRegistryOwnsEncryptionKeyIdsAndLegacyHoldersRemainAliases() throws Exception {
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
    void legacyEncryptionKeyTypesDelegateToCanonicalImplementation() throws Exception {
        for (Class<?> legacyType : LEGACY_TYPES) {
            assertTrue(RadioEncryptionKeyItem.class.isAssignableFrom(legacyType), legacyType.getName());
            legacyType.getConstructor(Item.Properties.class);
        }
    }
}
