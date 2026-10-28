package net.ssc14.content.menu.commerce;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class VendingIndicatorStateTest {
    @Test
    void preservesSlotKeys() {
        assertEquals("slot_1", VendingIndicatorState.slotKey(1));
        assertEquals("slot_6", VendingIndicatorState.slotKey(6));
    }

    @Test
    void clampsIndicatorTextureOffset() {
        assertEquals(0, VendingIndicatorState.textureOffset(-1));
        assertEquals(0, VendingIndicatorState.textureOffset(0));
        assertEquals(5, VendingIndicatorState.textureOffset(1));
        assertEquals(95, VendingIndicatorState.textureOffset(19));
        assertEquals(95, VendingIndicatorState.textureOffset(20));
    }
}
