package net.ssc14.content.commerce;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class VendingButtonIdsTest {
    @Test
    void preservesSixButtonSlotMapping() {
        assertEquals(1, VendingButtonIds.slotForButton(0));
        assertEquals(6, VendingButtonIds.slotForButton(5));
        assertEquals(-1, VendingButtonIds.slotForButton(-1));
        assertEquals(-1, VendingButtonIds.slotForButton(6));
    }
}
