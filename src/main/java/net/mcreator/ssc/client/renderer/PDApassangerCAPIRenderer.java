package net.mcreator.ssc.client.renderer;

import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.SlotContext;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.Minecraft;

import net.mcreator.ssc.client.model.Modelpda_equipped;

import java.util.Map;
import java.util.Collections;

import com.mojang.blaze3d.vertex.PoseStack;

public class PDApassangerCAPIRenderer implements ICurioRenderer.HumanoidRender {
	private static final ResourceLocation TEXTURE = ResourceLocation.parse("ssc_14:textures/entities/pda_equipped_texture.png");
	private final HumanoidModel humanoidModel;

	public PDApassangerCAPIRenderer() {
        Modelpda_equipped model = new Modelpda_equipped(Minecraft.getInstance().getEntityModels().bakeLayer(Modelpda_equipped.LAYER_LOCATION));
        this.humanoidModel = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of(
            "head", new ModelPart(Collections.emptyList(), Map.of(
                    "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap())
            )),
                "body", model.torso,
                "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())
        )));
	}

    @Override
    public void renderFirstPersonHand(ItemStack stack,
                                       SlotContext slotContext,
                                       HumanoidArm arm,
                                       PoseStack poseStack,
                                       MultiBufferSource renderTypeBuffer,
                                       PlayerRenderState playerRenderState,
                                       AbstractClientPlayer clientPlayer,
                                       int packedLight) {}

    @Override
    public HumanoidModel<? extends HumanoidRenderState> getModel(ItemStack stack, SlotContext slotContext) {
        return this.humanoidModel;
    }

    @Override
    public ResourceLocation getModelTexture(ItemStack stack, SlotContext slotContext) {
        return TEXTURE;
    }
}