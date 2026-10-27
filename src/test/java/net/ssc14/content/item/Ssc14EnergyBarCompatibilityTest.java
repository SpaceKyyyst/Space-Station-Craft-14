package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.item.EnergyBarItem;
import net.ssc14.content.item.food.PackagedEnergyBarItem;
import org.junit.jupiter.api.Test;

class Ssc14EnergyBarCompatibilityTest {
    @Test
    void canonicalRegistryOwnsEnergyBarIdAndLegacyHolderRemainsAlias() {
        assertSame(Ssc14Items.ENERGY_BAR, Ssc14ModItems.ENERGY_BAR);
        assertEquals("ssc_14:energy_bar", Ssc14Items.ENERGY_BAR.getId().toString());
    }

    @Test
    void legacyEnergyBarTypeDelegatesToCanonicalImplementation() {
        assertTrue(PackagedEnergyBarItem.class.isAssignableFrom(EnergyBarItem.class));
        assertTrue(EnergyBarItem.class.isAnnotationPresent(Deprecated.class));
    }
}
