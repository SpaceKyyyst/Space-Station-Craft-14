package net.ssc14.core.network;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MenuAccessPolicyTest {
    @Test
    void requiresLiveNonSpectatorWithValidMenu() {
        assertTrue(MenuAccessPolicy.canUse(true, false, true, true));
        assertFalse(MenuAccessPolicy.canUse(false, false, true, true));
        assertFalse(MenuAccessPolicy.canUse(true, true, true, true));
        assertFalse(MenuAccessPolicy.canUse(true, false, false, true));
        assertFalse(MenuAccessPolicy.canUse(true, false, true, false));
    }

    @Test
    void requiresExactMenuLoadedChunkPermissionAndDistanceBoundary() {
        assertTrue(MenuAccessPolicy.matches(true, true, true, true,
                MenuAccessPolicy.MAX_DISTANCE_SQUARED, true));
        assertFalse(MenuAccessPolicy.matches(false, true, true, true, 0.0, true));
        assertFalse(MenuAccessPolicy.matches(true, false, true, true, 0.0, true));
        assertFalse(MenuAccessPolicy.matches(true, true, false, true, 0.0, true));
        assertFalse(MenuAccessPolicy.matches(true, true, true, false, 0.0, true));
        assertFalse(MenuAccessPolicy.matches(true, true, true, true,
                Math.nextUp(MenuAccessPolicy.MAX_DISTANCE_SQUARED), true));
        assertFalse(MenuAccessPolicy.matches(true, true, true, true, 0.0, false));
    }
}
