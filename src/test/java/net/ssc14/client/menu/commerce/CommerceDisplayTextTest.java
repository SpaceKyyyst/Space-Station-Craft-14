package net.ssc14.client.menu.commerce;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CommerceDisplayTextTest {
    @Test
    void preservesRoundedCurrencyFormat() {
        assertEquals("$:  0", CommerceDisplayText.currency(0));
        assertEquals("$:  13", CommerceDisplayText.currency(12.6));
        assertEquals("$:  -2", CommerceDisplayText.currency(-1.6));
    }
}
