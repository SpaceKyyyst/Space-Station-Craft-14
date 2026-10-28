package net.ssc14.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.player.PlayerModel;
import net.ssc14.client.model.Modelfelinid_m;
import net.ssc14.client.model.Modelhuman_m;
import net.ssc14.client.model.Modelunath_m;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/** Canonical client-owned humanoid model registry. */
@EventBusSubscriber(Dist.CLIENT)
public final class Ssc14HumanoidModels {
    public static PlayerModel HUMAN_MODEL;
    public static PlayerModel UNATH_MODEL;
    public static PlayerModel FELINID_MODEL;

    private Ssc14HumanoidModels() { }

    @SubscribeEvent
    public static void initModels(EntityRenderersEvent.AddLayers event) {
        Modelhuman_m human = new Modelhuman_m(Minecraft.getInstance().getEntityModels().bakeLayer(Modelhuman_m.LAYER_LOCATION));
        HUMAN_MODEL = playerModel(human.head, human.torso, human.l_arm, human.r_arm, human.l_leg, human.r_leg);
        Modelunath_m unath = new Modelunath_m(Minecraft.getInstance().getEntityModels().bakeLayer(Modelunath_m.LAYER_LOCATION));
        UNATH_MODEL = playerModel(unath.head, unath.torso, unath.l_arm, unath.r_arm, unath.l_leg, unath.r_leg);
        Modelfelinid_m felinid = new Modelfelinid_m(Minecraft.getInstance().getEntityModels().bakeLayer(Modelfelinid_m.LAYER_LOCATION));
        FELINID_MODEL = playerModel(felinid.head, felinid.torso, felinid.l_arm, felinid.r_arm, felinid.l_leg, felinid.r_leg);
    }

    private static PlayerModel playerModel(ModelPart head, ModelPart torso, ModelPart leftArm,
            ModelPart rightArm, ModelPart leftLeg, ModelPart rightLeg) {
        return new PlayerModel(new ModelPart(Collections.emptyList(), Map.of(
                "head", new ModelPart(Collections.emptyList(), Map.of("head", head,
                        "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()))),
                "body", playerPart(torso, "jacket"), "left_arm", playerPart(leftArm, "left_sleeve"),
                "right_arm", playerPart(rightArm, "right_sleeve"), "left_leg", playerPart(leftLeg, "left_pants"),
                "right_leg", playerPart(rightLeg, "right_pants"))), false);
    }

    private static ModelPart playerPart(ModelPart modelPart, String child) {
        Map<String, ModelPart> children = new HashMap<>(modelPart.children);
        children.put(child, new ModelPart(Collections.emptyList(), Collections.emptyMap()));
        modelPart.children = Collections.unmodifiableMap(children);
        return modelPart;
    }
}
