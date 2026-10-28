package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.item.MagneticBootsACTIVEItemItem;
import net.mcreator.ssc.item.MagneticBootsItemItem;
import net.ssc14.content.item.equipment.MagneticBootsItem;
import net.ssc14.content.item.equipment.MagneticBootsState;
import org.junit.jupiter.api.Test;

class Ssc14MagneticBootsCompatibilityTest {
    @Test
    void canonicalRegistryOwnsBothIdsAndLegacyHoldersRemainAliases() {
        assertSame(Ssc14Items.MAGNETIC_BOOTS_ITEM, Ssc14ModItems.MAGNETIC_BOOTS_ITEM);
        assertSame(Ssc14Items.MAGNETIC_BOOTS_ACTIVE_ITEM,
                Ssc14ModItems.MAGNETIC_BOOTS_ACTIVE_ITEM);
        assertEquals("ssc_14:magnetic_boots_item",
                Ssc14Items.MAGNETIC_BOOTS_ITEM.getId().toString());
        assertEquals("ssc_14:magnetic_boots_active_item",
                Ssc14Items.MAGNETIC_BOOTS_ACTIVE_ITEM.getId().toString());
    }

    @Test
    void canonicalStatesAndLegacyFacadesRemainExplicit() {
        assertEquals("translation.key.magboots_description_off",
                MagneticBootsState.INACTIVE.tooltipKey());
        assertEquals("translation.key.magboots_description_on",
                MagneticBootsState.ACTIVE.tooltipKey());
        assertTrue(MagneticBootsItem.class.isAssignableFrom(MagneticBootsItemItem.class));
        assertTrue(MagneticBootsItem.class.isAssignableFrom(MagneticBootsACTIVEItemItem.class));
    }
}
