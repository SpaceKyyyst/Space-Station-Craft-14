package net.ssc14.common.atmosphere.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class GasTypeTest {
    @Test
    void preservesPersistedOrdinalAndNameContract() {
        assertArrayEquals(
                new String[] {
                    "oxygen",
                    "nitrogen",
                    "carbon_dioxide",
                    "nitrous_oxide",
                    "plasma",
                    "ammonia",
                    "tritium",
                    "freon",
                    "helium",
                    "water_vapor"
                },
                Arrays.stream(GasType.values()).map(GasType::getName).toArray(String[]::new));
        for (int index = 0; index < GasType.COUNT; index++) {
            GasType gas = GasType.values()[index];
            assertEquals(gas, GasType.byIndex(index));
            assertEquals(gas, GasType.byName(gas.getName()));
        }
    }

    @Test
    void rejectsUnknownPersistedKeysWithoutFallback() {
        assertNull(GasType.byIndex(-1));
        assertNull(GasType.byIndex(GasType.COUNT));
        assertNull(GasType.byName("unknown"));
        assertNull(GasType.byName(null));
    }

    @Test
    void exposesStablePhysicalAndReactionProperties() {
        assertEquals(0.032f, GasType.OXYGEN.getMolarMass());
        assertEquals(29.4f, GasType.OXYGEN.getHeatCapacity());
        assertEquals(0.004f, GasType.HELIUM.getMolarMass());
        assertEquals(20.8f, GasType.HELIUM.getHeatCapacity());
        assertEquals(true, GasType.PLASMA.isFlammable());
        assertEquals(true, GasType.TRITIUM.isFlammable());
        assertEquals(true, GasType.AMMONIA.isFlammable());
        assertEquals(false, GasType.OXYGEN.isFlammable());
        assertEquals(true, GasType.OXYGEN.isOxidizer());
        assertEquals(false, GasType.NITROGEN.isOxidizer());
    }
}
