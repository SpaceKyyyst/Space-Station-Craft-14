package net.mcreator.ssc.init;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.Minecraft;

import net.ssc14.client.model.Modelunath_m;
import net.ssc14.client.model.Modelhuman_m;
import net.ssc14.client.model.Modelfelinid_m;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

@Deprecated(forRemoval = true)
public class Ssc14ModHumanoidModels {
	public static PlayerModel HUMAN_MODEL;
	public static PlayerModel UNATH_MODEL;
	public static PlayerModel FELINID_MODEL;

	@SubscribeEvent
	public static void initModels(EntityRenderersEvent.AddLayers event) {
		Modelhuman_m human_model_temp = new Modelhuman_m(Minecraft.getInstance().getEntityModels().bakeLayer(Modelhuman_m.LAYER_LOCATION));
		HUMAN_MODEL = new PlayerModel(new ModelPart(Collections.emptyList(),
				Map.of("head", new ModelPart(Collections.emptyList(), Map.of("head", human_model_temp.head, "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()))), "body", getPlayerPart(human_model_temp.torso, "jacket"), "left_arm",
						getPlayerPart(human_model_temp.l_arm, "left_sleeve"), "right_arm", getPlayerPart(human_model_temp.r_arm, "right_sleeve"), "left_leg", getPlayerPart(human_model_temp.l_leg, "left_pants"), "right_leg",
						getPlayerPart(human_model_temp.r_leg, "right_pants"))),
				false);
		Modelunath_m unath_model_temp = new Modelunath_m(Minecraft.getInstance().getEntityModels().bakeLayer(Modelunath_m.LAYER_LOCATION));
		UNATH_MODEL = new PlayerModel(new ModelPart(Collections.emptyList(),
				Map.of("head", new ModelPart(Collections.emptyList(), Map.of("head", unath_model_temp.head, "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()))), "body", getPlayerPart(unath_model_temp.torso, "jacket"), "left_arm",
						getPlayerPart(unath_model_temp.l_arm, "left_sleeve"), "right_arm", getPlayerPart(unath_model_temp.r_arm, "right_sleeve"), "left_leg", getPlayerPart(unath_model_temp.l_leg, "left_pants"), "right_leg",
						getPlayerPart(unath_model_temp.r_leg, "right_pants"))),
				false);
		Modelfelinid_m felinid_model_temp = new Modelfelinid_m(Minecraft.getInstance().getEntityModels().bakeLayer(Modelfelinid_m.LAYER_LOCATION));
		FELINID_MODEL = new PlayerModel(new ModelPart(Collections.emptyList(),
				Map.of("head", new ModelPart(Collections.emptyList(), Map.of("head", felinid_model_temp.head, "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()))), "body", getPlayerPart(felinid_model_temp.torso, "jacket"),
						"left_arm", getPlayerPart(felinid_model_temp.l_arm, "left_sleeve"), "right_arm", getPlayerPart(felinid_model_temp.r_arm, "right_sleeve"), "left_leg", getPlayerPart(felinid_model_temp.l_leg, "left_pants"), "right_leg",
						getPlayerPart(felinid_model_temp.r_leg, "right_pants"))),
				false);
	}

	private static ModelPart getPlayerPart(ModelPart modelPart, String child) {
		Map<String, ModelPart> oldChildren = modelPart.children;
		Map<String, ModelPart> newChildren = new HashMap<>(oldChildren);
		newChildren.put(child, new ModelPart(Collections.emptyList(), Collections.emptyMap()));
		modelPart.children = Collections.unmodifiableMap(newChildren);
		return modelPart;
	}
}
