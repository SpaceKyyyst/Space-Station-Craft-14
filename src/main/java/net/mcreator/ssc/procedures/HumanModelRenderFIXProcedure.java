
package net.mcreator.ssc.procedures;

import org.joml.Vector3f;

import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.client.event.RenderArmEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.Minecraft;

import net.mcreator.ssc.init.Ssc14ModRenderStateModifiers;
import net.mcreator.ssc.init.Ssc14ModHumanoidModels;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Collection;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

@EventBusSubscriber(Dist.CLIENT)
public class HumanModelRenderFIXProcedure {

	public static Collection<Runnable> capes = new ConcurrentLinkedQueue<>();

	// ==========================================
	// 1. Рендер тела (Вид от 3-го лица)
	// ==========================================
	@SubscribeEvent
	public static void onPlayerRendered(RenderPlayerEvent.Pre event) {
		Entity entity = (Entity) event.getRenderState().getRenderData(Ssc14ModRenderStateModifiers.LIVING_ENTITY);
		EntityModel<?> entityModel = event.getRenderer().getModel();
		
		if (entityModel instanceof PlayerModel playerModel) {
			// Принудительно скрываем все части ванильной модели
			playerModel.head.skipDraw = true;
			playerModel.hat.skipDraw = true;
			playerModel.body.skipDraw = true;
			playerModel.jacket.skipDraw = true;
			playerModel.leftArm.skipDraw = true;
			playerModel.rightArm.skipDraw = true;
			playerModel.leftSleeve.skipDraw = true;
			playerModel.rightSleeve.skipDraw = true;
			playerModel.leftLeg.skipDraw = true;
			playerModel.rightLeg.skipDraw = true;
			playerModel.leftPants.skipDraw = true;
			playerModel.rightPants.skipDraw = true;
		}

		executeBodyRender(entity, entityModel, event, event.getPoseStack());
	}

	// ==========================================
	// 2. Рендер руки (Вид от 1-го лица) - ИСПРАВЛЕНИЕ БАГА
	// ==========================================
	@SubscribeEvent
	public static void onArmRendered(RenderArmEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null || mc.getEntityRenderDispatcher() == null) return;

		// ИСПРАВЛЕНИЕ 1: Если игрок в спектаторе, не трогаем рендер руки.
		// Мы выходим ДО отмены события, чтобы ванильный рендер спектатора работал нормально.
		if (mc.player.isSpectator()) {
			return; 
		}

		// Отменяем ванильный рендер руки, чтобы предотвратить баг и наложение
		event.setCanceled(true);

		var renderer = mc.getEntityRenderDispatcher().getRenderer(mc.player);
		if (!(renderer instanceof PlayerRenderer playerRenderer)) return;

		PlayerModel vanillaModel = (PlayerModel) playerRenderer.getModel();
		PlayerModel customModel = Ssc14ModHumanoidModels.HUMAN_MODEL;

		boolean isLeft = event.getArm() == HumanoidArm.LEFT;
		
		ModelPart vanillaArm = isLeft ? vanillaModel.leftArm : vanillaModel.rightArm;
		ModelPart vanillaSleeve = isLeft ? vanillaModel.leftSleeve : vanillaModel.rightSleeve;
		
		ModelPart customArm = isLeft ? customModel.leftArm : customModel.rightArm;
		ModelPart customSleeve = isLeft ? customModel.leftSleeve : customModel.rightSleeve;

		PoseStack poseStack = event.getPoseStack();
		poseStack.pushPose();

		// ИСПРАВЛЕНИЕ 2: Сбрасываем повороты!
		// Так как модели в Майнкрафте общие (shared) для всех игроков, 
		// после рендера других игроков в них остаются повороты их анимаций (ходьбы).
		// Если их не сбросить, рука от первого лица унаследует чужие анимации и будет махать.
		vanillaArm.resetPose();
		vanillaSleeve.resetPose();
		customArm.resetPose();
		customSleeve.resetPose();

		// Копируем сбросленные повороты
		customArm.copyFrom(vanillaArm);
		customSleeve.copyFrom(vanillaSleeve);

		// Временно разрешаем отрисовку наших частей
		boolean armVisible = customArm.skipDraw;
		boolean sleeveVisible = customSleeve.skipDraw;
		customArm.skipDraw = false;
		customSleeve.skipDraw = false;

		ResourceLocation texture = ResourceLocation.fromNamespaceAndPath("ssc_14", "textures/entities/human_m_texture.png");
		VertexConsumer buffer = event.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture));
		int packedLight = event.getPackedLight();

		// Рендерим руку и рукав
		customArm.render(poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
		customSleeve.render(poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);

		// Возвращаем исходное состояние skipDraw
		customArm.skipDraw = armVisible;
		customSleeve.skipDraw = sleeveVisible;

		poseStack.popPose();
	}

	// ==========================================
	// 3. Вспомогательные методы (без изменений)
	// ==========================================
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

	@SuppressWarnings("unchecked")
	public static void renderEntity(RenderPlayerEvent playerRenderEvent, EntityModel<?> model, VertexConsumer vertexConsumer, LivingEntityRenderState state) {
		PoseStack poseStack = playerRenderEvent.getPoseStack();
		poseStack.pushPose();
		playerRenderEvent.getRenderer().setupRotations((PlayerRenderState) state, poseStack, state.bodyRot, 0);
		poseStack.scale(-0.938f, -0.938f, 0.938f);
		poseStack.translate(0.0D, -1.501, 0.0D);
		((EntityModel<LivingEntityRenderState>) model).setupAnim(state);
		model.renderToBuffer(poseStack, vertexConsumer, playerRenderEvent.getPackedLight(), LivingEntityRenderer.getOverlayCoords(state, 0));
		poseStack.popPose();
	}

	private static void executeBodyRender(Entity entity, EntityModel<?> entityModel, RenderPlayerEvent playerRenderEvent, PoseStack poseStack) {
		if (entity == null || entityModel == null || playerRenderEvent == null || poseStack == null)
			return;
			
		double model_scale = entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.SCALE) 
				? _livingEntity0.getAttribute(Attributes.SCALE).getValue() : 1.0;
		double clothes_scale = 1.0 / (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.SCALE) 
				? _livingEntity1.getAttribute(Attributes.SCALE).getValue() : 1.0);

		if (!entity.isInvisible()) {
			poseStack.scale((float) model_scale, (float) model_scale, (float) model_scale);
			{
				ResourceLocation texture = ResourceLocation.fromNamespaceAndPath("ssc_14", "textures/entities/human_m_texture.png");
				renderHumanoid(playerRenderEvent, Ssc14ModHumanoidModels.HUMAN_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)), playerRenderEvent.getRenderState());
			}
			poseStack.scale((float) clothes_scale, (float) clothes_scale, (float) clothes_scale);
		}
	}
}
