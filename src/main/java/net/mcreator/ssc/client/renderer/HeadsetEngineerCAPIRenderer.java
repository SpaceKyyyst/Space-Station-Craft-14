package net.mcreator.ssc.client.renderer;

import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.SlotContext;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.Minecraft;

import net.mcreator.ssc.client.model.Modelheadset_equipped;

import java.util.Map;
import java.util.Collections;

import com.mojang.blaze3d.vertex.PoseStack;

public class HeadsetEngineerCAPIRenderer<S extends LivingEntityRenderState, M extends EntityModel<? super S>> implements ICurioRenderer.HumanoidRender {
	private static final Identifier TEXTURE = Identifier.parse("ssc_14:textures/entities/headset_engineering_equipped_texture.png");
	private final HumanoidModel humanoidModel;

	public HeadsetEngineerCAPIRenderer() {
        Modelheadset_equipped model = new Modelheadset_equipped(Minecraft.getInstance().getEntityModels().bakeLayer(Modelheadset_equipped.LAYER_LOCATION));
        this.humanoidModel = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of(
            "head", new ModelPart(Collections.emptyList(), Map.of(
                    "head", model.head,
                "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap())
            )),
                "body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())
        )));
	}

    @Override
    public void renderFirstPersonHand(
      ItemStack stack,
      SlotContext slotContext,
      HumanoidArm arm,
      PoseStack poseStack,
      SubmitNodeCollector submitNodeCollector,
      AvatarRenderState avatarRenderState,
      AbstractClientPlayer clientPlayer,
      int packedLight) {}

    @Override
    public HumanoidModel<HumanoidRenderState> getModel(ItemStack stack, SlotContext slotContext) {
        return this.humanoidModel;
    }

    @Override
    public Identifier getModelTexture(ItemStack stack, SlotContext slotContext) {
        return TEXTURE;
    }
}