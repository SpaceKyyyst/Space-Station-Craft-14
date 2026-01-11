/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.ssc.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.mcreator.ssc.entity.WallCarcaseEntitEntity;
import net.mcreator.ssc.entity.PlassteelWallCarcaseEntitEntity;
import net.mcreator.ssc.entity.IDConsoleENTITYEntity;
import net.mcreator.ssc.Ssc14Mod;

@EventBusSubscriber
public class Ssc14ModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, Ssc14Mod.MODID);
	public static final DeferredHolder<EntityType<?>, EntityType<WallCarcaseEntitEntity>> WALL_CARCASE_ENTIT = register("wall_carcase_entit",
			EntityType.Builder.<WallCarcaseEntitEntity>of(WallCarcaseEntitEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(1).setUpdateInterval(3).fireImmune()

					.sized(0.9f, 0.9f));
	public static final DeferredHolder<EntityType<?>, EntityType<IDConsoleENTITYEntity>> ID_CONSOLE_ENTITY = register("id_console_entity",
			EntityType.Builder.<IDConsoleENTITYEntity>of(IDConsoleENTITYEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(1).setUpdateInterval(3).fireImmune()

					.sized(0.9f, 1.1f));
	public static final DeferredHolder<EntityType<?>, EntityType<PlassteelWallCarcaseEntitEntity>> PLASSTEEL_WALL_CARCASE_ENTIT = register("plassteel_wall_carcase_entit",
			EntityType.Builder.<PlassteelWallCarcaseEntitEntity>of(PlassteelWallCarcaseEntitEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(1).setUpdateInterval(3).fireImmune()

					.sized(0.9f, 0.9f));

	// Start of user code block custom entities
	// End of user code block custom entities
	private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Ssc14Mod.MODID, registryname))));
	}

	@SubscribeEvent
	public static void init(RegisterSpawnPlacementsEvent event) {
		WallCarcaseEntitEntity.init(event);
		IDConsoleENTITYEntity.init(event);
		PlassteelWallCarcaseEntitEntity.init(event);
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(WALL_CARCASE_ENTIT.get(), WallCarcaseEntitEntity.createAttributes().build());
		event.put(ID_CONSOLE_ENTITY.get(), IDConsoleENTITYEntity.createAttributes().build());
		event.put(PLASSTEEL_WALL_CARCASE_ENTIT.get(), PlassteelWallCarcaseEntitEntity.createAttributes().build());
	}
}