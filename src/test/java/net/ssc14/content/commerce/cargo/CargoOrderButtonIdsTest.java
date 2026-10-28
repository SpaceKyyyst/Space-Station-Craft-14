package net.ssc14.content.commerce.cargo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CargoOrderButtonIdsTest {
    @Test
    void preservesTabAndItemButtonRanges() {
        assertEquals(1, CargoOrderButtonIds.tabForButton(0));
        assertEquals(15, CargoOrderButtonIds.tabForButton(14));
        assertEquals(-1, CargoOrderButtonIds.tabForButton(-1));
        assertEquals(-1, CargoOrderButtonIds.tabForButton(15));

        assertEquals(0, CargoOrderButtonIds.itemIndexForButton(100));
        assertEquals(9, CargoOrderButtonIds.itemIndexForButton(109));
        assertEquals(-1, CargoOrderButtonIds.itemIndexForButton(99));
        assertEquals(-1, CargoOrderButtonIds.itemIndexForButton(110));
    }
}
