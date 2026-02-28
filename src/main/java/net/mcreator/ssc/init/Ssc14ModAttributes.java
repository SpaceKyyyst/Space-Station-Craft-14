/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.ssc.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.core.registries.BuiltInRegistries;

import net.mcreator.ssc.Ssc14Mod;

import java.util.stream.Collectors;
import java.util.List;

@EventBusSubscriber
public class Ssc14ModAttributes {
	public static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, Ssc14Mod.MODID);
	public static final DeferredHolder<Attribute, Attribute> PROGRESS_BAR_ATRB = REGISTRY.register("progress_bar_atrb",
			() -> new RangedAttribute("attribute.ssc_14.progress_bar_atrb", 0, 0, 7).setSyncable(true).setSentiment(Attribute.Sentiment.NEUTRAL));
	public static final DeferredHolder<Attribute, Attribute> XY_ZPLAYER = REGISTRY.register("xy_zplayer", () -> new RangedAttribute("attribute.ssc_14.xy_zplayer", 0, -30000000, 30000000).setSyncable(true).setSentiment(Attribute.Sentiment.NEUTRAL));
	public static final DeferredHolder<Attribute, Attribute> ENT_PULL = REGISTRY.register("ent_pull", () -> new RangedAttribute("attribute.ssc_14.ent_pull", 0, 0, 1).setSyncable(true).setSentiment(Attribute.Sentiment.NEUTRAL));
	public static final DeferredHolder<Attribute, Attribute> GAS_AN_GU_IATRIB = REGISTRY.register("gas_an_gu_iatrib",
			() -> new RangedAttribute("attribute.ssc_14.gas_an_gu_iatrib", 0, 0, 1).setSyncable(true).setSentiment(Attribute.Sentiment.NEUTRAL));
	public static final DeferredHolder<Attribute, Attribute> OPEN_WORLD_CHECK_MENU = REGISTRY.register("open_world_check_menu",
			() -> new RangedAttribute("attribute.ssc_14.open_world_check_menu", 0, 0, 1).setSyncable(true).setSentiment(Attribute.Sentiment.NEUTRAL));
	public static final DeferredHolder<Attribute, Attribute> NUTRIENTS = REGISTRY.register("nutrients", () -> new RangedAttribute("attribute.ssc_14.nutrients", 125, 0, 200).setSyncable(true).setSentiment(Attribute.Sentiment.NEUTRAL));
	public static final DeferredHolder<Attribute, Attribute> DIGESTIVE_PROCESSES = REGISTRY.register("digestive_processes",
			() -> new RangedAttribute("attribute.ssc_14.digestive_processes", 0, 0, 10).setSyncable(true).setSentiment(Attribute.Sentiment.NEUTRAL));

	@SubscribeEvent
	public static void addAttributes(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, PROGRESS_BAR_ATRB);
		event.add(EntityType.PLAYER, XY_ZPLAYER);
		List.of(Ssc14ModEntities.WALL_CARCASE_ENTIT.get(), Ssc14ModEntities.ID_CONSOLE_ENTITY.get()).stream().filter(DefaultAttributes::hasSupplier).map(entityType -> (EntityType<? extends LivingEntity>) entityType).collect(Collectors.toList())
				.forEach(entity -> event.add(entity, ENT_PULL));
		event.add(EntityType.PLAYER, ENT_PULL);
		event.add(EntityType.PLAYER, GAS_AN_GU_IATRIB);
		event.add(EntityType.PLAYER, OPEN_WORLD_CHECK_MENU);
		event.add(EntityType.PLAYER, NUTRIENTS);
		event.add(EntityType.PLAYER, DIGESTIVE_PROCESSES);
	}
}