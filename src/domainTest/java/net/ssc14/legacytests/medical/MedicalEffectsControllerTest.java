package net.ssc14.legacytests.medical;

import org.junit.jupiter.api.Test;
import net.ssc14.common.medical.MedicalEffectsController;
import net.ssc14.common.medical.MedicalStatePolicy;
import static org.junit.jupiter.api.Assertions.*;

class MedicalEffectsControllerTest {
    @Test
    void rejectedCleanupKeepsOnlyBlindnessOwnershipUntilAConfirmedRetry() {
        Fixture target = new Fixture();
        var ownership = MedicalEffectsController.apply(target, MedicalEffectsController.Ownership.NONE, true, 220);
        target.rejectRemoval = true;
        ownership = MedicalEffectsController.apply(target, ownership, false, 220);
        assertTrue(ownership.blindness(), "A veto must not discard the only owner of SSC's hidden contribution");
        assertFalse(ownership.pose());
        assertFalse(target.forcedPose);
        assertTrue(target.blindness);
        target.rejectRemoval = false;
        ownership = MedicalEffectsController.apply(target, ownership, false, 220);
        assertEquals(MedicalEffectsController.Ownership.NONE, ownership);
        assertFalse(target.blindness);
    }

    @Test
    void acquiresAndReleasesOnlyOwnedEffects() {
        Fixture target = new Fixture();
        var ownership = MedicalEffectsController.apply(target, MedicalEffectsController.Ownership.NONE, true, 220);
        assertTrue(target.blindness);
        assertTrue(target.forcedPose);
        assertTrue(ownership.blindness());
        assertTrue(ownership.pose());
        ownership = MedicalEffectsController.apply(target, ownership, false, 220);
        assertFalse(target.blindness);
        assertFalse(target.forcedPose);
        assertEquals(MedicalEffectsController.Ownership.NONE, ownership);
    }

    @Test
    void unchangedTicksDoNotRepeatBlindnessOrPoseWrites() {
        Fixture target = new Fixture();
        var ownership = MedicalEffectsController.apply(target, MedicalEffectsController.Ownership.NONE, true, 220);
        for (int tick = 0; tick < 100; tick++) {
            ownership = MedicalEffectsController.apply(target, ownership, true, 220);
        }
        assertEquals(1, target.blindnessWrites);
        assertEquals(1, target.poseWrites);
    }

    @Test
    void externalBlindnessAndSwimmingArePreservedThroughRecovery() {
        Fixture target = new Fixture();
        target.blindness = true;
        target.poseAvailable = false;
        var ownership = MedicalEffectsController.apply(target, MedicalEffectsController.Ownership.NONE, true, 220);
        MedicalEffectsController.apply(target, ownership, false, 220);
        assertTrue(target.blindness);
        assertEquals(0, target.blindnessWrites);
        assertEquals(0, target.poseWrites);
    }

    @Test
    void externalPoseTakeoverIsNotOverwrittenOrReleased() {
        Fixture target = new Fixture();
        var ownership = MedicalEffectsController.apply(target, MedicalEffectsController.Ownership.NONE, true, 220);
        target.forcedPose = false;
        ownership = MedicalEffectsController.apply(target, ownership, true, 220);
        MedicalEffectsController.apply(target, ownership, false, 220);
        assertEquals(1, target.poseWrites);
    }

    @Test
    void expiredBlindnessCanBeRenewedButRejectedBlindnessIsNotOwned() {
        Fixture target = new Fixture();
        var ownership = MedicalEffectsController.apply(target, MedicalEffectsController.Ownership.NONE, true, 220);
        target.blindness = false;
        ownership = MedicalEffectsController.apply(target, ownership, true, 220);
        assertTrue(ownership.blindness());
        assertEquals(2, target.blindnessWrites);
        Fixture rejected = new Fixture();
        rejected.acceptBlindness = false;
        ownership = MedicalEffectsController.apply(rejected, MedicalEffectsController.Ownership.NONE, true, 220);
        assertFalse(ownership.blindness());
    }

    @Test
    void clientDoesNotChangeOwnedOrExternalState() {
        Fixture target = new Fixture();
        target.client = true;
        var ownership = MedicalEffectsController.apply(target, MedicalEffectsController.Ownership.NONE, true, 220);
        assertEquals(MedicalEffectsController.Ownership.NONE, ownership);
        assertEquals(0, target.blindnessWrites);
        assertEquals(0, target.poseWrites);
        assertEquals(0, target.modifierCalls);
    }

    @Test
    void healingResetAndCrossingAboveTheWindowReleaseOwnedStateIdempotently() {
        for (double recoveredDamage : new double[] {99, 0, 201}) {
            Fixture target = new Fixture();
            var ownership = MedicalEffectsController.apply(target, MedicalEffectsController.Ownership.NONE,
                    MedicalStatePolicy.isBuildRestricted(100), Integer.MAX_VALUE);
            assertEquals(Integer.MAX_VALUE, target.lastBlindnessTicks);
            ownership = MedicalEffectsController.apply(target, ownership,
                    MedicalStatePolicy.isBuildRestricted(recoveredDamage), 220);
            ownership = MedicalEffectsController.apply(target, ownership, false, 220);
            assertEquals(MedicalEffectsController.Ownership.NONE, ownership);
            assertEquals(2, target.blindnessWrites);
            assertEquals(2, target.poseWrites);
            assertFalse(target.criticalModifiers);
        }
    }

    // A boundary fixture: the production controller runs unchanged; world/packet operations are absent.
    private static final class Fixture implements MedicalEffectsController.Target {
        boolean client;
        boolean blindness;
        boolean acceptBlindness = true;
        boolean forcedPose;
        boolean poseAvailable = true;
        int blindnessWrites;
        int poseWrites;
        int modifierCalls;
        int lastBlindnessTicks;
        boolean criticalModifiers;
        boolean rejectRemoval;
        public boolean isClientSide() { return client; }
        public boolean hasBlindness() { return blindness; }
        public boolean addBlindness(int ticks) {
            blindnessWrites++;
            lastBlindnessTicks = ticks;
            blindness = acceptBlindness;
            return blindness;
        }
        public boolean removeBlindness() {
            blindnessWrites++;
            if (!rejectRemoval) blindness = false;
            return !blindness;
        }
        public boolean canAcquirePose() { return poseAvailable; }
        public boolean hasMedicalPose() { return forcedPose; }
        public void acquirePose() { poseWrites++; forcedPose = true; }
        public void releasePose() { poseWrites++; forcedPose = false; }
        public void reconcileModifiers(boolean critical) { modifierCalls++; criticalModifiers = critical; }
    }
}
