package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.ssc14.content.item.tool.RsuItem;
import org.junit.jupiter.api.Test;

class Ssc14RsuItemCompatibilityTest {
    @Test
    void canonicalRegistryOwnsIdAndLegacyHolderRemainsAlias() {
        assertSame(Ssc14Items.RSU, Ssc14ModItems.RSU);
        assertEquals("ssc_14:rsu", Ssc14Items.RSU.getId().toString());
    }

    @Test
    void legacyItemRemainsFacade() {
        assertTrue(RsuItem.class.isAssignableFrom(
                net.mcreator.ssc.item.RSUItem.class));
    }
}
