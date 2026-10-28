package net.ssc14.common.station;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class StationCodeServiceTest {
    @AfterEach
    void resetPorts() {
        StationCodeService.install(level -> 0);
        StationCodeService.installMutator((level, value) -> { });
    }

    @Test
    void advancesAndResetsThroughInstalledMutator() {
        AtomicInteger stored = new AtomicInteger(4);
        StationCodeService.install(level -> stored.get());
        StationCodeService.installMutator((level, value) -> stored.set(value));

        StationCodeService.advanceOrReset(null, false);
        assertEquals(5, stored.get());
        StationCodeService.advanceOrReset(null, true);
        assertEquals(0, stored.get());
    }
}
