
package net.mcreator.ssc.procedures;

import org.joml.Vector3f;

import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.ItemTags;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.core.registries.BuiltInRegistries;

import net.mcreator.ssc.init.Ssc14ModRenderStateModifiers;
import net.mcreator.ssc.init.Ssc14ModHumanoidModels;

import javax.annotation.Nullable;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Collection;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(Dist.CLIENT)
public class HumanModelRenderProcedure {
	@SubscribeEvent
	public static void onPlayerRendered(RenderPlayerEvent.Pre event) {
		Entity entity = (Entity) event.getRenderState().getRenderData(Ssc14ModRenderStateModifiers.LIVING_ENTITY);
		execute(event, entity, (EntityModel) event.getRenderer().getModel(), event, event.getPoseStack());
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
		Entity dataEntity = state.getRenderData(Ssc14ModRenderStateModifiers.LIVING_ENTITY);
		if (dataEntity == null) return;

		PoseStack poseStack = playerRenderEvent.getPoseStack();
		poseStack.pushPose();
		CompoundTag playerData = dataEntity.getPersistentData();
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

	private static boolean hasHardsuitInSlot(Player player, String slotId, String tagMain, String tagAlt, String itemMain, String itemAlt) {
		var invOpt = CuriosApi.getCuriosInventory(player);
		if (invOpt.isEmpty()) return false;
		var handler = invOpt.get().getStacksHandler(slotId);
		if (handler.isEmpty()) return false;
		var stacks = handler.get().getStacks();
		for (int i = 0; i < stacks.getSlots(); i++) {
			ItemStack stack = stacks.getStackInSlot(i);
			if (stack.isEmpty()) continue;
			ResourceLocation loc = BuiltInRegistries.ITEM.getKey(stack.getItem());
			if (stack.is(ItemTags.create(ResourceLocation.parse(tagMain)))
					|| stack.is(ItemTags.create(ResourceLocation.parse(tagAlt)))
					|| loc.toString().equals(itemMain)
					|| loc.toString().equals(itemAlt)) {
				return true;
			}
		}
		return false;
	}

	public static void execute(Entity entity, EntityModel entityModel, RenderPlayerEvent playerRenderEvent, PoseStack poseStack) {
		execute(null, entity, entityModel, playerRenderEvent, poseStack);
	}

	private static void execute(@Nullable Event event, Entity entity, EntityModel entityModel, RenderPlayerEvent playerRenderEvent, PoseStack poseStack) {
		if (entity == null || entityModel == null || playerRenderEvent == null || poseStack == null)
			return;

		double model_scale = 1;
		if (entity instanceof LivingEntity livingEntity && livingEntity.getAttributes().hasAttribute(Attributes.SCALE)) {
			model_scale = livingEntity.getAttribute(Attributes.SCALE).getValue();
		}
		if (!(model_scale > 0)) {
			model_scale = 1;
		}

		if (!entity.isInvisible()) {
			poseStack.pushPose();
			poseStack.scale((float) model_scale, (float) model_scale, (float) model_scale);

			Ssc14ModHumanoidModels.HUMAN_MODEL.body.skipDraw = false;
			Ssc14ModHumanoidModels.HUMAN_MODEL.jacket.skipDraw = false;
			Ssc14ModHumanoidModels.HUMAN_MODEL.leftArm.skipDraw = false;
			Ssc14ModHumanoidModels.HUMAN_MODEL.rightArm.skipDraw = false;
			Ssc14ModHumanoidModels.HUMAN_MODEL.leftSleeve.skipDraw = false;
			Ssc14ModHumanoidModels.HUMAN_MODEL.rightSleeve.skipDraw = false;
			Ssc14ModHumanoidModels.HUMAN_MODEL.leftLeg.skipDraw = false;
			Ssc14ModHumanoidModels.HUMAN_MODEL.rightLeg.skipDraw = false;
			Ssc14ModHumanoidModels.HUMAN_MODEL.leftPants.skipDraw = false;
			Ssc14ModHumanoidModels.HUMAN_MODEL.rightPants.skipDraw = false;
			Ssc14ModHumanoidModels.HUMAN_MODEL.head.skipDraw = false;
			Ssc14ModHumanoidModels.HUMAN_MODEL.head.getChild("head").skipDraw = false;
			Ssc14ModHumanoidModels.HUMAN_MODEL.hat.skipDraw = false;

			boolean hasHardsuitBody = false;
			boolean hasHardsuitHelmet = false;

			if (entity instanceof Player player) {
				hasHardsuitBody = hasHardsuitInSlot(player, "outerwear",
						"ssc14:hardsuits_body", "ssc_14:hardsuits_body",
						"ssc_14:hardsuit_salvage", "ssc14:hardsuit_salvage");
				hasHardsuitHelmet = hasHardsuitInSlot(player, "headdress",
						"ssc14:hardsuits_helmets", "ssc_14:hardsuits_helmets",
						"ssc_14:hardsuit_salvage_helmet", "ssc14:hardsuit_salvage_helmet");
			}

			if (hasHardsuitBody) {
				Ssc14ModHumanoidModels.HUMAN_MODEL.body.skipDraw = true;
				Ssc14ModHumanoidModels.HUMAN_MODEL.jacket.skipDraw = true;
				Ssc14ModHumanoidModels.HUMAN_MODEL.leftArm.skipDraw = true;
				Ssc14ModHumanoidModels.HUMAN_MODEL.rightArm.skipDraw = true;
				Ssc14ModHumanoidModels.HUMAN_MODEL.leftSleeve.skipDraw = true;
				Ssc14ModHumanoidModels.HUMAN_MODEL.rightSleeve.skipDraw = true;
				Ssc14ModHumanoidModels.HUMAN_MODEL.leftLeg.skipDraw = true;
				Ssc14ModHumanoidModels.HUMAN_MODEL.rightLeg.skipDraw = true;
				Ssc14ModHumanoidModels.HUMAN_MODEL.leftPants.skipDraw = true;
				Ssc14ModHumanoidModels.HUMAN_MODEL.rightPants.skipDraw = true;
			}

			if (hasHardsuitHelmet) {
				Ssc14ModHumanoidModels.HUMAN_MODEL.head.skipDraw = true;
				Ssc14ModHumanoidModels.HUMAN_MODEL.head.getChild("head").skipDraw = true;
				Ssc14ModHumanoidModels.HUMAN_MODEL.hat.skipDraw = true;
			}

			ResourceLocation texture = ResourceLocation.fromNamespaceAndPath("ssc_14", "textures/entities/human_m_texture.png");
			renderHumanoid(playerRenderEvent, Ssc14ModHumanoidModels.HUMAN_MODEL,
					playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)),
					playerRenderEvent.getRenderState());

			poseStack.popPose();
		}

		((PlayerModel) entityModel).body.skipDraw = true;
		((PlayerModel) entityModel).hat.skipDraw = true;
		((PlayerModel) entityModel).head.skipDraw = true;
		((PlayerModel) entityModel).jacket.skipDraw = true;
		((PlayerModel) entityModel).leftArm.skipDraw = true;
		((PlayerModel) entityModel).leftLeg.skipDraw = true;
		((PlayerModel) entityModel).leftPants.skipDraw = true;
		((PlayerModel) entityModel).leftSleeve.skipDraw = true;
		((PlayerModel) entityModel).rightArm.skipDraw = true;
		((PlayerModel) entityModel).rightLeg.skipDraw = true;
		((PlayerModel) entityModel).rightPants.skipDraw = true;
		((PlayerModel) entityModel).rightSleeve.skipDraw = true;
	}
}
