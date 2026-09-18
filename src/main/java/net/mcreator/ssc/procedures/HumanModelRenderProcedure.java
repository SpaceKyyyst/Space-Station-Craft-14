
package net.mcreator.ssc.procedures;

import org.joml.Vector3f;

import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.Identifier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;

import net.mcreator.ssc.init.Ssc14ModRenderStateModifiers;
import net.mcreator.ssc.init.Ssc14ModHumanoidModels;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Collection;

import com.mojang.blaze3d.vertex.PoseStack;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(Dist.CLIENT)
public class HumanModelRenderProcedure {

	@SubscribeEvent
	public static void onPlayerRendered(RenderPlayerEvent.Pre event) {
		// ИСПРАВЛЕНО НА ОСНОВЕ ПУСТЫШКИ: Достаем сущность игрока из стейта рендера
		Entity entity = (Entity) event.getRenderState().getRenderData(Ssc14ModRenderStateModifiers.LIVING_ENTITY);
		execute(event, entity, (EntityModel) event.getRenderer().getModel(), event.getPoseStack());
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
	public static void renderHumanoid(RenderPlayerEvent playerRenderEvent, PlayerModel model, RenderType renderType, AvatarRenderState state) {
		LivingEntity eventEntity_ = (LivingEntity) playerRenderEvent.getRenderState().getRenderData(Ssc14ModRenderStateModifiers.LIVING_ENTITY);
		PoseStack poseStack = playerRenderEvent.getPoseStack();
		poseStack.pushPose();
		
		// ИСПРАВЛЕНО ПОД 1.21.4: Достаем NBT игрока через стейт рендера
		CompoundTag playerData = state.getRenderData(Ssc14ModRenderStateModifiers.LIVING_ENTITY).getPersistentData();
		float oldAnimationProgress = 0;
		float oldAgeInTicks = 0;
		if (playerData.contains("PlayerAnimationProgress")) {
			// ИСПРАВЛЕНО ПОД NEW NBT API: Чтение float через getFloatOr
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
		if (eventEntity_.hasPose(Pose.SLEEPING)) {
			Direction direction = eventEntity_.getBedOrientation();
			if (direction != null) {
				float eyeHeightOffset = eventEntity_.getEyeHeight(Pose.STANDING) - 0.1F;
				poseStack.translate((float) (-direction.getStepX()) * eyeHeightOffset, 0.0F, (float) (-direction.getStepZ()) * eyeHeightOffset);
			}
		}
		
		// Настраиваем углы вращения на основе стейта
		playerRenderEvent.getRenderer().setupRotations(state, poseStack, state.bodyRot, 0);
		poseStack.scale(-0.938f, -0.938f, 0.938f);
		poseStack.translate(0.0D, -1.501, 0.0D);
		Vector3f offset = new Vector3f(0.015f);
		offsetScale(model, offset);
		if (!capes.isEmpty()) {
			capes.forEach(cape -> cape.run());
			capes.clear();
		}
		
		// Отрисовываем буфер
		model.renderToBuffer(poseStack, Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(renderType), state.lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0));
		offset.negate();
		offsetScale(model, offset);
		poseStack.popPose();
	}

	private static boolean hasHardsuitInSlot(Player player, String slotId, String tagMain, String tagAlt, String itemMain, String itemAlt) {
		var invOpt = CuriosApi.getCuriosInventory(player);
		if (invOpt.isEmpty()) return false;
		var handler = invOpt.get().getStacksHandler(slotId);
		if (handler.isEmpty()) return false;
		
		net.neoforged.neoforge.items.IItemHandlerModifiable stacks = handler.get().getStacks();
		if (stacks == null) return false;
		
		int slotsCount = stacks.getSlots();
		for (int i = 0; slotsCount > i; i++) {
			ItemStack stack = stacks.getStackInSlot(i);
			if (stack.isEmpty()) continue;
			Identifier loc = BuiltInRegistries.ITEM.getKey(stack.getItem());
			if (loc == null) continue;
			if (stack.is(ItemTags.create(Identifier.parse(tagMain)))
					|| stack.is(ItemTags.create(Identifier.parse(tagAlt)))
					|| loc.toString().equals(itemMain)
					|| loc.toString().equals(itemAlt)) {
				return true;
			}
		}
		return false;
	}

	public static void execute(RenderPlayerEvent playerRenderEvent, Entity entity, EntityModel entityModel, PoseStack poseStack) {
		if (entity == null || entityModel == null || playerRenderEvent == null || poseStack == null)
			return;
		double model_scale = entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.SCALE) ? _livingEntity0.getAttribute(Attributes.SCALE).getValue() : 1.0;
		double clothes_scale = 1.0 / (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.SCALE) ? _livingEntity1.getAttribute(Attributes.SCALE).getValue() : 1.0);
		
		if (!entity.isInvisible()) {
			PlayerModel humanModel = Ssc14ModHumanoidModels.HUMAN_MODEL;

			humanModel.body.skipDraw = false;
			humanModel.jacket.skipDraw = false;
			humanModel.leftArm.skipDraw = false;
			humanModel.rightArm.skipDraw = false;
			humanModel.leftSleeve.skipDraw = false;
			humanModel.rightSleeve.skipDraw = false;
			humanModel.leftLeg.skipDraw = false;
			humanModel.rightLeg.skipDraw = false;
			humanModel.leftPants.skipDraw = false;
			humanModel.rightPants.skipDraw = false;
			humanModel.head.skipDraw = false;
			humanModel.hat.skipDraw = false;

			boolean hasHardsuitBody = false;
			boolean hasHardsuitHelmet = false;

			if (entity instanceof Player player) {
				hasHardsuitBody = hasHardsuitInSlot(player, "outerwear", "ssc14:hardsuits_body", "ssc_14:hardsuits_body", "ssc_14:hardsuit_salvage", "ssc14:hardsuit_salvage");
				hasHardsuitHelmet = hasHardsuitInSlot(player, "headdress", "ssc14:hardsuits_helmets", "ssc_14:hardsuits_helmets", "ssc_14:hardsuit_salvage_helmet", "ssc14:hardsuit_salvage_helmet");
			}

			if (hasHardsuitBody) {
				humanModel.body.skipDraw = true;
				humanModel.jacket.skipDraw = true;
				humanModel.leftArm.skipDraw = true;
				humanModel.rightArm.skipDraw = true;
				humanModel.leftSleeve.skipDraw = true;
				humanModel.rightSleeve.skipDraw = true;
				humanModel.leftLeg.skipDraw = true;
				humanModel.rightLeg.skipDraw = true;
				humanModel.leftPants.skipDraw = true;
				humanModel.rightPants.skipDraw = true;
			}

			if (hasHardsuitHelmet) {
				humanModel.head.skipDraw = true;
				humanModel.hat.skipDraw = true;
			}

			poseStack.scale((float) model_scale, (float) model_scale, (float) model_scale);
			{
				Identifier texture = (Identifier.fromNamespaceAndPath("ssc_14", "textures/entities/human_m_texture.png"));
				renderHumanoid(playerRenderEvent, Ssc14ModHumanoidModels.HUMAN_MODEL, RenderTypes.armorCutoutNoCull(texture), (AvatarRenderState) playerRenderEvent.getRenderState());
			}
			poseStack.scale((float) clothes_scale, (float) clothes_scale, (float) clothes_scale);
		}
		
		// Скрываем части оригинальной ванильной модели
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
