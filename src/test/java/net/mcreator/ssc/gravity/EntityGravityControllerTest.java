package net.mcreator.ssc.gravity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Marker;
import org.junit.jupiter.api.Test;

import static net.mcreator.ssc.gravity.GravityPolicy.Decision.*;
import static org.junit.jupiter.api.Assertions.*;

class EntityGravityControllerTest {
    @Test
    void preservesExternalTrueBaselineThroughStationControlAndRelease() {
        CountingMarker entity = new CountingMarker();
        entity.setNoGravity(true);
        int initialWrites = entity.writes;
        EntityGravityController.apply(entity, GRAVITY);
        EntityGravityController.apply(entity, NO_GRAVITY);
        EntityGravityController.apply(entity, RELEASE);
        assertTrue(entity.isNoGravity());
        assertEquals(initialWrites, entity.writes);
        assertTrue(entity.getPersistentData().isEmpty());
    }

    @Test
    void restoresExternalFalseBaselineAfterSpaceEntryAndExit() {
        CountingMarker entity = new CountingMarker();
        EntityGravityController.apply(entity, NO_GRAVITY);
        assertTrue(entity.isNoGravity());
        EntityGravityController.apply(entity, RELEASE);
        assertFalse(entity.isNoGravity());
        assertEquals(2, entity.writes);
        assertTrue(entity.getPersistentData().isEmpty());
    }

    @Test
    void stationAndBootsTransitionsReuseTheOriginalBaseline() {
        CountingMarker entity = new CountingMarker();
        EntityGravityController.apply(entity, GRAVITY);
        assertEquals(0, entity.writes);
        EntityGravityController.apply(entity, NO_GRAVITY);
        EntityGravityController.apply(entity, GRAVITY);
        EntityGravityController.apply(entity, NO_GRAVITY);
        EntityGravityController.apply(entity, RELEASE);
        assertFalse(entity.isNoGravity());
        assertEquals(4, entity.writes);
    }

    @Test
    void externalChangeWhileOwnedPreventsFurtherWritesUntilRelease() {
        CountingMarker entity = new CountingMarker();
        EntityGravityController.apply(entity, NO_GRAVITY);
        entity.setNoGravity(false);
        int takeoverWrites = entity.writes;
        EntityGravityController.apply(entity, NO_GRAVITY);
        EntityGravityController.apply(entity, GRAVITY);
        EntityGravityController.apply(entity, NO_GRAVITY);
        assertFalse(entity.isNoGravity());
        assertEquals(takeoverWrites, entity.writes);
        EntityGravityController.apply(entity, RELEASE);
        assertFalse(entity.isNoGravity());
        assertEquals(takeoverWrites, entity.writes);
        assertTrue(entity.getPersistentData().isEmpty());
        EntityGravityController.apply(entity, NO_GRAVITY);
        assertTrue(entity.isNoGravity(), "A new control episode may acquire ownership again");
    }

    @Test
    void externalChangeAfterStationTransitionIsNotRestoredToTheCapturedBaseline() {
        CountingMarker entity = new CountingMarker();
        EntityGravityController.apply(entity, NO_GRAVITY);
        EntityGravityController.apply(entity, GRAVITY);
        entity.setNoGravity(true);
        int takeoverWrites = entity.writes;
        EntityGravityController.apply(entity, RELEASE);
        assertTrue(entity.isNoGravity());
        assertEquals(takeoverWrites, entity.writes);
        assertTrue(entity.getPersistentData().isEmpty());
    }

    @Test
    void unchangedDecisionsAvoidSetterWritesAndPersistentMutations() {
        CountingMarker entity = new CountingMarker();
        EntityGravityController.apply(entity, RELEASE);
        assertEquals(0, entity.writes);
        assertTrue(entity.getPersistentData().isEmpty());
        EntityGravityController.apply(entity, NO_GRAVITY);
        var ownership = entity.getPersistentData().copy();
        for (int tick = 0; tick < 100; tick++) EntityGravityController.apply(entity, NO_GRAVITY);
        assertEquals(1, entity.writes);
        assertEquals(ownership, entity.getPersistentData());
    }

    @Test
    void takeoverBeforeTheFirstOwnedWriteIsPreserved() {
        CountingMarker entity = new CountingMarker();
        EntityGravityController.apply(entity, GRAVITY);
        entity.setNoGravity(true);
        EntityGravityController.apply(entity, GRAVITY);
        EntityGravityController.apply(entity, RELEASE);
        assertTrue(entity.isNoGravity());
        assertEquals(1, entity.writes);
    }

    @Test
    void removedEntityReleasesOwnershipEvenIfAnActiveDecisionArrives() {
        CountingMarker entity = new CountingMarker();
        entity.getPersistentData().putString("other_mod", "keep");
        EntityGravityController.apply(entity, NO_GRAVITY);
        entity.setRemoved(net.minecraft.world.entity.Entity.RemovalReason.UNLOADED_TO_CHUNK);
        EntityGravityController.apply(entity, NO_GRAVITY);
        assertFalse(entity.isNoGravity());
        assertEquals(2, entity.writes);
        assertEquals(1, entity.getPersistentData().size());
        assertEquals("keep", entity.getPersistentData().getStringOr("other_mod", ""));
    }

    // Real vanilla synched entity data; the controller needs no world operations.
    private static final class CountingMarker extends Marker {
        private int writes;

        private CountingMarker() {
            super(EntityType.MARKER, null);
        }

        @Override
        public void setNoGravity(boolean value) {
            writes++;
            super.setNoGravity(value);
        }
    }
}
