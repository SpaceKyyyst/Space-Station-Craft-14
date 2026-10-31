package net.ssc14.legacytests.combat;

import org.junit.jupiter.api.Test;
import net.ssc14.core.combat.ResistanceRegistry;

import static org.junit.jupiter.api.Assertions.*;

class ResistanceRegistryTest {
    private final ResistanceRegistry registry = new ResistanceRegistry();

    @Test
    void unknownItemsAndDamageTypesDoNotReduceDamage() {
        registry.register("ssc_14:helmet", "heat", 0.5);
        assertEquals(100f, registry.apply("ssc_14:helmet", "slash", 100f));
        assertEquals(100f, registry.apply("ssc_14:unknown", "heat", 100f));
    }

    @Test
    void equippedItemsStackMultiplicatively() {
        registry.register("ssc_14:helmet", "heat", 0.5);
        registry.register("ssc_14:suit", "heat", 0.25);
        float afterHelmet = registry.apply("ssc_14:helmet", "heat", 100f);
        assertEquals(37.5f, registry.apply("ssc_14:suit", "heat", afterHelmet));
    }

    @Test
    void clampsFiniteResistanceToUnitInterval() {
        registry.register("ssc_14:helmet", "heat", -1);
        assertEquals(100f, registry.apply("ssc_14:helmet", "heat", 100f));
        registry.register("ssc_14:helmet", "heat", 2);
        assertEquals(0f, registry.apply("ssc_14:helmet", "heat", 100f));
    }

    @Test
    void rejectsNonFiniteRegistrationWithoutReplacingValidValue() {
        registry.register("ssc_14:helmet", "heat", 0.5);
        for (double invalid : new double[]{Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY}) {
            assertThrows(IllegalArgumentException.class,
                    () -> registry.register("ssc_14:helmet", "heat", invalid));
        }
        assertEquals(50f, registry.apply("ssc_14:helmet", "heat", 100f));
    }

    @Test
    void structuredKeysCannotCollideAtSeparator() {
        registry.register("mod:item:variant", "heat", 0.5);
        registry.register("mod:item", "variant:heat", 0.25);
        assertEquals(50f, registry.apply("mod:item:variant", "heat", 100f));
        assertEquals(75f, registry.apply("mod:item", "variant:heat", 100f));
    }

    @Test
    void registrationReplacesOnlyMatchingRule() {
        registry.register("ssc_14:helmet", "heat", 0.5);
        registry.register("ssc_14:helmet", "heat", 0.25);
        assertEquals(75f, registry.apply("ssc_14:helmet", "heat", 100f));
        assertEquals(0f, registry.apply("ssc_14:helmet", "heat", -10f));
    }
}
