/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.ssc.init;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.ssc.client.model.*;

@EventBusSubscriber(Dist.CLIENT)
public class Ssc14ModModels {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(Modelframe_of_plasstal_wall_JAVA.LAYER_LOCATION, Modelframe_of_plasstal_wall_JAVA::createBodyLayer);
		event.registerLayerDefinition(ModelConsole_of_ID_DisConnected_ENTITY.LAYER_LOCATION, ModelConsole_of_ID_DisConnected_ENTITY::createBodyLayer);
		event.registerLayerDefinition(Modelhelmets_eva_equipped.LAYER_LOCATION, Modelhelmets_eva_equipped::createBodyLayer);
		event.registerLayerDefinition(Modelcloaks_cap.LAYER_LOCATION, Modelcloaks_cap::createBodyLayer);
		event.registerLayerDefinition(Modelarmor_security.LAYER_LOCATION, Modelarmor_security::createBodyLayer);
		event.registerLayerDefinition(Modelboots_magboots.LAYER_LOCATION, Modelboots_magboots::createBodyLayer);
		event.registerLayerDefinition(Modelhelmets_eva_large_equipped.LAYER_LOCATION, Modelhelmets_eva_large_equipped::createBodyLayer);
		event.registerLayerDefinition(Modeldecal_1.LAYER_LOCATION, Modeldecal_1::createBodyLayer);
		event.registerLayerDefinition(Modelheadsets.LAYER_LOCATION, Modelheadsets::createBodyLayer);
		event.registerLayerDefinition(Modelhelmet_security.LAYER_LOCATION, Modelhelmet_security::createBodyLayer);
		event.registerLayerDefinition(Modelframe_of_wall_JAVA.LAYER_LOCATION, Modelframe_of_wall_JAVA::createBodyLayer);
		event.registerLayerDefinition(Modelcolor_soles.LAYER_LOCATION, Modelcolor_soles::createBodyLayer);
		event.registerLayerDefinition(Modelpda_equipped.LAYER_LOCATION, Modelpda_equipped::createBodyLayer);
		event.registerLayerDefinition(Modelhelmets_eva_syndicate_equipped.LAYER_LOCATION, Modelhelmets_eva_syndicate_equipped::createBodyLayer);
		event.registerLayerDefinition(Modeljumpsuit_security.LAYER_LOCATION, Modeljumpsuit_security::createBodyLayer);
		event.registerLayerDefinition(Modelhuman_m.LAYER_LOCATION, Modelhuman_m::createBodyLayer);
		event.registerLayerDefinition(Modelbelt.LAYER_LOCATION, Modelbelt::createBodyLayer);
		event.registerLayerDefinition(Modelgloves.LAYER_LOCATION, Modelgloves::createBodyLayer);
		event.registerLayerDefinition(Modelscarf.LAYER_LOCATION, Modelscarf::createBodyLayer);
		event.registerLayerDefinition(Modelboots_combatboots.LAYER_LOCATION, Modelboots_combatboots::createBodyLayer);
		event.registerLayerDefinition(Modelouterclothing_eva_equipped.LAYER_LOCATION, Modelouterclothing_eva_equipped::createBodyLayer);
		event.registerLayerDefinition(Modelmantles_cap.LAYER_LOCATION, Modelmantles_cap::createBodyLayer);
	}
}