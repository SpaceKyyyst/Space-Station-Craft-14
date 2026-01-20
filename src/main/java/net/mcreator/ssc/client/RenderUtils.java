package net.mcreator.ssc.client;

import org.lwjgl.opengl.GL11;

import org.joml.Matrix4f;

import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.Minecraft;

import com.mojang.math.Axis;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.opengl.GlStateManager;

public class RenderUtils {
	public static final ResourceLocation SUN_LOCATION = ResourceLocation.withDefaultNamespace("textures/environment/sun.png");
	public static final ResourceLocation MOON_LOCATION = ResourceLocation.withDefaultNamespace("textures/environment/moon_phases.png");
	private static final Minecraft mc = Minecraft.getInstance();

	public static void swapVanillaTexture(ResourceLocation original, ResourceLocation replacement) {
		TextureManager textureManager = mc.getTextureManager();
		textureManager.registerAndLoad(original, new SimpleTexture(replacement));
	}

	public static void renderCustomSun(RenderLevelStageEvent.AfterSky event, ResourceLocation texture) {
		PoseStack posestack = event.getPoseStack();
		posestack.pushPose();
		GlStateManager._enableBlend();
		GlStateManager._depthMask(false);
		GlStateManager._blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE, GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(false);
		float f11 = 1.0F - mc.getInstance().player.level().getRainLevel(partialTick);
		posestack.mulPose(Axis.YP.rotationDegrees(-90.0F));
		posestack.mulPose(Axis.XP.rotationDegrees(mc.getInstance().player.level().getTimeOfDay(partialTick) * 360.0F));
		Matrix4f matrix4f1 = posestack.last().pose();
		float f12 = 30.0F;
		MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();
		VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.celestial(texture));
		vertexConsumer.addVertex(matrix4f1, -f12, 100.0F, -f12).setUv(0.0F, 0.0F).setColor(-1);
		vertexConsumer.addVertex(matrix4f1, f12, 100.0F, -f12).setUv(1.0F, 0.0F).setColor(-1);
		vertexConsumer.addVertex(matrix4f1, f12, 100.0F, f12).setUv(1.0F, 1.0F).setColor(-1);
		vertexConsumer.addVertex(matrix4f1, -f12, 100.0F, f12).setUv(0.0F, 1.0F).setColor(-1);
		bufferSource.endBatch();
		GlStateManager._disableBlend();
		GlStateManager._depthMask(true);
		posestack.popPose();
	}

	public static void renderCustomMoon(RenderLevelStageEvent.AfterSky event, ResourceLocation texture) {
		PoseStack posestack = event.getPoseStack();
		posestack.pushPose();
		GlStateManager._enableBlend();
		GlStateManager._depthMask(false);
		GlStateManager._blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE, GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(false);
		float f11 = 1.0F - mc.getInstance().player.level().getRainLevel(partialTick);
		posestack.mulPose(Axis.YP.rotationDegrees(-90.0F));
		posestack.mulPose(Axis.XP.rotationDegrees(mc.getInstance().player.level().getTimeOfDay(partialTick) * 360.0F));
		Matrix4f matrix4f1 = posestack.last().pose();
		float f12 = 20.0F;
		int k = mc.getInstance().player.level().getMoonPhase();
		int l = k % 4;
		int i1 = k / 4 % 2;
		float f13 = (float) (l + 0) / 4.0F;
		float f14 = (float) (i1 + 0) / 2.0F;
		float f15 = (float) (l + 1) / 4.0F;
		float f16 = (float) (i1 + 1) / 2.0F;
		MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();
		VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.celestial(texture));
		vertexConsumer.addVertex(matrix4f1, -f12, -100.0F, f12).setUv(f15, f16).setColor(-1);
		vertexConsumer.addVertex(matrix4f1, f12, -100.0F, f12).setUv(f13, f16).setColor(-1);
		vertexConsumer.addVertex(matrix4f1, f12, -100.0F, -f12).setUv(f13, f14).setColor(-1);
		vertexConsumer.addVertex(matrix4f1, -f12, -100.0F, -f12).setUv(f15, f14).setColor(-1);
		bufferSource.endBatch();
		GlStateManager._disableBlend();
		GlStateManager._depthMask(true);
		posestack.popPose();
	}

	public static void renderCustomSkybox(RenderLevelStageEvent.AfterSky event, ResourceLocation texture, int color, float alpha) {
		PoseStack poseStack = event.getPoseStack();
		poseStack.pushPose();
		GlStateManager._enableBlend();
		GlStateManager.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager._depthMask(false);
		MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();
		VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.celestial(texture));
		float distance = 100.0F;
		float size = 100.0F;
		int colorWithAlpha = (color & 0x00FFFFFF) | ((int) (alpha * 255) << 24);
		renderSkyboxQuad(poseStack, vertexConsumer, colorWithAlpha, -size, distance, -size, 1.0F / 4.0F, 1.0F / 3.0F, size, distance, -size, 2.0F / 4.0F, 1.0F / 3.0F, size, distance, size, 2.0F / 4.0F, 0.0F, -size, distance, size, 1.0F / 4.0F, 0.0F);
		renderSkyboxQuad(poseStack, vertexConsumer, colorWithAlpha, -size, -distance, -size, 1.0F / 4.0F, 2.0F / 3.0F, -size, -distance, size, 1.0F / 4.0F, 3.0F / 3.0F, size, -distance, size, 2.0F / 4.0F, 3.0F / 3.0F, size, -distance, -size,
				2.0F / 4.0F, 2.0F / 3.0F);
		renderSkyboxQuad(poseStack, vertexConsumer, colorWithAlpha, -distance, -size, size, 0.0F, 2.0F / 3.0F, -distance, -size, -size, 1.0F / 4.0F, 2.0F / 3.0F, -distance, size, -size, 1.0F / 4.0F, 1.0F / 3.0F, -distance, size, size, 0.0F,
				1.0F / 3.0F);
		renderSkyboxQuad(poseStack, vertexConsumer, colorWithAlpha, -size, -size, -distance, 1.0F / 4.0F, 2.0F / 3.0F, size, -size, -distance, 2.0F / 4.0F, 2.0F / 3.0F, size, size, -distance, 2.0F / 4.0F, 1.0F / 3.0F, -size, size, -distance,
				1.0F / 4.0F, 1.0F / 3.0F);
		renderSkyboxQuad(poseStack, vertexConsumer, colorWithAlpha, distance, -size, -size, 2.0F / 4.0F, 2.0F / 3.0F, distance, -size, size, 3.0F / 4.0F, 2.0F / 3.0F, distance, size, size, 3.0F / 4.0F, 1.0F / 3.0F, distance, size, -size, 2.0F / 4.0F,
				1.0F / 3.0F);
		renderSkyboxQuad(poseStack, vertexConsumer, colorWithAlpha, size, -size, distance, 3.0F / 4.0F, 2.0F / 3.0F, -size, -size, distance, 4.0F / 4.0F, 2.0F / 3.0F, -size, size, distance, 4.0F / 4.0F, 1.0F / 3.0F, size, size, distance, 3.0F / 4.0F,
				1.0F / 3.0F);
		bufferSource.endBatch();
		GlStateManager._depthMask(true);
		GlStateManager._disableBlend();
		poseStack.popPose();
	}

	private static void renderSkyboxQuad(PoseStack poseStack, VertexConsumer vertexConsumer, int color, float x1, float y1, float z1, float u1, float v1, float x2, float y2, float z2, float u2, float v2, float x3, float y3, float z3, float u3,
			float v3, float x4, float y4, float z4, float u4, float v4) {
		PoseStack.Pose pose = poseStack.last();
		Matrix4f matrix = pose.pose();
		vertexConsumer.addVertex(matrix, x1, y1, z1).setColor(color).setUv(u1, v1);
		vertexConsumer.addVertex(matrix, x2, y2, z2).setColor(color).setUv(u2, v2);
		vertexConsumer.addVertex(matrix, x3, y3, z3).setColor(color).setUv(u3, v3);
		vertexConsumer.addVertex(matrix, x4, y4, z4).setColor(color).setUv(u4, v4);
	}

	public static int[] getRGB(int hexColor) {
		int[] rgb = new int[3];
		rgb[0] = (hexColor >> 16) & 0xFF;
		rgb[1] = (hexColor >> 8) & 0xFF;
		rgb[2] = hexColor & 0xFF;
		return rgb;
	}
}