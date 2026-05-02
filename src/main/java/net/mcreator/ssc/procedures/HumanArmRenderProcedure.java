package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.client.event.RenderArmEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.Minecraft;

import net.mcreator.ssc.init.Ssc14ModHumanoidModels;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

@EventBusSubscriber(Dist.CLIENT)
public class HumanArmRenderProcedure {
	@SubscribeEvent
	public static void onArmRendered(RenderArmEvent event) {
		Minecraft mc = Minecraft.getInstance();
		PlayerRenderer renderer = (PlayerRenderer) mc.getEntityRenderDispatcher().getRenderer(mc.player);
		execute(event, event, (EntityModel) renderer.getModel(), event.getPoseStack());
	}

	public static void execute(RenderArmEvent armRenderEvent, EntityModel entityModel, PoseStack poseStack) {
		execute(null, armRenderEvent, entityModel, poseStack);
	}

	private static void execute(@Nullable Event event, RenderArmEvent armRenderEvent, EntityModel entityModel, PoseStack poseStack) {
		if (armRenderEvent == null || entityModel == null || poseStack == null)
			return;
		{
			ResourceLocation texture = (ResourceLocation.fromNamespaceAndPath("ssc_14", "textures/entities/human_m_texture.png"));
			PlayerModel humanoidModel = Ssc14ModHumanoidModels.HUMAN_MODEL;
			PlayerModel playerOriginal = (PlayerModel) entityModel;
			boolean lefthanded = armRenderEvent.getArm() == HumanoidArm.LEFT;
			ModelPart part = lefthanded ? humanoidModel.leftArm : humanoidModel.rightArm;
			boolean partVisible = part.skipDraw;
			part.resetPose();
			if (lefthanded)
				playerOriginal.leftArm.resetPose();
			else
				playerOriginal.rightArm.resetPose();
			part.copyFrom(lefthanded ? playerOriginal.leftArm : playerOriginal.rightArm);
			part.skipDraw = false;
			part.render(poseStack, armRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)), armRenderEvent.getPackedLight(), OverlayTexture.NO_OVERLAY);
			part.skipDraw = partVisible;
		}
	}
}