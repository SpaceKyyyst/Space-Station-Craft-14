package net.ssc14.common.gravity;

import org.junit.jupiter.api.Test;

import static net.ssc14.common.gravity.GravityPolicy.Decision.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GravityPolicyTest {
    @Test
    void releasesOutsideSpaceOrWhenExcluded() {
        assertEquals(RELEASE, decide(false, true, true, true, false));
        assertEquals(RELEASE, decide(true, true, true, true, true));
    }

    @Test
    void stationGravityUsesTheSevenBlockSupportResult() {
        assertEquals(GRAVITY, decide(true, true, false, true, false));
        assertEquals(NO_GRAVITY, decide(true, true, false, false, false));
    }

    @Test
    void magneticBootsUseSupportOnlyWhenStationGravityIsDisabled() {
        assertEquals(GRAVITY, decide(true, false, true, true, false));
        assertEquals(NO_GRAVITY, decide(true, false, true, false, false));
        assertEquals(NO_GRAVITY, decide(true, false, false, true, false));
        assertEquals(NO_GRAVITY, decide(true, false, false, false, false));
    }

    private static GravityPolicy.Decision decide(boolean inSpace, boolean stationGravity, boolean magneticBoots,
                                                  boolean supportingBlock, boolean excluded) {
        return GravityPolicy.evaluate(new GravityPolicy.EntitySnapshot(inSpace, stationGravity, magneticBoots, supportingBlock, excluded));
    }
}
