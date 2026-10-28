package net.ssc14.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.ssc14.client.model.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/** Canonical registration of all generated model layer definitions. */
@EventBusSubscriber(Dist.CLIENT)
public final class Ssc14ModelLayers {
    private Ssc14ModelLayers() { }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        register(event, Modelframe_of_plasstal_wall_JAVA.LAYER_LOCATION, Modelframe_of_plasstal_wall_JAVA::createBodyLayer);
        register(event, Modelhardsuit_salvage_equipped.LAYER_LOCATION, Modelhardsuit_salvage_equipped::createBodyLayer);
        register(event, ModelConsole_of_ID_DisConnected_ENTITY.LAYER_LOCATION, ModelConsole_of_ID_DisConnected_ENTITY::createBodyLayer);
        register(event, Modelmantle_equipped.LAYER_LOCATION, Modelmantle_equipped::createBodyLayer);
        register(event, Modeljumpsuit_equipped.LAYER_LOCATION, Modeljumpsuit_equipped::createBodyLayer);
        register(event, Modelarmor_security_equipped.LAYER_LOCATION, Modelarmor_security_equipped::createBodyLayer);
        register(event, Modelhelmets_eva_equipped.LAYER_LOCATION, Modelhelmets_eva_equipped::createBodyLayer);
        register(event, Modelcloaks_cap.LAYER_LOCATION, Modelcloaks_cap::createBodyLayer);
        register(event, Modelgloves_equipped.LAYER_LOCATION, Modelgloves_equipped::createBodyLayer);
        register(event, Modelheadset_equipped.LAYER_LOCATION, Modelheadset_equipped::createBodyLayer);
        register(event, Modelhead_cone_equipped.LAYER_LOCATION, Modelhead_cone_equipped::createBodyLayer);
        register(event, Modelbackpack_equipped.LAYER_LOCATION, Modelbackpack_equipped::createBodyLayer);
        register(event, Modelarmor_security.LAYER_LOCATION, Modelarmor_security::createBodyLayer);
        register(event, Modelboots_magboots.LAYER_LOCATION, Modelboots_magboots::createBodyLayer);
        register(event, Modelhelmets_eva_large_equipped.LAYER_LOCATION, Modelhelmets_eva_large_equipped::createBodyLayer);
        register(event, Modelcloak_equipped.LAYER_LOCATION, Modelcloak_equipped::createBodyLayer);
        register(event, Modelboots_jackboots_equipped.LAYER_LOCATION, Modelboots_jackboots_equipped::createBodyLayer);
        register(event, Modelmask_gas_equipped.LAYER_LOCATION, Modelmask_gas_equipped::createBodyLayer);
        register(event, Modelmale_boxers.LAYER_LOCATION, Modelmale_boxers::createBodyLayer);
        register(event, Modelmouses.LAYER_LOCATION, Modelmouses::createBodyLayer);
        register(event, Modeldecal_1.LAYER_LOCATION, Modeldecal_1::createBodyLayer);
        register(event, Modelfoam_dart.LAYER_LOCATION, Modelfoam_dart::createBodyLayer);
        register(event, Modelheadsets.LAYER_LOCATION, Modelheadsets::createBodyLayer);
        register(event, Modeltanks_suitstorage_equipped.LAYER_LOCATION, Modeltanks_suitstorage_equipped::createBodyLayer);
        register(event, Modelsoles_equipped.LAYER_LOCATION, Modelsoles_equipped::createBodyLayer);
        register(event, Modelhelmet_security.LAYER_LOCATION, Modelhelmet_security::createBodyLayer);
        register(event, Modelframe_of_wall_JAVA.LAYER_LOCATION, Modelframe_of_wall_JAVA::createBodyLayer);
        register(event, Modelties_equipped.LAYER_LOCATION, Modelties_equipped::createBodyLayer);
        register(event, Modelcarp.LAYER_LOCATION, Modelcarp::createBodyLayer);
        register(event, Modelcoat_security_equipped.LAYER_LOCATION, Modelcoat_security_equipped::createBodyLayer);
        register(event, Modelsocks_equipped.LAYER_LOCATION, Modelsocks_equipped::createBodyLayer);
        register(event, Modelfelinid_m.LAYER_LOCATION, Modelfelinid_m::createBodyLayer);
        register(event, Modelcolor_soles.LAYER_LOCATION, Modelcolor_soles::createBodyLayer);
        register(event, Modelmask_gassecurity_equipped.LAYER_LOCATION, Modelmask_gassecurity_equipped::createBodyLayer);
        register(event, Modelpda_equipped.LAYER_LOCATION, Modelpda_equipped::createBodyLayer);
        register(event, Modelboots_magboots_equipped.LAYER_LOCATION, Modelboots_magboots_equipped::createBodyLayer);
        register(event, Modelglass_equipped.LAYER_LOCATION, Modelglass_equipped::createBodyLayer);
        register(event, Modelhelmet_security_equipped.LAYER_LOCATION, Modelhelmet_security_equipped::createBodyLayer);
        register(event, Modelhelmets_eva_syndicate_equipped.LAYER_LOCATION, Modelhelmets_eva_syndicate_equipped::createBodyLayer);
        register(event, Modelmothroach.LAYER_LOCATION, Modelmothroach::createBodyLayer);
        register(event, Modelbullet.LAYER_LOCATION, Modelbullet::createBodyLayer);
        register(event, Modelwintercoat_coat_equipped.LAYER_LOCATION, Modelwintercoat_coat_equipped::createBodyLayer);
        register(event, Modelhuman_m.LAYER_LOCATION, Modelhuman_m::createBodyLayer);
        register(event, Modelbelt.LAYER_LOCATION, Modelbelt::createBodyLayer);
        register(event, Modelgloves.LAYER_LOCATION, Modelgloves::createBodyLayer);
        register(event, Modelscarfs_equipped.LAYER_LOCATION, Modelscarfs_equipped::createBodyLayer);
        register(event, Modelscarf.LAYER_LOCATION, Modelscarf::createBodyLayer);
        register(event, Modelhardsuit_helmet_salvage_equipped.LAYER_LOCATION, Modelhardsuit_helmet_salvage_equipped::createBodyLayer);
        register(event, Modeluniform_equipped.LAYER_LOCATION, Modeluniform_equipped::createBodyLayer);
        register(event, Modelboots_combatboots.LAYER_LOCATION, Modelboots_combatboots::createBodyLayer);
        register(event, Modelunath_m.LAYER_LOCATION, Modelunath_m::createBodyLayer);
        register(event, Modelboots_workboots_equipped.LAYER_LOCATION, Modelboots_workboots_equipped::createBodyLayer);
        register(event, Modelhelmet_cosmonaut_equipped.LAYER_LOCATION, Modelhelmet_cosmonaut_equipped::createBodyLayer);
        register(event, Modelmantle_hos_shoulder_equipped.LAYER_LOCATION, Modelmantle_hos_shoulder_equipped::createBodyLayer);
        register(event, Modelouterclothing_eva_equipped.LAYER_LOCATION, Modelouterclothing_eva_equipped::createBodyLayer);
        register(event, Modelmantles_cap.LAYER_LOCATION, Modelmantles_cap::createBodyLayer);
        register(event, Modelalt_headset_equipped.LAYER_LOCATION, Modelalt_headset_equipped::createBodyLayer);
        register(event, Modelbelt_equipped.LAYER_LOCATION, Modelbelt_equipped::createBodyLayer);
    }

    private static void register(EntityRenderersEvent.RegisterLayerDefinitions event, ModelLayerLocation location,
            java.util.function.Supplier<net.minecraft.client.model.geom.builders.LayerDefinition> factory) {
        event.registerLayerDefinition(location, factory);
    }
}
