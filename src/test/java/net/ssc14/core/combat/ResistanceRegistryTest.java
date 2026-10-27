package net.ssc14.core.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ResistanceRegistryTest {
    @Test
    void clampsRulesAndAppliesThemToRemainingDamage() {
        var registry = new ResistanceRegistry();
        registry.register("ssc_14:helmet", "blunt", 1.5);
        registry.register("ssc_14:suit", "blunt", 0.25);

        assertEquals(0.0F, registry.apply("ssc_14:helmet", "blunt", 10.0F));
        assertEquals(7.5F, registry.apply("ssc_14:suit", "blunt", 10.0F));
        assertEquals(10.0F, registry.apply("ssc_14:suit", "heat", 10.0F));
    }

    @Test
    void rejectsNonFiniteResistance() {
        var registry = new ResistanceRegistry();
        assertThrows(IllegalArgumentException.class,
                () -> registry.register("ssc_14:suit", "blunt", Double.NaN));
    }
}
