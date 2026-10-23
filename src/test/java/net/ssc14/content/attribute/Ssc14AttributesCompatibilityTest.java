package net.ssc14.content.attribute;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class Ssc14AttributesCompatibilityTest {
    private static final Set<String> EXPECTED_PATHS = Set.of(
            "progress_bar_atrb",
            "xy_zplayer",
            "ent_pull",
            "gas_an_gu_iatrib",
            "open_world_check_menu",
            "nutrients",
            "digestive_processes",
            "health_u_iattribute",
            "liquid",
            "fluid_absorption",
            "race");

    @Test
    void legacyRegistryExposesExactAttributeFieldsAndIds() {
        Set<Field> holders = publicHolders(Ssc14ModAttributes.class);

        assertEquals(EXPECTED_PATHS.stream()
                .map(path -> path.toUpperCase(Locale.ROOT))
                .collect(Collectors.toUnmodifiableSet()), holderNames(holders));
        assertEquals(EXPECTED_PATHS, holderPaths(holders));
    }

    @Test
    void legacyRegistryKeepsItsPublicNoArgumentConstructor() throws ReflectiveOperationException {
        Ssc14ModAttributes.class.getConstructor();
    }

    @Test
    void targetRegistryOwnsExactAttributesAndLegacyFacadeAliasesThem() throws ReflectiveOperationException {
        Set<Field> targetHolders = publicHolders(Ssc14Attributes.class);
        assertEquals(EXPECTED_PATHS, holderPaths(targetHolders));
        assertSame(Ssc14Attributes.REGISTRY, Ssc14ModAttributes.REGISTRY);

        for (Field targetField : targetHolders) {
            assertSame(targetField.get(null), Ssc14ModAttributes.class.getField(targetField.getName()).get(null),
                    targetField.getName());
        }
    }

    private static Set<Field> publicHolders(Class<?> owner) {
        return Arrays.stream(owner.getFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .filter(field -> DeferredHolder.class.isAssignableFrom(field.getType()))
                .collect(Collectors.toUnmodifiableSet());
    }

    private static Set<String> holderNames(Set<Field> holders) {
        return holders.stream().map(Field::getName).collect(Collectors.toUnmodifiableSet());
    }

    private static Set<String> holderPaths(Set<Field> holders) {
        return holders.stream().map(field -> {
            try {
                return ((DeferredHolder<?, ?>) field.get(null)).getId().getPath();
            } catch (IllegalAccessException exception) {
                throw new IllegalStateException(exception);
            }
        }).collect(Collectors.toUnmodifiableSet());
    }
}
