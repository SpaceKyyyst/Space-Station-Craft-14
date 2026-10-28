package net.mcreator.ssc.medical;

import org.junit.jupiter.api.Test;
import net.ssc14.common.medical.MedicalPresentationController;
import static org.junit.jupiter.api.Assertions.*;

class MedicalPresentationControllerTest {
    @Test
    void receivedEntryAndRecoveryApplyOnceDespiteRepeatedSnapshots() {
        var target = new Presentation();
        boolean active = MedicalPresentationController.apply(target, false, true);
        assertTrue(active);
        assertTrue(target.pose);
        active = MedicalPresentationController.apply(target, active, true);
        assertEquals(1, target.acquisitions);
        active = MedicalPresentationController.apply(target, active, false);
        assertFalse(active);
        assertFalse(target.pose);
        MedicalPresentationController.apply(target, active, false);
        assertEquals(1, target.releases);
    }

    @Test
    void inactiveInitializationDoesNotReleaseAnotherPresentationOwner() {
        var target = new Presentation();
        target.pose = true;
        assertFalse(MedicalPresentationController.apply(target, false, false));
        assertTrue(target.pose);
        assertEquals(0, target.releases);
    }

    private static final class Presentation implements MedicalPresentationController.Target {
        private boolean pose;
        private int acquisitions;
        private int releases;
        @Override public void acquire() { pose = true; acquisitions++; }
        @Override public void release() { pose = false; releases++; }
    }
}
