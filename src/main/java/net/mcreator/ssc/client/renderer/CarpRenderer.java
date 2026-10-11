package net.mcreator.ssc.client.renderer;

import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.util.context.ContextKey;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.Minecraft;

import net.mcreator.ssc.entity.CarpEntity;
import net.mcreator.ssc.client.model.animations.carpAnimation;
import net.mcreator.ssc.client.model.Modelcarp;

import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;

public class CarpRenderer extends MobRenderer<CarpEntity, LivingEntityRenderState, Modelcarp> {
	private final Identifier entityTexture = Identifier.parse("ssc_14:textures/entities/gray_carp_texture.png");

	public CarpRenderer(EntityRendererProvider.Context context) {
		super(context, new AnimatedModel(context.bakeLayer(Modelcarp.LAYER_LOCATION)), 0.4f);
		this.addLayer(new RenderLayer<>(this) {
			final Identifier LAYER_TEXTURE = Identifier.parse("ssc_14:textures/entities/carp_glowing_teeth_texture.png");
			final RenderType RENDER_TYPE = RenderTypes.eyes(LAYER_TEXTURE);
			final EntityModel LAYER_MODEL = new Modelcarp(Minecraft.getInstance().getEntityModels().bakeLayer(Modelcarp.LAYER_LOCATION));

			@Override
			public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, LivingEntityRenderState state, float headYaw, float headPitch) {
				LAYER_MODEL.setupAnim(state);
				submitNodeCollector.submitModel(LAYER_MODEL, state, poseStack, RENDER_TYPE, light, LivingEntityRenderer.getOverlayCoords(state, 0), state.outlineColor, null);
			}
		});
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void extractRenderState(CarpEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState state) {
		return entityTexture;
	}

	private static final class AnimatedModel extends Modelcarp {
		private final KeyframeAnimation keyframeAnimation0;

		public AnimatedModel(ModelPart root) {
			super(root);
			this.keyframeAnimation0 = safeBake(carpAnimation.walk_animation);
		}

		private KeyframeAnimation safeBake(AnimationDefinition source) {
			try {
				return source.bake(root);
			} catch (IllegalArgumentException e) {
				return new AnimationDefinition(0, false, Map.of()).bake(root);
			}
		}

		@Override
		public void setupAnim(LivingEntityRenderState state) {
			this.root().getAllParts().forEach(ModelPart::resetPose);
			CarpEntity entity = state.getRenderData(ENTITY_KEY);
			this.keyframeAnimation0.apply(entity.animationState0, state.ageInTicks, 1f);
			super.setupAnim(state);
		}
	}

	public static final ContextKey<CarpEntity> ENTITY_KEY = new ContextKey<>(Identifier.parse("ssc_14:carp_entity"));

	@EventBusSubscriber(Dist.CLIENT)
	public static class EntityStateAdder {
		@SubscribeEvent
		private static void registerRenderStateModifiersEvent(RegisterRenderStateModifiersEvent event) {
			event.registerEntityModifier(CarpRenderer.class, (entity, state) -> state.setRenderData(ENTITY_KEY, entity));
		}
	}
}