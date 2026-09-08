package net.mcreator.ssc.client.renderer;

import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;

import net.mcreator.ssc.entity.FoamDartProjectileEntity;
import net.mcreator.ssc.client.model.Modelfoam_dart;

import com.mojang.math.Axis;
import com.mojang.blaze3d.vertex.PoseStack;

public class FoamDartProjectileRenderer extends EntityRenderer<FoamDartProjectileEntity, LivingEntityRenderState> {
	private static final Identifier texture = Identifier.parse("ssc_14:textures/entities/foam_dart_texture.png");
	private final Modelfoam_dart model;

	public FoamDartProjectileRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new Modelfoam_dart(context.bakeLayer(Modelfoam_dart.LAYER_LOCATION));
	}

	@Override
	public void submit(LivingEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot - 90));
		poseStack.mulPose(Axis.ZP.rotationDegrees(90 + state.xRot));
		model.setupAnim(state);
		submitNodeCollector.submitModel(this.model, state, poseStack, texture, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
		poseStack.popPose();
		super.submit(state, poseStack, submitNodeCollector, camera);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void extractRenderState(FoamDartProjectileEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.xRot = entity.getXRot(partialTicks);
		state.yRot = entity.getYRot(partialTicks);
	}
}