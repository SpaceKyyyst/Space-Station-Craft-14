package net.ssc14.content.block.wall;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WallDamageServiceTest {
    @Test
    void preservesLegacyC4TimingAndSearchRadii() {
        assertEquals(1, WallDamageService.EXPLOSION_DELAY_TICKS);
        assertEquals(1.25, WallDamageService.STEEL_DIRECT_RADIUS);
        assertEquals(1.0, WallDamageService.PLASTEEL_DIRECT_RADIUS);
        assertEquals(5.0, WallDamageService.DEGRADE_RADIUS);
    }
}
