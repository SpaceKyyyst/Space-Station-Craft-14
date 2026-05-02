package net.mcreator.ssc.procedures;

import org.joml.Vector3f;

import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.EntityModel;

import net.mcreator.ssc.init.Ssc14ModRenderStateModifiers;
import net.mcreator.ssc.init.Ssc14ModHumanoidModels;

import javax.annotation.Nullable;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Collection;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

@EventBusSubscriber(Dist.CLIENT)
public class HumanModelRenderProcedure {
	@SubscribeEvent
	public static void onPlayerRendered(RenderPlayerEvent.Pre event) {
		Entity entity = (Entity) event.getRenderState().getRenderData(Ssc14ModRenderStateModifiers.LIVING_ENTITY);
		execute(event, entity, (EntityModel) event.getRenderer().getModel(), event);
	}

	public static Collection<Runnable> capes = new ConcurrentLinkedQueue<>();

	public static void offsetScale(PlayerModel model, Vector3f offset) {
		model.head.offsetScale(offset);
		model.head.y += offset.x() > 0 ? 0.05 : -0.05;
		model.body.offsetScale(offset);
		model.leftArm.offsetScale(offset);
		model.rightArm.offsetScale(offset);
		model.leftLeg.offsetScale(offset);
		model.rightLeg.offsetScale(offset);
		model.hat.offsetScale(offset);
		model.hat.y += offset.x() > 0 ? 0.05 : -0.05;
		model.jacket.offsetScale(offset);
		model.leftSleeve.offsetScale(offset);
		model.rightSleeve.offsetScale(offset);
		model.leftPants.offsetScale(offset);
		model.rightPants.offsetScale(offset);
	}

	public static void renderHumanoid(RenderPlayerEvent playerRenderEvent, PlayerModel model, VertexConsumer vertexConsumer, PlayerRenderState state) {
		PoseStack poseStack = playerRenderEvent.getPoseStack();
		poseStack.pushPose();
		CompoundTag playerData = state.getRenderData(Ssc14ModRenderStateModifiers.LIVING_ENTITY).getPersistentData();
		float oldAnimationProgress = 0;
		float oldAgeInTicks = 0;
		if (playerData.contains("PlayerAnimationProgress")) {
			oldAnimationProgress = playerData.getFloatOr("PlayerAnimationProgress", 0);
			oldAgeInTicks = playerData.getFloatOr("LastTickTime", 0);
		}
		model.setupAnim(state);
		if (playerData.contains("PlayerAnimationProgress") && playerData.getFloatOr("PlayerAnimationProgress", 0) > 0) {
			playerData.putFloat("PlayerAnimationProgress", oldAnimationProgress);
			playerData.putFloat("LastTickTime", oldAgeInTicks);
		} else if (oldAnimationProgress > 0) {
			model.setupAnim(state);
		}
		playerRenderEvent.getRenderer().setupRotations(state, poseStack, state.bodyRot, 0);
		poseStack.scale(-0.938f, -0.938f, 0.938f);
		poseStack.translate(0.0D, -1.501, 0.0D);
		Vector3f offset = new Vector3f(0.015f);
		offsetScale(model, offset);
		if (!capes.isEmpty()) {
			capes.forEach(cape -> cape.run());
			capes.clear();
		}
		model.renderToBuffer(poseStack, vertexConsumer, playerRenderEvent.getPackedLight(), LivingEntityRenderer.getOverlayCoords(state, 0));
		offset.negate();
		offsetScale(model, offset);
		poseStack.popPose();
	}

	public static void renderEntity(RenderPlayerEvent playerRenderEvent, EntityModel model, VertexConsumer vertexConsumer, LivingEntityRenderState state) {
		PoseStack poseStack = playerRenderEvent.getPoseStack();
		poseStack.pushPose();
		playerRenderEvent.getRenderer().setupRotations((PlayerRenderState) state, poseStack, state.bodyRot, 0);
		poseStack.scale(-0.938f, -0.938f, 0.938f);
		poseStack.translate(0.0D, -1.501, 0.0D);
		model.setupAnim(state);
		model.renderToBuffer(poseStack, vertexConsumer, playerRenderEvent.getPackedLight(), LivingEntityRenderer.getOverlayCoords(state, 0));
		poseStack.popPose();
	}

	public static void execute(Entity entity, EntityModel entityModel, RenderPlayerEvent playerRenderEvent) {
		execute(null, entity, entityModel, playerRenderEvent);
	}

	private static void execute(@Nullable Event event, Entity entity, EntityModel entityModel, RenderPlayerEvent playerRenderEvent) {
		if (entity == null || entityModel == null || playerRenderEvent == null)
			return;
		if (!entity.isInvisible()) {
			{
				ResourceLocation texture = (ResourceLocation.fromNamespaceAndPath("ssc_14", "textures/entities/human_m_texture.png"));
				renderHumanoid(playerRenderEvent, Ssc14ModHumanoidModels.HUMAN_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)), playerRenderEvent.getRenderState());
			}
		}
		((PlayerModel) entityModel).body.skipDraw = !(false);
		((PlayerModel) entityModel).hat.skipDraw = !(false);
		((PlayerModel) entityModel).head.skipDraw = !(false);
		((PlayerModel) entityModel).jacket.skipDraw = !(false);
		((PlayerModel) entityModel).leftArm.skipDraw = !(false);
		((PlayerModel) entityModel).leftLeg.skipDraw = !(false);
		((PlayerModel) entityModel).leftPants.skipDraw = !(false);
		((PlayerModel) entityModel).leftSleeve.skipDraw = !(false);
		((PlayerModel) entityModel).rightArm.skipDraw = !(false);
		((PlayerModel) entityModel).rightLeg.skipDraw = !(false);
		((PlayerModel) entityModel).rightPants.skipDraw = !(false);
		((PlayerModel) entityModel).rightSleeve.skipDraw = !(false);
	}
}