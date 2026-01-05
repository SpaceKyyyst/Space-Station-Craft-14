/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.ssc.init;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.ssc.client.model.Modelframe_of_wall_JAVA;
import net.mcreator.ssc.client.model.ModelConsole_of_ID_DisConnected_ENTITY;

@EventBusSubscriber(Dist.CLIENT)
public class Ssc14ModModels {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModelConsole_of_ID_DisConnected_ENTITY.LAYER_LOCATION, ModelConsole_of_ID_DisConnected_ENTITY::createBodyLayer);
		event.registerLayerDefinition(Modelframe_of_wall_JAVA.LAYER_LOCATION, Modelframe_of_wall_JAVA::createBodyLayer);
	}
}