package net.ssc14.legacytests.atmosphere;

import net.ssc14.core.atmosphere.RegionWorkQueue;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegionWorkQueueTest {
    @Test
    void thousandsOfUnloadedSectionsDoNotDelayTheActiveRoom() {
        RegionWorkQueue queue = new RegionWorkQueue();
        for (long region = 0; region < 12800; region++) queue.schedule(region, region, false);
        queue.schedule(20000, 20000, true);
        assertEquals(List.of(20000L), queue.takeReady(128));
        queue.resumeChunk(7);
        assertEquals(List.of(7L), queue.takeReady(128));
    }

    @Test
    void reactivatedWorkWaitsBehindPreviouslyDeferredReadyWork() {
        RegionWorkQueue queue = new RegionWorkQueue();
        queue.schedule(1, 0, true);
        queue.schedule(2, 0, true);
        assertEquals(List.of(1L), queue.takeReady(1));
        queue.schedule(1, 0, true);
        assertEquals(List.of(2L, 1L), queue.takeReady(2));
        assertTrue(queue.takeReady(2).isEmpty());
    }

    @Test
    void unloadPreservesWorkAndReloadOnlyWakesItsOwnChunk() {
        RegionWorkQueue queue = new RegionWorkQueue();
        queue.schedule(1, 10, true);
        queue.schedule(2, 10, true);
        queue.schedule(3, 20, true);
        queue.suspendChunk(10);
        assertEquals(List.of(3L), queue.takeReady(128));
        queue.resumeChunk(20);
        assertTrue(queue.takeReady(128).isEmpty());
        queue.resumeChunk(10);
        assertEquals(List.of(1L, 2L), queue.takeReady(128));
    }

    @Test
    void duplicateNotificationsDoNotRepeatASectionWithinTheStep() {
        RegionWorkQueue queue = new RegionWorkQueue();
        queue.schedule(1, 10, false);
        queue.schedule(1, 10, false);
        queue.resumeChunk(10);
        queue.resumeChunk(10);
        assertTrue(queue.takeReady(0).isEmpty());
        assertEquals(List.of(1L), queue.takeReady(128));
        queue.resumeChunk(10);
        assertTrue(queue.takeReady(128).isEmpty());
    }
}
