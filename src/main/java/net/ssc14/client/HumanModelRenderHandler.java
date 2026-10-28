
package net.ssc14.client;

import org.joml.Vector3f;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.client.event.RenderArmEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.Identifier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.ssc14.client.Ssc14RenderStateModifiers;
import net.mcreator.ssc.init.Ssc14ModHumanoidModels;
import net.ssc14.content.attribute.Ssc14Attributes;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Collection;

import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(Dist.CLIENT)
public final class HumanModelRenderHandler {

	private HumanModelRenderHandler() {
	}

	public static Collection<Runnable> capes = new ConcurrentLinkedQueue<>();

	@SubscribeEvent
	public static void onPlayerRendered(RenderPlayerEvent.Pre event) {
		Entity entity = (Entity) event.getRenderState().getRenderData(Ssc14RenderStateModifiers.LIVING_ENTITY);
		execute(event, entity, (EntityModel) event.getRenderer().getModel(), event.getPoseStack());
	}

	@SubscribeEvent
	public static void onArmRendered(RenderArmEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null || mc.getEntityRenderDispatcher() == null) return;
		if (mc.player.isSpectator()) return;

		event.setCanceled(true);

		var renderer = mc.getEntityRenderDispatcher().getRenderer(mc.player);
		if (!(renderer instanceof AvatarRenderer avatarRenderer)) return;

		PlayerModel vanillaModel = (PlayerModel) avatarRenderer.getModel();
		
		int raceId = 0;
		if (mc.player.getAttributes().hasAttribute(Ssc14Attributes.RACE)) {
			raceId = (int) mc.player.getAttribute(Ssc14Attributes.RACE).getBaseValue();
		}

		PlayerModel customModel = switch (raceId) {
			case 1 -> Ssc14ModHumanoidModels.UNATH_MODEL;
			case 2 -> Ssc14ModHumanoidModels.FELINID_MODEL;
			default -> Ssc14ModHumanoidModels.HUMAN_MODEL;
		};
		
		Identifier texture = switch (raceId) {
			case 1 -> Identifier.fromNamespaceAndPath("ssc_14", "textures/entities/unath_m_texture.png");
			case 2 -> Identifier.fromNamespaceAndPath("ssc_14", "textures/entities/felinid_m_texture.png");
			case 3 -> Identifier.fromNamespaceAndPath("ssc_14", "textures/entities/slimeman_m_texture.png");
			default -> Identifier.fromNamespaceAndPath("ssc_14", "textures/entities/human_m_texture.png");
		};

		boolean isLeft = event.getArm() == HumanoidArm.LEFT;
		ModelPart customArm = isLeft ? customModel.leftArm : customModel.rightArm;

		PoseStack poseStack = event.getPoseStack();
		poseStack.pushPose();

		if (raceId == 4) {
			poseStack.scale(1.0f, 0.80f, 1.0f);
		}

		customArm.resetPose();
		if (isLeft) vanillaModel.leftArm.resetPose(); else vanillaModel.rightArm.resetPose();

		customArm.loadPose(isLeft ? vanillaModel.leftArm.storePose() : vanillaModel.rightArm.storePose());

		boolean armVisible = customArm.skipDraw;
		customArm.skipDraw = false;

		RenderType armRenderType = (raceId == 3) 
			? RenderTypes.entityTranslucent(texture) 
			: RenderTypes.armorCutoutNoCull(texture);

		VertexConsumer buffer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(armRenderType);
		customArm.render(poseStack, buffer, event.getPackedLight(), OverlayTexture.NO_OVERLAY);

		customArm.skipDraw = armVisible;
		poseStack.popPose();
	}
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
		LivingEntity eventEntity_ = (LivingEntity) playerRenderEvent.getRenderState().getRenderData(Ssc14RenderStateModifiers.LIVING_ENTITY);
		PoseStack poseStack = playerRenderEvent.getPoseStack();
		poseStack.pushPose();
		
		CompoundTag playerData = state.getRenderData(Ssc14RenderStateModifiers.LIVING_ENTITY).getPersistentData();
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
		
		if (eventEntity_.hasPose(Pose.SLEEPING)) {
			Direction direction = eventEntity_.getBedOrientation();
			if (direction != null) {
				float eyeHeightOffset = eventEntity_.getEyeHeight(Pose.STANDING) - 0.1F;
				poseStack.translate((float) (-direction.getStepX()) * eyeHeightOffset, 0.0F, (float) (-direction.getStepZ()) * eyeHeightOffset);
			}
		}
		
		playerRenderEvent.getRenderer().setupRotations(state, poseStack, state.bodyRot, 0);
		poseStack.scale(-0.938f, -0.938f, 0.938f);
		
		boolean isCrawling = playerData.getBoolean("ssc14_crawl_active").orElse(false); 
		
		if (isCrawling || state.pose == Pose.SWIMMING) {
			poseStack.translate(0.0D, -0.5D, 0.5D); 
			poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(90.0F));
			poseStack.translate(0.0D, -1.0D, 0.0D);
		} else {
			poseStack.translate(0.0D, -1.501, 0.0D);
		}
		
		Vector3f offset = new Vector3f(0.015f);
		offsetScale(model, offset);
		if (!capes.isEmpty()) {
			capes.forEach(cape -> cape.run());
			capes.clear();
		}
		
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
			
		// Вычисляем точные динамические коэффициенты из ванильных атрибутов MCreator
		double model_scale = entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.SCALE) ? _livingEntity0.getAttribute(Attributes.SCALE).getValue() : 1.0;
		double clothes_scale = 1.0 / (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.SCALE) ? _livingEntity1.getAttribute(Attributes.SCALE).getValue() : 1.0);
		
		int raceId = 0;
		if (entity instanceof LivingEntity _livingEntityRace && _livingEntityRace.getAttributes().hasAttribute(Ssc14Attributes.RACE)) {
			raceId = (int) _livingEntityRace.getAttribute(Ssc14Attributes.RACE).getBaseValue();
		}

		PlayerModel activeCustomModel = switch (raceId) {
			case 1 -> Ssc14ModHumanoidModels.UNATH_MODEL;
			case 2 -> Ssc14ModHumanoidModels.FELINID_MODEL;
			default -> Ssc14ModHumanoidModels.HUMAN_MODEL;
		};
		
		Identifier texture = switch (raceId) {
			case 1 -> Identifier.fromNamespaceAndPath("ssc_14", "textures/entities/unath_m_texture.png");
			case 2 -> Identifier.fromNamespaceAndPath("ssc_14", "textures/entities/felinid_m_texture.png");
			case 3 -> Identifier.fromNamespaceAndPath("ssc_14", "textures/entities/slimeman_m_texture.png");
			default -> Identifier.fromNamespaceAndPath("ssc_14", "textures/entities/human_m_texture.png");
		};

		// 🔥 ПАТТЕРН МСРЕАТОР СИНХРОННЫЙ: Ужимаем по оси Y, опираясь строго на динамический model_scale
		if (raceId == 4) {
			poseStack.scale(1.0f, (float) model_scale, 1.0f);
		}

		if (!entity.isInvisible()) {
			activeCustomModel.body.skipDraw = false; activeCustomModel.jacket.skipDraw = false;
			activeCustomModel.leftArm.skipDraw = false; activeCustomModel.rightArm.skipDraw = false;
			activeCustomModel.leftSleeve.skipDraw = false; activeCustomModel.rightSleeve.skipDraw = false;
			activeCustomModel.leftLeg.skipDraw = false; activeCustomModel.rightLeg.skipDraw = false;
			activeCustomModel.leftPants.skipDraw = false; activeCustomModel.rightPants.skipDraw = false;
			activeCustomModel.head.skipDraw = false; activeCustomModel.hat.skipDraw = false;

			boolean hasHardsuitBody = false;
			boolean hasHardsuitHelmet = false;

			if (entity instanceof Player player) {
				hasHardsuitBody = hasHardsuitInSlot(player, "outerwear", "ssc14:hardsuits_body", "ssc_14:hardsuits_body", "ssc_14:hardsuit_salvage", "ssc14:hardsuit_salvage");
				hasHardsuitHelmet = hasHardsuitInSlot(player, "headdress", "ssc14:hardsuits_helmets", "ssc_14:hardsuits_helmets", "ssc_14:hardsuit_salvage_helmet", "ssc14:hardsuit_salvage_helmet");
			}

			if (hasHardsuitBody) {
				activeCustomModel.body.skipDraw = true; activeCustomModel.jacket.skipDraw = true;
				activeCustomModel.leftArm.skipDraw = true; activeCustomModel.rightArm.skipDraw = true;
				activeCustomModel.leftSleeve.skipDraw = true; activeCustomModel.rightSleeve.skipDraw = true;
				activeCustomModel.leftLeg.skipDraw = true; activeCustomModel.rightLeg.skipDraw = true;
				activeCustomModel.leftPants.skipDraw = true; activeCustomModel.rightPants.skipDraw = true;
			}
			if (hasHardsuitHelmet) { activeCustomModel.head.skipDraw = true; activeCustomModel.hat.skipDraw = true; }

			RenderType bodyRenderType = (raceId == 3) ? RenderTypes.entityTranslucent(texture) : RenderTypes.armorCutoutNoCull(texture);

			poseStack.pushPose();
			// Внутри кастомного рендера применяем базовый масштаб
			poseStack.scale((float) model_scale, (float) model_scale, (float) model_scale);
			renderHumanoid(playerRenderEvent, activeCustomModel, bodyRenderType, (AvatarRenderState) playerRenderEvent.getRenderState());
			poseStack.popPose();
		}
		
		if (entityModel instanceof PlayerModel vanillaModel) {
			activeCustomModel.head.loadPose(vanillaModel.head.storePose());
			activeCustomModel.hat.loadPose(vanillaModel.hat.storePose());
			activeCustomModel.body.loadPose(vanillaModel.body.storePose());
			activeCustomModel.leftArm.loadPose(vanillaModel.leftArm.storePose());
			activeCustomModel.rightArm.loadPose(vanillaModel.rightArm.storePose());
			activeCustomModel.leftLeg.loadPose(vanillaModel.leftLeg.storePose());
			activeCustomModel.rightLeg.loadPose(vanillaModel.rightLeg.storePose());
			
			activeCustomModel.jacket.loadPose(vanillaModel.jacket.storePose());
			activeCustomModel.leftSleeve.loadPose(vanillaModel.leftSleeve.storePose());
			activeCustomModel.rightSleeve.loadPose(vanillaModel.rightSleeve.storePose());
			activeCustomModel.leftPants.loadPose(vanillaModel.leftPants.storePose());
			activeCustomModel.rightPants.loadPose(vanillaModel.rightPants.storePose());

			if (entity.getPersistentData().getBoolean("ssc14_crawl_active").orElse(false)) {
				float swing = vanillaModel.leftLeg.xRot; 
				activeCustomModel.head.xRot -= 1.4F; activeCustomModel.hat.xRot = activeCustomModel.head.xRot;
				activeCustomModel.leftArm.xRot = -2.0F + (swing * 0.8F); activeCustomModel.leftArm.zRot = -0.2F;
				activeCustomModel.leftSleeve.xRot = activeCustomModel.leftArm.xRot; activeCustomModel.leftSleeve.zRot = activeCustomModel.leftArm.zRot;
				activeCustomModel.rightArm.xRot = -2.0F - (swing * 0.8F); activeCustomModel.rightArm.zRot = 0.2F;
				activeCustomModel.rightSleeve.xRot = activeCustomModel.rightArm.xRot; activeCustomModel.rightSleeve.zRot = activeCustomModel.rightArm.zRot;
				activeCustomModel.leftLeg.xRot = 0.2F - (swing * 0.8F); activeCustomModel.leftLeg.yRot = -0.3F; activeCustomModel.leftLeg.zRot = -0.2F;
				activeCustomModel.leftPants.xRot = activeCustomModel.leftLeg.xRot; activeCustomModel.leftPants.yRot = activeCustomModel.leftLeg.yRot; activeCustomModel.leftPants.zRot = activeCustomModel.leftLeg.zRot;
				activeCustomModel.rightLeg.xRot = 0.2F + (swing * 0.8F); activeCustomModel.rightLeg.yRot = 0.3F; activeCustomModel.rightLeg.zRot = 0.2F;
				activeCustomModel.rightPants.xRot = activeCustomModel.rightLeg.xRot; activeCustomModel.rightPants.yRot = activeCustomModel.rightLeg.yRot; activeCustomModel.rightPants.zRot = activeCustomModel.rightLeg.zRot;
			}

			vanillaModel.body.skipDraw = true; vanillaModel.hat.skipDraw = true; vanillaModel.head.skipDraw = true;
			vanillaModel.jacket.skipDraw = true; vanillaModel.leftArm.skipDraw = true; vanillaModel.leftLeg.skipDraw = true;
			vanillaModel.leftPants.skipDraw = true; vanillaModel.leftSleeve.skipDraw = true; vanillaModel.rightArm.skipDraw = true;
			vanillaModel.rightLeg.skipDraw = true; vanillaModel.rightPants.skipDraw = true; vanillaModel.rightSleeve.skipDraw = true;
		}

		// 🔥 КОПИЯ КОСТЫЛЯ ИЗ ШАБЛОНА СИНХРОННАЯ: Восстанавливаем матрицу строго по оси Y через clothes_scale
		if (raceId == 4) {
			poseStack.scale(1.0f, (float) clothes_scale, 1.0f);
		}
	}
}
