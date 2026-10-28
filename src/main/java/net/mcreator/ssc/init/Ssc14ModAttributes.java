/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.ssc.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.ssc14.content.attribute.Ssc14Attributes;
import net.ssc14.content.entity.Ssc14EntityTypes;

import java.util.stream.Collectors;
import java.util.List;

@EventBusSubscriber
@Deprecated(forRemoval = false)
public class Ssc14ModAttributes {
	public static final DeferredRegister<Attribute> REGISTRY = Ssc14Attributes.REGISTRY;
	public static final DeferredHolder<Attribute, Attribute> PROGRESS_BAR_ATRB = Ssc14Attributes.PROGRESS_BAR_ATRB;
	public static final DeferredHolder<Attribute, Attribute> XY_ZPLAYER = Ssc14Attributes.XY_ZPLAYER;
	public static final DeferredHolder<Attribute, Attribute> ENT_PULL = Ssc14Attributes.ENT_PULL;
	public static final DeferredHolder<Attribute, Attribute> GAS_AN_GU_IATRIB = Ssc14Attributes.GAS_AN_GU_IATRIB;
	public static final DeferredHolder<Attribute, Attribute> OPEN_WORLD_CHECK_MENU = Ssc14Attributes.OPEN_WORLD_CHECK_MENU;
	public static final DeferredHolder<Attribute, Attribute> NUTRIENTS = Ssc14Attributes.NUTRIENTS;
	public static final DeferredHolder<Attribute, Attribute> DIGESTIVE_PROCESSES = Ssc14Attributes.DIGESTIVE_PROCESSES;
	public static final DeferredHolder<Attribute, Attribute> HEALTH_U_IATTRIBUTE = Ssc14Attributes.HEALTH_U_IATTRIBUTE;
	public static final DeferredHolder<Attribute, Attribute> LIQUID = Ssc14Attributes.LIQUID;
	public static final DeferredHolder<Attribute, Attribute> FLUID_ABSORPTION = Ssc14Attributes.FLUID_ABSORPTION;
	public static final DeferredHolder<Attribute, Attribute> RACE = Ssc14Attributes.RACE;

	/** Retained for source and binary compatibility with the generated legacy API. */
	@Deprecated(forRemoval = false)
	public Ssc14ModAttributes() {
	}

	@SubscribeEvent
	public static void addAttributes(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, PROGRESS_BAR_ATRB);
		event.add(EntityType.PLAYER, XY_ZPLAYER);
		List.of(Ssc14EntityTypes.WALL_CARCASE_ENTIT.get(), Ssc14EntityTypes.ID_CONSOLE_ENTITY.get()).stream().filter(DefaultAttributes::hasSupplier).map(entityType -> (EntityType<? extends LivingEntity>) entityType).collect(Collectors.toList())
				.forEach(entity -> event.add(entity, ENT_PULL));
		event.add(EntityType.PLAYER, ENT_PULL);
		event.add(EntityType.PLAYER, GAS_AN_GU_IATRIB);
		event.add(EntityType.PLAYER, OPEN_WORLD_CHECK_MENU);
		event.add(EntityType.PLAYER, NUTRIENTS);
		event.add(EntityType.PLAYER, DIGESTIVE_PROCESSES);
		event.add(EntityType.PLAYER, HEALTH_U_IATTRIBUTE);
		event.add(EntityType.PLAYER, LIQUID);
		event.add(EntityType.PLAYER, FLUID_ABSORPTION);
		event.add(EntityType.PLAYER, RACE);
	}
}
