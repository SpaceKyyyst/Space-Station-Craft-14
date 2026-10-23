package net.mcreator.ssc.init;

import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ssc14.content.entity.Bullet35Entity;
import net.ssc14.content.entity.C4CrutchEntEntity;
import net.ssc14.content.entity.CarpEntity;
import net.ssc14.content.entity.ChairEntityEntity;
import net.ssc14.content.entity.FoamDartProjectileEntity;
import net.ssc14.content.entity.IDConsoleENTITYEntity;
import net.ssc14.content.entity.MouseBrownEntity;
import net.ssc14.content.entity.MouseGrayEntity;
import net.ssc14.content.entity.MouseWhiteEntity;
import net.ssc14.content.entity.PlassteelWallCarcaseEntitEntity;
import net.ssc14.content.entity.PosterEntity;
import net.ssc14.content.entity.Ssc14EntityTypes;
import net.ssc14.content.entity.WallCarcaseEntitEntity;

/** @deprecated Use {@link Ssc14EntityTypes}. */
@Deprecated(forRemoval = false)
public class Ssc14ModEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = Ssc14EntityTypes.REGISTRY;
    public static final DeferredHolder<EntityType<?>, EntityType<WallCarcaseEntitEntity>> WALL_CARCASE_ENTIT = Ssc14EntityTypes.WALL_CARCASE_ENTIT;
    public static final DeferredHolder<EntityType<?>, EntityType<IDConsoleENTITYEntity>> ID_CONSOLE_ENTITY = Ssc14EntityTypes.ID_CONSOLE_ENTITY;
    public static final DeferredHolder<EntityType<?>, EntityType<PlassteelWallCarcaseEntitEntity>> PLASSTEEL_WALL_CARCASE_ENTIT = Ssc14EntityTypes.PLASSTEEL_WALL_CARCASE_ENTIT;
    public static final DeferredHolder<EntityType<?>, EntityType<ChairEntityEntity>> CHAIR_ENTITY = Ssc14EntityTypes.CHAIR_ENTITY;
    public static final DeferredHolder<EntityType<?>, EntityType<C4CrutchEntEntity>> C_4_CRUTCH_ENT = Ssc14EntityTypes.C_4_CRUTCH_ENT;
    public static final DeferredHolder<EntityType<?>, EntityType<MouseBrownEntity>> MOUSE_BROWN = Ssc14EntityTypes.MOUSE_BROWN;
    public static final DeferredHolder<EntityType<?>, EntityType<MouseGrayEntity>> MOUSE_GRAY = Ssc14EntityTypes.MOUSE_GRAY;
    public static final DeferredHolder<EntityType<?>, EntityType<MouseWhiteEntity>> MOUSE_WHITE = Ssc14EntityTypes.MOUSE_WHITE;
    public static final DeferredHolder<EntityType<?>, EntityType<Bullet35Entity>> BULLET_35 = Ssc14EntityTypes.BULLET_35;
    public static final DeferredHolder<EntityType<?>, EntityType<FoamDartProjectileEntity>> FOAM_DART_PROJECTILE = Ssc14EntityTypes.FOAM_DART_PROJECTILE;
    public static final DeferredHolder<EntityType<?>, EntityType<PosterEntity>> POSTER = Ssc14EntityTypes.POSTER;
    public static final DeferredHolder<EntityType<?>, EntityType<CarpEntity>> CARP = Ssc14EntityTypes.CARP;

    /** Retained for source and binary compatibility with the generated legacy API. */
    public Ssc14ModEntities() {
    }

    public static void init(RegisterSpawnPlacementsEvent event) {
        Ssc14EntityTypes.registerSpawnPlacements(event);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        Ssc14EntityTypes.registerAttributes(event);
    }
}
