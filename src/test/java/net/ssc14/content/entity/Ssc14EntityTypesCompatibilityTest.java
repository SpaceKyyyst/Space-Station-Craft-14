package net.ssc14.content.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Ssc14EntityTypesCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.ofEntries(
            Map.entry("WALL_CARCASE_ENTIT", "wall_carcase_entit"),
            Map.entry("ID_CONSOLE_ENTITY", "id_console_entity"),
            Map.entry("PLASSTEEL_WALL_CARCASE_ENTIT", "plassteel_wall_carcase_entit"),
            Map.entry("CHAIR_ENTITY", "chair_entity"),
            Map.entry("C_4_CRUTCH_ENT", "c_4_crutch_ent"),
            Map.entry("MOUSE_BROWN", "mouse_brown"),
            Map.entry("MOUSE_GRAY", "mouse_gray"),
            Map.entry("MOUSE_WHITE", "mouse_white"),
            Map.entry("BULLET_35", "bullet_35"),
            Map.entry("FOAM_DART_PROJECTILE", "foam_dart_projectile"),
            Map.entry("POSTER", "poster"),
            Map.entry("CARP", "carp"));

    private static final Map<Class<?>, Class<?>> LEGACY_TYPES = Map.ofEntries(
            Map.entry(net.mcreator.ssc.entity.WallCarcaseEntitEntity.class, WallCarcaseEntitEntity.class),
            Map.entry(net.mcreator.ssc.entity.IDConsoleENTITYEntity.class, IDConsoleENTITYEntity.class),
            Map.entry(net.mcreator.ssc.entity.PlassteelWallCarcaseEntitEntity.class, PlassteelWallCarcaseEntitEntity.class),
            Map.entry(net.mcreator.ssc.entity.ChairEntityEntity.class, ChairEntityEntity.class),
            Map.entry(net.mcreator.ssc.entity.C4CrutchEntEntity.class, C4CrutchEntEntity.class),
            Map.entry(net.mcreator.ssc.entity.MouseBrownEntity.class, MouseBrownEntity.class),
            Map.entry(net.mcreator.ssc.entity.MouseGrayEntity.class, MouseGrayEntity.class),
            Map.entry(net.mcreator.ssc.entity.MouseWhiteEntity.class, MouseWhiteEntity.class),
            Map.entry(net.mcreator.ssc.entity.Bullet35Entity.class, Bullet35Entity.class),
            Map.entry(net.mcreator.ssc.entity.FoamDartProjectileEntity.class, FoamDartProjectileEntity.class),
            Map.entry(net.mcreator.ssc.entity.PosterEntity.class, PosterEntity.class),
            Map.entry(net.mcreator.ssc.entity.CarpEntity.class, CarpEntity.class));

    @Test
    void canonicalRegistryOwnsExactIds() throws ReflectiveOperationException {
        Set<Field> targetHolders = publicHolders(Ssc14EntityTypes.class);

        assertEquals(EXPECTED_HOLDERS.keySet(), names(targetHolders));
        assertEquals(EXPECTED_HOLDERS.values().stream()
                .map(path -> "ssc_14:" + path)
                .collect(Collectors.toUnmodifiableSet()), ids(targetHolders));
    }

    @Test
    void legacyEntityClassesRemainConstructibleCompatibilitySubtypes() throws ReflectiveOperationException {
        for (Map.Entry<Class<?>, Class<?>> pair : LEGACY_TYPES.entrySet()) {
            Class<?> legacyType = pair.getKey();
            Class<?> targetType = pair.getValue();
            assertTrue(targetType.isAssignableFrom(legacyType), legacyType.getName());
            legacyType.getConstructor(EntityType.class, Level.class);
        }
    }

    @Test
    void legacyProjectileClassesKeepDeclaredConstructorsFieldsAndShootDescriptors()
            throws ReflectiveOperationException {
        assertLegacyProjectileApi(net.mcreator.ssc.entity.Bullet35Entity.class);
        assertLegacyProjectileApi(net.mcreator.ssc.entity.FoamDartProjectileEntity.class);
    }

    private static Set<Field> publicHolders(Class<?> owner) {
        return Arrays.stream(owner.getFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .filter(field -> DeferredHolder.class.isAssignableFrom(field.getType()))
                .collect(Collectors.toUnmodifiableSet());
    }

    private static void assertLegacyProjectileApi(Class<?> projectileType)
            throws ReflectiveOperationException {
        projectileType.getConstructor(EntityType.class, Level.class);
        projectileType.getConstructor(
                EntityType.class, double.class, double.class, double.class, Level.class, ItemStack.class);
        projectileType.getConstructor(EntityType.class, LivingEntity.class, Level.class, ItemStack.class);

        Field projectileItem = projectileType.getDeclaredField("PROJECTILE_ITEM");
        assertTrue(Modifier.isPublic(projectileItem.getModifiers()));
        assertTrue(Modifier.isStatic(projectileItem.getModifiers()));
        assertEquals(ItemStack.class, projectileItem.getType());

        assertEquals(projectileType, projectileType.getDeclaredMethod(
                "shoot", Level.class, LivingEntity.class, RandomSource.class).getReturnType());
        assertEquals(projectileType, projectileType.getDeclaredMethod(
                "shoot", Level.class, LivingEntity.class, RandomSource.class, float.class).getReturnType());
        assertEquals(projectileType, projectileType.getDeclaredMethod(
                "shoot", Level.class, LivingEntity.class, RandomSource.class,
                float.class, double.class, int.class).getReturnType());
        assertEquals(projectileType, projectileType.getDeclaredMethod(
                "shoot", LivingEntity.class, LivingEntity.class).getReturnType());
    }

    private static Set<String> names(Set<Field> fields) {
        return fields.stream().map(Field::getName).collect(Collectors.toUnmodifiableSet());
    }

    private static Set<String> ids(Set<Field> fields) {
        return fields.stream().map(field -> {
            try {
                return ((DeferredHolder<?, ?>) field.get(null)).getId().toString();
            } catch (IllegalAccessException exception) {
                throw new IllegalStateException(exception);
            }
        }).collect(Collectors.toUnmodifiableSet());
    }
}
