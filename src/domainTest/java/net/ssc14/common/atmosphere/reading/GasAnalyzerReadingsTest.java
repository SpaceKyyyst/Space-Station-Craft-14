package net.ssc14.common.atmosphere.reading;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GasAnalyzerReadingsTest {
    @Test
    void acceptsVacuumAndNormalAtmosphere() {
        assertEquals(0, new GasAnalyzerReadings(0, 293, 0, 0, 0).totalMoles());
        assertEquals(20, new GasAnalyzerReadings(101325, 293, 100, 20, 0).oxygenMoles());
    }

    @Test
    void rejectsImpossibleOrNonfiniteReadings() {
        assertThrows(IllegalArgumentException.class, () -> new GasAnalyzerReadings(Float.NaN, 293, 1, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new GasAnalyzerReadings(1, 0, 1, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new GasAnalyzerReadings(1, 293, 1, 2, 0));
        assertThrows(IllegalArgumentException.class, () -> new GasAnalyzerReadings(1, 293, Float.POSITIVE_INFINITY, 1, 0));
    }
}
