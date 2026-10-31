package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.ssc14.content.item.tool.RsuItem;
import org.junit.jupiter.api.Test;

class Ssc14RsuItemCompatibilityTest {
    @Test
    void canonicalRegistryOwnsIdAndPreservesCanonicalId() {
        assertEquals("ssc_14:rsu", Ssc14Items.RSU.getId().toString());
    }

    @Test
    void legacyItemRemainsFacade() {
        assertTrue(RsuItem.class.isAssignableFrom(
                net.mcreator.ssc.item.RSUItem.class));
    }
}
