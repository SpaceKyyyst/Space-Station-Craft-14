package net.mcreator.ssc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GasTypeTest {
    @Test
    void persistedGasIndicesRemainStable() {
        String[] names = {"oxygen", "nitrogen", "carbon_dioxide", "nitrous_oxide",
                "plasma", "ammonia", "tritium", "freon", "helium", "water_vapor"};
        assertEquals(names.length, GasType.COUNT);
        for (int i = 0; i < names.length; i++) {
            assertEquals(names[i], GasType.byIndex(i).getName());
            assertSame(GasType.byIndex(i), GasType.byName(names[i]));
        }
        assertNull(GasType.byIndex(-1));
        assertNull(GasType.byIndex(GasType.COUNT));
        assertNull(GasType.byName("unknown"));
    }
}
