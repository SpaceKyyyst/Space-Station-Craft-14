package net.mcreator.ssc.init;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.Minecraft;

import net.mcreator.ssc.client.model.Modelhuman_m;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

@EventBusSubscriber(Dist.CLIENT)
public class Ssc14ModHumanoidModels {
	public static PlayerModel HUMAN_MODEL;

	@SubscribeEvent
	public static void initModels(EntityRenderersEvent.AddLayers event) {
		Modelhuman_m human_model_temp = new Modelhuman_m(Minecraft.getInstance().getEntityModels().bakeLayer(Modelhuman_m.LAYER_LOCATION));
		HUMAN_MODEL = new PlayerModel(new ModelPart(Collections.emptyList(),
				Map.of("head", new ModelPart(Collections.emptyList(), Map.of("head", human_model_temp.head, "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()))), "body", getPlayerPart(human_model_temp.torso, "jacket"), "left_arm",
						getPlayerPart(human_model_temp.l_arm, "left_sleeve"), "right_arm", getPlayerPart(human_model_temp.r_arm, "right_sleeve"), "left_leg", getPlayerPart(human_model_temp.l_leg, "left_pants"), "right_leg",
						getPlayerPart(human_model_temp.r_leg, "right_pants"))),
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