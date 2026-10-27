package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.ssc14.content.item.weapon.FoamDartLauncherItem;
import org.junit.jupiter.api.Test;

class Ssc14AstroAceFoamPowerItemCompatibilityTest {
    @Test
    void canonicalRegistryOwnsIdAndLegacyHolderRemainsAlias() {
        assertSame(Ssc14Items.ASTRO_ACE_FOAM_POWER,
                Ssc14ModItems.ASTRO_ACE_FOAM_POWER);
        assertEquals("ssc_14:astro_ace_foam_power",
                Ssc14Items.ASTRO_ACE_FOAM_POWER.getId().toString());
    }

    @Test
    void legacyItemRemainsConstructibleFacade() {
        assertTrue(FoamDartLauncherItem.class.isAssignableFrom(
                net.mcreator.ssc.item.AstroAceFoamPowerItem.class));
        assertDoesNotThrow(() -> net.mcreator.ssc.item.AstroAceFoamPowerItem.class
                .getConstructor(net.minecraft.world.item.Item.Properties.class));
    }
}
