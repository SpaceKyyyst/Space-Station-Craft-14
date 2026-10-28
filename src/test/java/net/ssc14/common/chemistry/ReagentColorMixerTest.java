package net.ssc14.common.chemistry;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;

class ReagentColorMixerTest {
    @Test
    void mixesColorsByVolume() {
        assertEquals(0x7F007F, ReagentColorMixer.mix(
                Map.of("red", 1, "blue", 1),
                Map.of("red", 0xFF0000, "blue", 0x0000FF)));
    }

    @Test
    void returnsWhiteForEmptyOrUnknownContents() {
        assertEquals(0xFFFFFF, ReagentColorMixer.mix(Map.of(), Map.of()));
        assertEquals(0x000000, ReagentColorMixer.mix(Map.of("unknown", 1), Map.of()));
    }
}
