package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14HeadsetItemsCompatibilityTest {
    private static final List<ExpectedHeadset> EXPECTED_HEADSETS = List.of(
            headset("HEADSET_BASE", "headset_base", "HeadsetBaseItem"),
            headset("HEADSET_BRIGMEDIC", "headset_brigmedic", "HeadsetBrigmedicItem"),
            headset("HEADSET_CARGO", "headset_cargo", "HeadsetCargoItem"),
            headset("HEADSET_CENTCOM", "headset_centcom", "HeadsetCentcomItem"),
            headset("HEADSET_COMMAND", "headset_command", "HeadsetCommandItem"),
            headset("HEADSET_ENGINEERING", "headset_engineering", "HeadsetEngineeringItem"),
            headset("HEADSET_MEDICAL", "headset_medical", "HeadsetMedicalItem"),
            headset("HEADSET_MEDICAL_SCIENCE", "headset_medical_science",
                    "HeadsetMedicalScienceItem"),
            headset("HEADSET_MINING", "headset_mining", "HeadsetMiningItem"),
            headset("HEADSET_ROBOTICS", "headset_robotics", "HeadsetRoboticsItem"),
            headset("HEADSET_SCIENCE", "headset_science", "HeadsetScienceItem"),
            headset("HEADSET_SECURITY", "headset_security", "HeadsetSecurityItem"),
            headset("HEADSET_SERVICE", "headset_service", "HeadsetServiceItem"),
            headset("HEADSET_SYNDICATE", "headset_syndicate", "HeadsetSyndicateItem"));

    @Test
    void canonicalRegistryOwnsAllHeadsetIdsAndLegacyHoldersRemainAliases()
            throws Exception {
        assertEquals(14, EXPECTED_HEADSETS.size());
        for (ExpectedHeadset expected : EXPECTED_HEADSETS) {
            Field canonicalField = Ssc14Items.class.getField(expected.fieldName());
            Field legacyField = Ssc14ModItems.class.getField(expected.fieldName());
            Object canonicalHolder = canonicalField.get(null);
            assertSame(canonicalHolder, legacyField.get(null), expected.fieldName());
            assertEquals("ssc_14:" + expected.registryName(),
                    ((DeferredHolder<?, ?>) canonicalHolder).getId().toString(),
                    expected.fieldName());
        }
    }

    @Test
    void legacyHeadsetTypesDelegateToCanonicalImplementation() throws Exception {
        Class<?> canonicalType = Class.forName(
                "net.ssc14.content.item.communication.HeadsetItem");
        for (ExpectedHeadset expected : EXPECTED_HEADSETS) {
            Class<?> legacyType = Class.forName(
                    "net.mcreator.ssc.item." + expected.legacyClassName());
            assertTrue(canonicalType.isAssignableFrom(legacyType),
                    expected.legacyClassName());
        }
    }

    private static ExpectedHeadset headset(
            String fieldName, String registryName, String legacyClassName) {
        return new ExpectedHeadset(fieldName, registryName, legacyClassName);
    }

    private record ExpectedHeadset(
            String fieldName, String registryName, String legacyClassName) { }
}
