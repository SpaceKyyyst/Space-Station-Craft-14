package net.ssc14.content.entity;

import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14EntityTypesGameTests {
    private static final String TEST_PATH = "compatibility_entity_type_contract";
    private static final Identifier TEST_ID = id(TEST_PATH);
    private static final List<ExpectedEntity> EXPECTED = List.of(
            expected(Ssc14EntityTypes.WALL_CARCASE_ENTIT, "wall_carcase_entit", WallCarcaseEntitEntity.class,
                    MobCategory.MONSTER, 0.9f, 0.9f, 1, 3, true, true, null),
            expected(Ssc14EntityTypes.ID_CONSOLE_ENTITY, "id_console_entity", IDConsoleENTITYEntity.class,
                    MobCategory.MONSTER, 0.9f, 1.1f, 1, 3, true, true, null),
            expected(Ssc14EntityTypes.PLASSTEEL_WALL_CARCASE_ENTIT, "plassteel_wall_carcase_entit",
                    PlassteelWallCarcaseEntitEntity.class, MobCategory.MONSTER, 0.9f, 0.9f, 1, 3, true, true, null),
            expected(Ssc14EntityTypes.CHAIR_ENTITY, "chair_entity", ChairEntityEntity.class,
                    MobCategory.MONSTER, 0.1f, 0.1f, 64, 3, true, true, null),
            expected(Ssc14EntityTypes.C_4_CRUTCH_ENT, "c_4_crutch_ent", C4CrutchEntEntity.class,
                    MobCategory.MONSTER, 0.01f, 0.01f, 64, 3, false, true, null),
            expected(Ssc14EntityTypes.MOUSE_BROWN, "mouse_brown", MouseBrownEntity.class,
                    MobCategory.MONSTER, 0.3f, 0.3f, 64, 3, false, true, null),
            expected(Ssc14EntityTypes.MOUSE_GRAY, "mouse_gray", MouseGrayEntity.class,
                    MobCategory.MONSTER, 0.3f, 0.3f, 64, 3, false, true, null),
            expected(Ssc14EntityTypes.MOUSE_WHITE, "mouse_white", MouseWhiteEntity.class,
                    MobCategory.MONSTER, 0.3f, 0.3f, 64, 3, false, true, null),
            expected(Ssc14EntityTypes.BULLET_35, "bullet_35", Bullet35Entity.class,
                    MobCategory.MISC, 0.1f, 0.1f, 64, 1, false, false, "bullet_35item"),
            expected(Ssc14EntityTypes.FOAM_DART_PROJECTILE, "foam_dart_projectile",
                    FoamDartProjectileEntity.class, MobCategory.MISC, 0.1f, 0.3f, 64, 1, false, false, "foam_dart"),
            expected(Ssc14EntityTypes.POSTER, "poster", PosterEntity.class,
                    MobCategory.MONSTER, 0.1f, 1.0f, 64, 3, false, true, null),
            expected(Ssc14EntityTypes.CARP, "carp", CarpEntity.class,
                    MobCategory.MONSTER, 0.5f, 0.5f, 64, 3, false, true, null));

    private Ssc14EntityTypesGameTests() {
    }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID, Ssc14EntityTypesGameTests::verifyEntityTypeContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("entity_type_compatibility_environment"));
        var data = new TestData<>(environment, id("audit_empty"), 20, 1, true);
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID), data));
    }

    private static void verifyEntityTypeContract(GameTestHelper helper) {
        for (ExpectedEntity expected : EXPECTED) {
            EntityType<?> type = expected.holder().get();
            helper.assertTrue(id(expected.path()).equals(BuiltInRegistries.ENTITY_TYPE.getKey(type)),
                    expected.path() + " registry ID changed");
            helper.assertTrue(type.getCategory() == expected.category(), expected.path() + " category changed");
            assertFloat(helper, expected.width(), type.getDimensions().width(), expected.path() + " width");
            assertFloat(helper, expected.height(), type.getDimensions().height(), expected.path() + " height");
            helper.assertTrue(type.clientTrackingRange() == expected.trackingRange(),
                    expected.path() + " tracking range changed");
            helper.assertTrue(type.updateInterval() == expected.updateInterval(),
                    expected.path() + " update interval changed");
            helper.assertTrue(type.trackDeltas(), expected.path() + " must track velocity deltas");
            helper.assertTrue(type.fireImmune() == expected.fireImmune(),
                    expected.path() + " fire immunity changed");
            helper.assertTrue(DefaultAttributes.hasSupplier(type) == expected.hasAttributes(),
                    expected.path() + " default attribute binding changed");

            Entity entity = type.create(helper.getLevel(), EntitySpawnReason.COMMAND);
            helper.assertTrue(entity != null, expected.path() + " factory returned null");
            helper.assertTrue(expected.implementation().isInstance(entity),
                    expected.path() + " factory implementation changed to " + entity.getClass().getName());
            if (expected.projectileItemPath() != null) {
                helper.assertTrue(entity instanceof ItemSupplier,
                        expected.path() + " no longer supplies a projectile item");
                var projectileItem = ((ItemSupplier) entity).getItem().getItem();
                helper.assertTrue(id(expected.projectileItemPath()).equals(
                                BuiltInRegistries.ITEM.getKey(projectileItem)),
                        expected.path() + " projectile item changed");
            }
        }
        helper.succeed();
    }

    private static void assertFloat(GameTestHelper helper, float expected, float actual, String label) {
        helper.assertTrue(Float.compare(expected, actual) == 0,
                label + " changed: expected " + expected + ", got " + actual);
    }

    private static ExpectedEntity expected(
            DeferredHolder<EntityType<?>, ? extends EntityType<?>> holder,
            String path,
            Class<? extends Entity> implementation,
            MobCategory category,
            float width,
            float height,
            int trackingRange,
            int updateInterval,
            boolean fireImmune,
            boolean hasAttributes,
            String projectileItemPath) {
        return new ExpectedEntity(holder, path, implementation, category, width, height,
                trackingRange, updateInterval, fireImmune, hasAttributes, projectileItemPath);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedEntity(
            DeferredHolder<EntityType<?>, ? extends EntityType<?>> holder,
            String path,
            Class<? extends Entity> implementation,
            MobCategory category,
            float width,
            float height,
            int trackingRange,
            int updateInterval,
            boolean fireImmune,
            boolean hasAttributes,
            String projectileItemPath) {
    }
}
