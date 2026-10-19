package net.mcreator.ssc.combat;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CriticalAbilityPolicyTest {
    @Test
    void syncsOnlyWhenBuildPermissionChanges() {
        assertTrue(CriticalAbilityPolicy.shouldSync(false, true));
        assertTrue(CriticalAbilityPolicy.shouldSync(true, false));
        assertFalse(CriticalAbilityPolicy.shouldSync(false, false));
        assertFalse(CriticalAbilityPolicy.shouldSync(true, true));
    }
}
