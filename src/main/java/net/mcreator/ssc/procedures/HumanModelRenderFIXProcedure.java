
package net.mcreator.ssc.procedures;

import org.joml.Vector3f;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;

import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.client.event.RenderArmEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.Identifier; 
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;

import net.mcreator.ssc.init.Ssc14ModHumanoidModels;
import net.mcreator.ssc.init.Ssc14ModRenderStateModifiers; // ДОБАВЛЕН ИМПОРТ МОДИФИКАТОРОВ СТЕЙТА

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Collection;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(Dist.CLIENT)
public class HumanModelRenderFIXProcedure {

	public static Collection<Runnable> capes = new ConcurrentLinkedQueue<>();

	@SubscribeEvent
	public static void onPlayerRendered(RenderPlayerEvent.Pre event) {
		// ИСПРАВЛЕНО НА ОСНОВЕ ЭТАЛОНА: Извлекаем игрока через RenderState RenderData
		Player entity = (Player) event.getRenderState().getRenderData(Ssc14ModRenderStateModifiers.LIVING_ENTITY);
		AvatarRenderer renderer = (AvatarRenderer) event.getRenderer();
		EntityModel<?> entityModel = (EntityModel<?>) renderer.getModel();

		if (entityModel instanceof PlayerModel playerModel) {
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

	@SubscribeEvent
	public static void onArmRendered(RenderArmEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null || mc.getEntityRenderDispatcher() == null) return;

		if (mc.player.isSpectator()) return;

		event.setCanceled(true);

		var renderer = mc.getEntityRenderDispatcher().getRenderer(mc.player);
		if (!(renderer instanceof AvatarRenderer avatarRenderer)) return;

		PlayerModel vanillaModel = (PlayerModel) avatarRenderer.getModel();
		PlayerModel customModel = Ssc14ModHumanoidModels.HUMAN_MODEL;

		boolean isLeft = event.getArm() == HumanoidArm.LEFT;
		ModelPart customArm = isLeft ? customModel.leftArm : customModel.rightArm;

		PoseStack poseStack = event.getPoseStack();
		poseStack.pushPose();

		customArm.resetPose();
		if (isLeft) vanillaModel.leftArm.resetPose(); else vanillaModel.rightArm.resetPose();

		customArm.loadPose(isLeft ? vanillaModel.leftArm.storePose() : vanillaModel.rightArm.storePose());

		boolean armVisible = customArm.skipDraw;
		customArm.skipDraw = false;

		Identifier texture = Identifier.fromNamespaceAndPath("ssc_14", "textures/entities/human_m_texture.png");
		VertexConsumer buffer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderTypes.armorCutoutNoCull(texture));
		int packedLight = event.getPackedLight();

		customArm.render(poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);

		customArm.skipDraw = armVisible;
		poseStack.popPose();
	}
	public static void renderHumanoid(RenderPlayerEvent playerRenderEvent, PlayerModel model, VertexConsumer vertexConsumer) {
		PoseStack poseStack = playerRenderEvent.getPoseStack();
		poseStack.pushPose();

		poseStack.scale(-0.938f, -0.938f, 0.938f);
		poseStack.translate(0.0D, -1.501, 0.0D);
		
		if (!capes.isEmpty()) {
			capes.forEach(cape -> cape.run());
			capes.clear();
		}
		
		// ИСПРАВЛЕНО НА ОСНОВЕ ЭТАЛОНА: Извлекаем LivingEntity и его запрятанное поле освещения из стейта
		var state = playerRenderEvent.getRenderState();
		int lightCoords = state.lightCoords;
		
		model.renderToBuffer(poseStack, vertexConsumer, lightCoords, OverlayTexture.NO_OVERLAY);
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

	private static void executeBodyRender(Entity entity, EntityModel<?> entityModel, RenderPlayerEvent playerRenderEvent, PoseStack poseStack) {
		if (entity == null || entityModel == null || playerRenderEvent == null || poseStack == null)
			return;

		double model_scale = entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.SCALE)
				? _livingEntity0.getAttribute(Attributes.SCALE).getValue() : 1.0;
		double clothes_scale = 1.0 / (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.SCALE)
				? _livingEntity1.getAttribute(Attributes.SCALE).getValue() : 1.0);

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
				hasHardsuitBody = hasHardsuitInSlot(player, "outerwear",
						"ssc14:hardsuits_body", "ssc_14:hardsuits_body",
						"ssc_14:hardsuit_salvage", "ssc14:hardsuit_salvage");
				hasHardsuitHelmet = hasHardsuitInSlot(player, "headdress",
						"ssc14:hardsuits_helmets", "ssc_14:hardsuits_helmets",
						"ssc_14:hardsuit_salvage_helmet", "ssc14:hardsuit_salvage_helmet");
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
				Identifier texture = Identifier.fromNamespaceAndPath("ssc_14", "textures/entities/human_m_texture.png");
				renderHumanoid(playerRenderEvent, Ssc14ModHumanoidModels.HUMAN_MODEL, Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderTypes.armorCutoutNoCull(texture)));
			}
			poseStack.scale((float) clothes_scale, (float) clothes_scale, (float) clothes_scale);
		}
	}
}
