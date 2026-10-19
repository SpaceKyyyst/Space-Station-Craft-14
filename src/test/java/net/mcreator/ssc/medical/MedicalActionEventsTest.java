package net.mcreator.ssc.medical;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MedicalActionEventsTest {
    @Test
    void criticalServerBreakCancelsAndRequestsClientCorrection() {
        BreakBlockEvent event = breakEvent();
        MedicalActionEvents.restrictBuild(event, false, 100);
        assertTrue(event.isCanceled());
        assertTrue(event.shouldNotifyClient());
    }

    @Test
    void breakDoesNotMutateOnTheClientOrOutsideCriticalWindow() {
        for (double damage : new double[] {0, 99, 201}) {
            BreakBlockEvent event = breakEvent();
            MedicalActionEvents.restrictBuild(event, false, damage);
            assertFalse(event.isCanceled());
            assertFalse(event.shouldNotifyClient());
        }
        BreakBlockEvent event = breakEvent();
        MedicalActionEvents.restrictBuild(event, true, 200);
        assertFalse(event.isCanceled());
        assertFalse(event.shouldNotifyClient());
    }

    @Test
    void genericPlaceCancellationUsesTheSameWindowAndPreservesOtherCancellations() {
        PlaceCancellation event = new PlaceCancellation();
        MedicalActionEvents.restrictBuild(event, false, 200);
        assertTrue(event.isCanceled());
        MedicalActionEvents.restrictBuild(event, false, 201);
        assertTrue(event.isCanceled());
        PlaceCancellation client = new PlaceCancellation();
        MedicalActionEvents.restrictBuild(client, true, 100);
        assertFalse(client.isCanceled());
    }

    private static BreakBlockEvent breakEvent() {
        // The cancellation boundary does not read the world or entity; keep both absent.
        return new BreakBlockEvent(null, BlockPos.ZERO, Blocks.STONE.defaultBlockState(), null);
    }

    // Covers the common cancellation contract; real placement snapshots require a live level.
    private static final class PlaceCancellation extends Event implements ICancellableEvent { }
}
