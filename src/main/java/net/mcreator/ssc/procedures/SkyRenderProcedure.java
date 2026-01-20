package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.Minecraft;

import net.mcreator.ssc.client.RenderUtils;

import javax.annotation.Nullable;

@EventBusSubscriber(Dist.CLIENT)
public class SkyRenderProcedure {
	@SubscribeEvent
	public static void onSkyRendered(RenderLevelStageEvent.AfterSky event) {
		Minecraft mc = Minecraft.getInstance();
		execute(event, mc.player, event);
	}

	public static void execute(Entity entity, RenderLevelStageEvent.AfterSky skyRenderEvent) {
		execute(null, entity, skyRenderEvent);
	}

	private static void execute(@Nullable Event event, Entity entity, RenderLevelStageEvent.AfterSky skyRenderEvent) {
		if (entity == null || skyRenderEvent == null)
			return;
		if ((entity.level().dimension()) == ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("ssc_14:spaced"))) {
			RenderUtils.renderCustomSkybox(skyRenderEvent, ResourceLocation.parse("ssc_14:textures/space_sky_texture_2.png"), 0xffffff, Math.min(1, 1));
		}
	}
}