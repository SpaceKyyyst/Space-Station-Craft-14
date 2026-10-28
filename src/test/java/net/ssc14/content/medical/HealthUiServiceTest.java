package net.ssc14.content.medical;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HealthUiServiceTest {
    @Test
    void animationFramePreservesLegacyCycle() {
        assertEquals(0.0, HealthUiService.animationFrame(0));
        assertEquals(Math.round((20 * 2.8 % 113.0) / 4.0),
                HealthUiService.animationFrame(20));
        assertEquals(Math.round((113 * 2.8 % 113.0) / 4.0),
                HealthUiService.animationFrame(113));
    }
}
