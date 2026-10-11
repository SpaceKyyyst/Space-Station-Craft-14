package net.mcreator.ssc.client;

import org.joml.Vector4f;
import org.joml.Vector3f;
import org.joml.Matrix4fStack;
import org.joml.Matrix4f;

import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.state.level.SkyRenderState;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.Minecraft;

import java.util.OptionalInt;
import java.util.OptionalDouble;

import com.mojang.math.Axis;
import com.mojang.blaze3d.vertex.*;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.buffers.GpuBuffer;

public class RenderUtils {
	private static GpuBuffer sunBuffer;
	private static GpuBuffer moonBuffer;
	private static GpuBuffer skyboxBuffer;

	private static void initBuffers() {
		if (sunBuffer == null)
			sunBuffer = buildSunBuffer();
		if (moonBuffer == null)
			moonBuffer = buildMoonBuffer();
		if (skyboxBuffer == null)
			skyboxBuffer = buildSkyboxBuffer();
	}

	public static void renderCustomSun(RenderLevelStageEvent.AfterSky event, Identifier textureId) {
		initBuffers();
		Minecraft mc = Minecraft.getInstance();
		PoseStack poseStack = event.getPoseStack();
		SkyRenderState state = event.getLevelRenderState().skyRenderState;
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
		poseStack.mulPose(Axis.XP.rotation(state.sunAngle));
		Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
		modelViewStack.pushMatrix();
		modelViewStack.mul(poseStack.last().pose());
		modelViewStack.translate(0.0F, 100.0F, 0.0F);
		modelViewStack.scale(30.0F, 1.0F, 30.0F);
		GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms().writeTransform(modelViewStack, new Vector4f(1.0F, 1.0F, 1.0F, state.rainBrightness), new Vector3f(), new Matrix4f());
		GpuTextureView color = mc.getMainRenderTarget().getColorTextureView();
		GpuTextureView depth = mc.getMainRenderTarget().getDepthTextureView();
		GpuBuffer indexBuffer = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS).getBuffer(6);
		AbstractTexture texture = mc.getTextureManager().getTexture(textureId);
		try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "ssc_14 Sun", color, OptionalInt.empty(), depth, OptionalDouble.empty())) {
			renderPass.setPipeline(RenderPipelines.CELESTIAL);
			RenderSystem.bindDefaultUniforms(renderPass);
			renderPass.setUniform("DynamicTransforms", dynamicTransforms);
			renderPass.bindTexture("Sampler0", texture.getTextureView(), texture.getSampler());
			renderPass.setVertexBuffer(0, sunBuffer);
			renderPass.setIndexBuffer(indexBuffer, RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS).type());
			renderPass.drawIndexed(0, 0, 6, 1);
		}
		modelViewStack.popMatrix();
		poseStack.popPose();
	}

	public static void renderCustomMoon(RenderLevelStageEvent.AfterSky event, Identifier textureId) {
		initBuffers();
		Minecraft mc = Minecraft.getInstance();
		PoseStack poseStack = event.getPoseStack();
		SkyRenderState state = event.getLevelRenderState().skyRenderState;
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
		poseStack.mulPose(Axis.XP.rotation(state.moonAngle));
		Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
		modelViewStack.pushMatrix();
		modelViewStack.mul(poseStack.last().pose());
		modelViewStack.translate(0.0F, 100.0F, 0.0F);
		modelViewStack.scale(20.0F, 1.0F, 20.0F);
		GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms().writeTransform(modelViewStack, new Vector4f(1.0F, 1.0F, 1.0F, state.rainBrightness), new Vector3f(), new Matrix4f());
		GpuTextureView color = mc.getMainRenderTarget().getColorTextureView();
		GpuTextureView depth = mc.getMainRenderTarget().getDepthTextureView();
		GpuBuffer indexBuffer = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS).getBuffer(6);
		AbstractTexture texture = mc.getTextureManager().getTexture(textureId);
		int baseVertex = state.moonPhase.index() * 4;
		try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "ssc_14 Moon", color, OptionalInt.empty(), depth, OptionalDouble.empty())) {
			renderPass.setPipeline(RenderPipelines.CELESTIAL);
			RenderSystem.bindDefaultUniforms(renderPass);
			renderPass.setUniform("DynamicTransforms", dynamicTransforms);
			renderPass.bindTexture("Sampler0", texture.getTextureView(), texture.getSampler());
			renderPass.setVertexBuffer(0, moonBuffer);
			renderPass.setIndexBuffer(indexBuffer, RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS).type());
			renderPass.drawIndexed(baseVertex, 0, 6, 1);
		}
		modelViewStack.popMatrix();
		poseStack.popPose();
	}

	public static void renderCustomSkybox(RenderLevelStageEvent.AfterSky event, Identifier textureId, int color, float alpha) {
		initBuffers();
		Minecraft mc = Minecraft.getInstance();
		PoseStack poseStack = event.getPoseStack();
		Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
		modelViewStack.pushMatrix();
		modelViewStack.mul(poseStack.last().pose());
		float[] rgba = getRGBA(color, alpha);
		GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms().writeTransform(modelViewStack, new Vector4f(rgba[0], rgba[1], rgba[2], rgba[3]), new Vector3f(), new Matrix4f());
		GpuTextureView colorTexture = mc.getMainRenderTarget().getColorTextureView();
		GpuTextureView depth = mc.getMainRenderTarget().getDepthTextureView();
		GpuBuffer indexBuffer = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS).getBuffer(36);
		AbstractTexture texture = mc.getTextureManager().getTexture(textureId);
		try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "ssc_14 Skybox", colorTexture, OptionalInt.empty(), depth, OptionalDouble.empty())) {
			renderPass.setPipeline(RenderPipelines.END_SKY);
			RenderSystem.bindDefaultUniforms(renderPass);
			renderPass.setUniform("DynamicTransforms", dynamicTransforms);
			renderPass.bindTexture("Sampler0", texture.getTextureView(), texture.getSampler());
			renderPass.setVertexBuffer(0, skyboxBuffer);
			renderPass.setIndexBuffer(indexBuffer, RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS).type());
			renderPass.drawIndexed(0, 0, 36, 1);
		}
		modelViewStack.popMatrix();
	}

	private static GpuBuffer buildSunBuffer() {
		VertexFormat format = DefaultVertexFormat.POSITION_TEX;
		try (ByteBufferBuilder byteBufferBuilder = ByteBufferBuilder.exactlySized(4 * format.getVertexSize())) {
			BufferBuilder bufferBuilder = new BufferBuilder(byteBufferBuilder, VertexFormat.Mode.QUADS, format);
			bufferBuilder.addVertex(-1.0F, 0.0F, -1.0F).setUv(0.0F, 0.0F);
			bufferBuilder.addVertex(1.0F, 0.0F, -1.0F).setUv(1.0F, 0.0F);
			bufferBuilder.addVertex(1.0F, 0.0F, 1.0F).setUv(1.0F, 1.0F);
			bufferBuilder.addVertex(-1.0F, 0.0F, 1.0F).setUv(0.0F, 1.0F);
			try (MeshData mesh = bufferBuilder.buildOrThrow()) {
				return RenderSystem.getDevice().createBuffer(() -> "ssc_14 Sun", 32, mesh.vertexBuffer());
			}
		}
	}

	private static GpuBuffer buildMoonBuffer() {
		VertexFormat format = DefaultVertexFormat.POSITION_TEX;
		try (ByteBufferBuilder byteBufferBuilder = ByteBufferBuilder.exactlySized(8 * 4 * format.getVertexSize())) {
			BufferBuilder bufferBuilder = new BufferBuilder(byteBufferBuilder, VertexFormat.Mode.QUADS, format);
			for (int k = 0; k < 8; k++) {
				int l = k % 4;
				int i1 = k / 4 % 2;
				float f13 = (float) (l) / 4.0F;
				float f14 = (float) (i1) / 2.0F;
				float f15 = (float) (l + 1) / 4.0F;
				float f16 = (float) (i1 + 1) / 2.0F;
				bufferBuilder.addVertex(-1.0F, 0.0F, -1.0F).setUv(f15, f16);
				bufferBuilder.addVertex(1.0F, 0.0F, -1.0F).setUv(f13, f16);
				bufferBuilder.addVertex(1.0F, 0.0F, 1.0F).setUv(f13, f14);
				bufferBuilder.addVertex(-1.0F, 0.0F, 1.0F).setUv(f15, f14);
			}
			try (MeshData mesh = bufferBuilder.buildOrThrow()) {
				return RenderSystem.getDevice().createBuffer(() -> "ssc_14 Moon", 32, mesh.vertexBuffer());
			}
		}
	}

	private static GpuBuffer buildSkyboxBuffer() {
		VertexFormat format = DefaultVertexFormat.POSITION_TEX_COLOR;
		try (ByteBufferBuilder byteBufferBuilder = ByteBufferBuilder.exactlySized(24 * format.getVertexSize())) {
			BufferBuilder bufferBuilder = new BufferBuilder(byteBufferBuilder, VertexFormat.Mode.QUADS, format);
			float distance = 100.0F;
			float size = 100.0F;
			int color = 0xFFFFFFFF;
			addSkyboxFace(bufferBuilder, -size, distance, -size, 1.0F / 4.0F, 1.0F / 3.0F, size, distance, -size, 2.0F / 4.0F, 1.0F / 3.0F, size, distance, size, 2.0F / 4.0F, 0.0F, -size, distance, size, 1.0F / 4.0F, 0.0F, color);
			addSkyboxFace(bufferBuilder, -size, -distance, -size, 1.0F / 4.0F, 2.0F / 3.0F, -size, -distance, size, 1.0F / 4.0F, 3.0F / 3.0F, size, -distance, size, 2.0F / 4.0F, 3.0F / 3.0F, size, -distance, -size, 2.0F / 4.0F, 2.0F / 3.0F, color);
			addSkyboxFace(bufferBuilder, -distance, -size, size, 0.0F, 2.0F / 3.0F, -distance, -size, -size, 1.0F / 4.0F, 2.0F / 3.0F, -distance, size, -size, 1.0F / 4.0F, 1.0F / 3.0F, -distance, size, size, 0.0F, 1.0F / 3.0F, color);
			addSkyboxFace(bufferBuilder, -size, -size, -distance, 1.0F / 4.0F, 2.0F / 3.0F, size, -size, -distance, 2.0F / 4.0F, 2.0F / 3.0F, size, size, -distance, 2.0F / 4.0F, 1.0F / 3.0F, -size, size, -distance, 1.0F / 4.0F, 1.0F / 3.0F, color);
			addSkyboxFace(bufferBuilder, distance, -size, -size, 2.0F / 4.0F, 2.0F / 3.0F, distance, -size, size, 3.0F / 4.0F, 2.0F / 3.0F, distance, size, size, 3.0F / 4.0F, 1.0F / 3.0F, distance, size, -size, 2.0F / 4.0F, 1.0F / 3.0F, color);
			addSkyboxFace(bufferBuilder, size, -size, distance, 3.0F / 4.0F, 2.0F / 3.0F, -size, -size, distance, 4.0F / 4.0F, 2.0F / 3.0F, -size, size, distance, 4.0F / 4.0F, 1.0F / 3.0F, size, size, distance, 3.0F / 4.0F, 1.0F / 3.0F, color);
			try (MeshData meshData = bufferBuilder.buildOrThrow()) {
				return RenderSystem.getDevice().createBuffer(() -> "ssc_14 Skybox", 40, meshData.vertexBuffer());
			}
		}
	}

	private static void addSkyboxFace(BufferBuilder bufferBuilder, float x1, float y1, float z1, float u1, float v1, float x2, float y2, float z2, float u2, float v2, float x3, float y3, float z3, float u3, float v3, float x4, float y4, float z4,
			float u4, float v4, int color) {
		bufferBuilder.addVertex(x1, y1, z1).setUv(u1, v1).setColor(color);
		bufferBuilder.addVertex(x2, y2, z2).setUv(u2, v2).setColor(color);
		bufferBuilder.addVertex(x3, y3, z3).setUv(u3, v3).setColor(color);
		bufferBuilder.addVertex(x4, y4, z4).setUv(u4, v4).setColor(color);
	}

	public static float[] getRGBA(int hexColor, float alpha) {
		float[] rgba = new float[4];
		rgba[0] = ((hexColor >> 16) & 0xFF) / 255.0f;
		rgba[1] = ((hexColor >> 8) & 0xFF) / 255.0f;
		rgba[2] = (hexColor & 0xFF) / 255.0f;
		rgba[3] = alpha;
		return rgba;
	}
}