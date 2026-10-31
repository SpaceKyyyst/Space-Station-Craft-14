package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.ssc14.content.item.tool.MultitoolItem;
import org.junit.jupiter.api.Test;

class Ssc14MultitoolItemCompatibilityTest {
    @Test
    void canonicalRegistryOwnsId() {
        assertEquals("ssc_14:multitul", Ssc14Items.MULTITOOL.getId().toString());
    }

    @Test
    void legacyItemRemainsFacade() {
        assertTrue(MultitoolItem.class.isAssignableFrom(
                net.mcreator.ssc.item.MultitulItem.class));
    }
}
