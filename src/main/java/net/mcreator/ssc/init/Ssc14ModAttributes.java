/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.ssc.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.minecraft.world.entity.ai.attributes.Attribute;

import net.ssc14.content.attribute.Ssc14Attributes;

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

	public static void addAttributes(EntityAttributeModificationEvent event) {
		Ssc14Attributes.addAttributes(event);
	}
}
