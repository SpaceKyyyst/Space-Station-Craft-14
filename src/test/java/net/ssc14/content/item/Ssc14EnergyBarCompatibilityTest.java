package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.item.EnergyBarItem;
import net.ssc14.content.item.food.PackagedEnergyBarItem;
import org.junit.jupiter.api.Test;

class Ssc14EnergyBarCompatibilityTest {
    @Test
    void canonicalRegistryOwnsEnergyBarIdAndPreservesCanonicalId() {
        assertEquals("ssc_14:energy_bar", Ssc14Items.ENERGY_BAR.getId().toString());
    }

    @Test
    void legacyEnergyBarTypeDelegatesToCanonicalImplementation() {
        assertTrue(PackagedEnergyBarItem.class.isAssignableFrom(EnergyBarItem.class));
        assertTrue(EnergyBarItem.class.isAnnotationPresent(Deprecated.class));
    }
}
