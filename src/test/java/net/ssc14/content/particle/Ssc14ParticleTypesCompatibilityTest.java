package net.ssc14.content.particle;

import net.mcreator.ssc.init.Ssc14ModParticleTypes;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class Ssc14ParticleTypesCompatibilityTest {
    private static final Set<String> EXPECTED_HOLDERS = Set.of(
            "AMMONIA_PARTICLES_1",
            "AMMONIA_PARTICLES_2",
            "AMMONIA_PARTICLES_3",
            "AMMONIA_PARTICLES_4",
            "BULLET_FLIGHT_P",
            "DECONSTRUCT_P",
            "DECONSTRUCT_PX",
            "DECONSTRUCT_PZ",
            "EXTINGUISHER_SPRAY",
            "FREON_PARTICLES_1",
            "FREON_PARTICLES_2",
            "FREON_PARTICLES_3",
            "FREON_PARTICLES_4",
            "GRAV_GEN_ACTIVE_PARTICLE",
            "GRAV_GEN_PARTICLE_2",
            "GRAV_GEN_PARTICLE_3",
            "GRAV_GEN_PARTICLE_4",
            "HELIUM_PARTICLES_1",
            "HELIUM_PARTICLES_2",
            "HELIUM_PARTICLES_3",
            "HELIUM_PARTICLES_4",
            "PLASMA_PARTICLES_1",
            "PLASMA_PARTICLES_2",
            "RCD_INTERFERENCE_P",
            "SPARK",
            "SPECIFY_P",
            "TRITIUM_PARTICLES_1",
            "TRITIUM_PARTICLES_2",
            "WATER_VAPOR_PARTICLES_1",
            "WATER_VAPOR_PARTICLES_2",
            "WATER_VAPOR_PARTICLES_3",
            "WATER_VAPOR_PARTICLES_4");

    @Test
    void targetRegistryOwnsExactParticleFields() {
        assertEquals(EXPECTED_HOLDERS, publicHolderNames(Ssc14ParticleTypes.class));
    }

    @Test
    void legacyFacadeSharesRegistryAndEveryHolder() throws ReflectiveOperationException {
        assertSame(Ssc14ParticleTypes.REGISTRY, Ssc14ModParticleTypes.REGISTRY);
        for (String fieldName : EXPECTED_HOLDERS) {
            Field targetField = Ssc14ParticleTypes.class.getField(fieldName);
            Field legacyField = Ssc14ModParticleTypes.class.getField(fieldName);
            assertSame(targetField.get(null), legacyField.get(null), fieldName);
        }
    }

    @Test
    void legacyFacadeKeepsItsPublicNoArgumentConstructor() throws ReflectiveOperationException {
        Ssc14ModParticleTypes.class.getConstructor();
    }

    private static Set<String> publicHolderNames(Class<?> owner) {
        return Arrays.stream(owner.getFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .filter(field -> DeferredHolder.class.isAssignableFrom(field.getType()))
                .map(Field::getName)
                .collect(Collectors.toUnmodifiableSet());
    }
}
