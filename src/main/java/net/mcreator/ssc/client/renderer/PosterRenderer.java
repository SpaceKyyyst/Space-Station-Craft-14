
package net.mcreator.ssc.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.mcreator.ssc.entity.PosterEntity;

public class PosterRenderer extends EntityRenderer<PosterEntity, PosterRenderer.PosterRenderState> {

    public PosterRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public static class PosterRenderState extends net.minecraft.client.renderer.entity.state.EntityRenderState {
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

    @Override
    public void render(PosterRenderState state, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        
        // Поворот текстуры к игроку
        poseStack.mulPose(Axis.YP.rotationDegrees(-state.facing.toYRot()));
        
        // Смещаем рендер в центр блока и вплотную к стене
        poseStack.translate(0.0, 0.0, 0.0); 

        float halfWidth = 1.0F;  
        float halfHeight = 1.0F; 
        
        poseStack.translate(0.0, halfHeight, 0.0);

        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath("ssc_14", "textures/item/" + state.posterType + ".png");
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(texture));

        PoseStack.Pose pose = poseStack.last();
        
        // ЛИЦЕВАЯ СТОРОНА (нормаль смотрит на игрока: Z = -1.0F)
        vertex(vertexConsumer, pose, -halfWidth, -halfHeight, 0.0F, 0, 1, packedLight, 0.0F, 0.0F, -1.0F);
        vertex(vertexConsumer, pose, halfWidth, -halfHeight, 0.0F, 1, 1, packedLight, 0.0F, 0.0F, -1.0F);
        vertex(vertexConsumer, pose, halfWidth, halfHeight, 0.0F, 1, 0, packedLight, 0.0F, 0.0F, -1.0F);
        vertex(vertexConsumer, pose, -halfWidth, halfHeight, 0.0F, 0, 0, packedLight, 0.0F, 0.0F, -1.0F);

        // ЗАДНЯЯ СТОРОНА (нормаль смотрит внутрь стены: Z = 1.0F, вершины идут в обратном порядке)
        vertex(vertexConsumer, pose, halfWidth, -halfHeight, 0.001F, 0, 1, packedLight, 0.0F, 0.0F, 1.0F);
        vertex(vertexConsumer, pose, -halfWidth, -halfHeight, 0.001F, 1, 1, packedLight, 0.0F, 0.0F, 1.0F);
        vertex(vertexConsumer, pose, -halfWidth, halfHeight, 0.001F, 1, 0, packedLight, 0.0F, 0.0F, 1.0F);
        vertex(vertexConsumer, pose, halfWidth, halfHeight, 0.001F, 0, 0, packedLight, 0.0F, 0.0F, 1.0F);

        poseStack.popPose();
        super.render(state, poseStack, buffer, packedLight);
    }

    // Обновленный вспомогательный метод с поддержкой кастомных нормалей
    private void vertex(VertexConsumer consumer, PoseStack.Pose pose, float x, float y, float z, float u, float v, int light, float nx, float ny, float nz) {
        consumer.addVertex(pose, x, y, z)
                .setColor(255, 255, 255, 255)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(pose, nx, ny, nz);
    }
}
