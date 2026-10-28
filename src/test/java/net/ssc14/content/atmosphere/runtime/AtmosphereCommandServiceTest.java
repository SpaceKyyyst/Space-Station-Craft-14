package net.ssc14.content.atmosphere.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import net.ssc14.common.atmosphere.model.GasType;
import org.junit.jupiter.api.Test;

class AtmosphereCommandServiceTest {
    @Test
    void resolvesGasNamesThroughTargetService() {
        assertSame(GasType.OXYGEN, AtmosphereCommandService.gasByName("oxygen"));
    }

    @Test
    void preservesRegistryOrderWhenFormattingAvailableGasNames() {
        String expected = String.join(", ",
                java.util.Arrays.stream(GasType.values()).map(GasType::getName).toList());

        assertEquals(expected, AtmosphereCommandService.availableGasNames());
    }
}
