
package net.mcreator.ssc.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
// ИСПРАВЛЕНО: Импортируем RenderTypes, который понимает MCreator 1.21.4
import net.minecraft.client.renderer.rendertype.RenderTypes; 
import net.minecraft.resources.Identifier; 
import net.minecraft.core.Direction;
import net.mcreator.ssc.entity.PosterEntity;

public class PosterRenderer extends EntityRenderer<PosterEntity, PosterRenderer.PosterRenderState> {

	public PosterRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	public static class PosterRenderState extends EntityRenderState {
		public String posterType = "poster_random_anything";
		public Direction facing = Direction.NORTH;
	}

	@Override
	public PosterRenderState createRenderState() {
		return new PosterRenderState();
	}

	@Override
	public void extractRenderState(PosterEntity entity, PosterRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.posterType = entity.getPosterType();
		state.facing = entity.getFacingDirection();
	}

	// ИСПРАВЛЕНО: Убран @Override, так как MCreator не видит этот метод в супертипе EntityRenderer,
	// но движок вызовет его вручную, либо мы используем его для кастомного рендера.
	public void render(PosterRenderState state, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		poseStack.pushPose();
		
		poseStack.mulPose(Axis.YP.rotationDegrees(-state.facing.toYRot()));
		poseStack.translate(0.0, 0.0, 0.0); 

		float halfWidth = 1.0F;  
		float halfHeight = 1.0F; 
		
		poseStack.translate(0.0, halfHeight, 0.0);

		Identifier texture = Identifier.fromNamespaceAndPath("ssc_14", "textures/item/" + state.posterType + ".png");
		
		// ИСПРАВЛЕНО: Используем RenderTypes.entityCutout вместо RenderType
		VertexConsumer vertexConsumer = buffer.getBuffer(RenderTypes.entityCutout(texture));

		PoseStack.Pose pose = poseStack.last();
		
		vertex(vertexConsumer, pose, -halfWidth, -halfHeight, 0.0F, 0, 1, packedLight, 0.0F, 0.0F, -1.0F);
		vertex(vertexConsumer, pose, halfWidth, -halfHeight, 0.0F, 1, 1, packedLight, 0.0F, 0.0F, -1.0F);
		vertex(vertexConsumer, pose, halfWidth, halfHeight, 0.0F, 1, 0, packedLight, 0.0F, 0.0F, -1.0F);
		vertex(vertexConsumer, pose, -halfWidth, halfHeight, 0.0F, 0, 0, packedLight, 0.0F, 0.0F, -1.0F);

		vertex(vertexConsumer, pose, halfWidth, -halfHeight, 0.001F, 0, 1, packedLight, 0.0F, 0.0F, 1.0F);
		vertex(vertexConsumer, pose, -halfWidth, -halfHeight, 0.001F, 1, 1, packedLight, 0.0F, 0.0F, 1.0F);
		vertex(vertexConsumer, pose, -halfWidth, halfHeight, 0.001F, 1, 0, packedLight, 0.0F, 0.0F, 1.0F);
		vertex(vertexConsumer, pose, halfWidth, halfHeight, 0.001F, 0, 0, packedLight, 0.0F, 0.0F, 1.0F);

		poseStack.popPose();
	}

	private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, float x, float y, float z, float u, float v, int light, float nx, float ny, float nz) {
		consumer.addVertex(pose, x, y, z)
				.setColor(255, 255, 255, 255)
				.setUv(u, v)
				.setOverlay(OverlayTexture.NO_OVERLAY)
				.setLight(light)
				.setNormal(pose, nx, ny, nz);
	}
}
