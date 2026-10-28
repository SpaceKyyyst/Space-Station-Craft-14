package net.ssc14.client.menu.station;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GravityGeneratorDisplayStateTest {
    @Test
    void preservesBoundaryLabels() {
        var empty = new GravityGeneratorDisplayState(0, false, false);
        var full = new GravityGeneratorDisplayState(2000, true, true);

        assertEquals(GravityGeneratorDisplayState.Status.OFF, empty.status());
        assertEquals("N/D", empty.etaText());
        assertEquals("0 %", empty.percentText());
        assertFalse(empty.showsRod());

        assertEquals(GravityGeneratorDisplayState.Status.FULL, full.status());
        assertEquals("N/D", full.etaText());
        assertEquals("100 %", full.percentText());
        assertTrue(full.showsRod());
    }

    @Test
    void preservesIntermediateTimingAndStatus() {
        var active = new GravityGeneratorDisplayState(1000, true, false);
        var inactive = new GravityGeneratorDisplayState(1200, false, false);

        assertEquals(GravityGeneratorDisplayState.Status.ACTIVE, active.status());
        assertEquals("00:50", active.etaText());
        assertEquals("50 %", active.percentText());

        assertEquals(GravityGeneratorDisplayState.Status.INACTIVE, inactive.status());
        assertEquals("01:00", inactive.etaText());
        assertEquals("60 %", inactive.percentText());
    }
}
