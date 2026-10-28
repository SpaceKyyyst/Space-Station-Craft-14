package net.mcreator.ssc.medical;

import org.junit.jupiter.api.Test;
import net.ssc14.common.medical.MedicalStatePolicy;
import static org.junit.jupiter.api.Assertions.*;

class MedicalStatePolicyTest {
    @Test
    void restrictsOnlyTheInclusiveCriticalWindow() {
        assertFalse(MedicalStatePolicy.isBuildRestricted(99));
        assertTrue(MedicalStatePolicy.isBuildRestricted(100));
        assertTrue(MedicalStatePolicy.isBuildRestricted(200));
        assertFalse(MedicalStatePolicy.isBuildRestricted(201));
        assertFalse(MedicalStatePolicy.isBuildRestricted(Double.NaN));
    }

    @Test
    void medicalPermissionNeverGrantsGameModePermissions() {
        for (double damage : new double[] {0, 99, 100, 200, 201}) {
            assertFalse(MedicalStatePolicy.canBuild(MedicalStatePolicy.evaluate(damage), false));
        }
        assertTrue(MedicalStatePolicy.canBuild(MedicalStatePolicy.evaluate(99), true));
        assertFalse(MedicalStatePolicy.canBuild(MedicalStatePolicy.evaluate(100), true));
        assertTrue(MedicalStatePolicy.canBuild(MedicalStatePolicy.evaluate(201), true));
    }

    @Test
    void cancelsBuildingOnlyOnTheServerAndReleasesAfterHealingOrReset() {
        assertTrue(MedicalStatePolicy.shouldCancelBuilding(false, 100));
        assertTrue(MedicalStatePolicy.shouldCancelBuilding(false, 200));
        assertFalse(MedicalStatePolicy.shouldCancelBuilding(true, 100));
        assertFalse(MedicalStatePolicy.shouldCancelBuilding(false, 99));
        assertFalse(MedicalStatePolicy.shouldCancelBuilding(false, 0));
        assertFalse(MedicalStatePolicy.shouldCancelBuilding(false, 201));
    }
}
