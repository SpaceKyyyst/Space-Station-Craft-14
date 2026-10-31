package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.item.ExtinguisherOpenItem;
import net.ssc14.content.item.safety.ExtinguisherItem;
import net.ssc14.content.item.safety.ExtinguisherState;
import org.junit.jupiter.api.Test;

class Ssc14ExtinguisherItemsCompatibilityTest {
    @Test
    void canonicalRegistryOwnsBothIdsAndPreservesCanonicalIds() {
        assertEquals("ssc_14:extinguisher", Ssc14Items.EXTINGUISHER.getId().toString());
        assertEquals("ssc_14:extinguisher_open",
                Ssc14Items.EXTINGUISHER_OPEN.getId().toString());
    }

    @Test
    void canonicalStatesAndLegacyFacadesRemainExplicit() {
        assertTrue(ExtinguisherItem.class.isAssignableFrom(
                net.mcreator.ssc.item.ExtinguisherItem.class));
        assertTrue(ExtinguisherItem.class.isAssignableFrom(ExtinguisherOpenItem.class));
        assertEquals(2, ExtinguisherState.values().length);
        assertDoesNotThrow(() -> net.mcreator.ssc.item.ExtinguisherItem.class
                .getConstructor(net.minecraft.world.item.Item.Properties.class));
        assertDoesNotThrow(() -> ExtinguisherOpenItem.class
                .getConstructor(net.minecraft.world.item.Item.Properties.class));
    }
}
