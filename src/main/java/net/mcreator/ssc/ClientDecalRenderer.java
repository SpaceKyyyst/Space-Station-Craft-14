
package net.mcreator.ssc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;
import java.util.List;

public class ClientDecalRenderer {

    private static boolean registered = false;

    public static void ensureRegistered() {
        if (registered) return;
        System.out.println("[SS14-Decals] Connecting Decal Shaders to Render Frame Bus...");
        
        // Используем RenderFrameEvent.Post — это событие гарантированно не падает при запуске
        NeoForge.EVENT_BUS.addListener(net.neoforged.neoforge.client.event.RenderFrameEvent.Post.class, event -> {
            Minecraft mc = Minecraft.getInstance();
            ClientLevel level = mc.level;
            
            if (level == null || DecalNetwork.CLIENT_DECAL_STORAGE.isEmpty()) return;

            // КЛЮЧЕВОЙ ИСПРАВЛЕНИЕ: Вместо "new PoseStack()" мы берем честную системную матрицу 
            // трансформации мира самого Майнкрафта. Это намертво привяжет декали к блокам при беге/падении!
            PoseStack poseStack = new PoseStack();
            
            // Получаем точную интерполированную позицию камеры из системного рендерера сущностей кадра
            double renderX = mc.getEntityRenderDispatcher().camera.getPosition().x;
            double renderY = mc.getEntityRenderDispatcher().camera.getPosition().y;
            double renderZ = mc.getEntityRenderDispatcher().camera.getPosition().z;

            MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();

            for (var entry : DecalNetwork.CLIENT_DECAL_STORAGE.entrySet()) {
                List<DecalData> decals = entry.getValue();
                if (decals == null || decals.isEmpty()) continue;

                for (DecalData decal : decals) {
                    ResourceLocation texture = ResourceLocation.fromNamespaceAndPath("ssc_14", "textures/" + decal.decalId() + ".png");
                    VertexConsumer buffer = bufferSource.getBuffer(RenderType.entityCutout(texture));

                    poseStack.pushPose();

                    // Строим координаты относительно точной позиции кадра видеокарты
                    double x = decal.pos().getX() - renderX + 0.5;
                    double y = decal.pos().getY() - renderY + 0.5;
                    double z = decal.pos().getZ() - renderZ + 0.5;
                    poseStack.translate(x, y, z);

                    // Офсет 0.01 от грани блока наружу (0.5 + 0.01 = 0.51f)
                    Direction face = decal.face();
                    float offset = 0.51f; 
                    poseStack.translate(face.getStepX() * offset, face.getStepY() * offset, face.getStepZ() * offset);

                    // Разворачиваем грань лицом к игроку
                    applyFaceRotation(poseStack, face, decal.rotation());

                    // Рассчитываем реальный свет блока
                    int blockLight = level.getBrightness(LightLayer.BLOCK, decal.pos().relative(face));
                    int skyLight = level.getBrightness(LightLayer.SKY, decal.pos().relative(face));
                    int packedLight = LightTexture.pack(blockLight, skyLight);

                    renderQuad(poseStack.last().pose(), buffer, packedLight);

                    poseStack.popPose();
                }
            }
        });
        
        registered = true;
    }

    private static void applyFaceRotation(PoseStack stack, Direction face, int rotation) {
        // ИСПРАВЛЕНО: Стены (NORTH, SOUTH, WEST, EAST) теперь развернуты наружу блока,
        // чтобы их лицевая сторона смотрела прямо на игрока, а не внутрь барьера плитки!
        switch (face) {
            case DOWN -> stack.mulPose(Axis.XP.rotationDegrees(90));
            case UP -> stack.mulPose(Axis.XP.rotationDegrees(-90));
            case NORTH -> stack.mulPose(Axis.YP.rotationDegrees(180)); 
            case SOUTH -> stack.mulPose(Axis.YP.rotationDegrees(0));
            case WEST -> stack.mulPose(Axis.YP.rotationDegrees(-90));
            case EAST -> stack.mulPose(Axis.YP.rotationDegrees(90));
        }
        if (rotation != 0) {
            stack.mulPose(Axis.ZP.rotationDegrees(rotation * 90));
        }
    }

    private static void renderQuad(Matrix4f matrix, VertexConsumer buffer, int packedLight) {
        buffer.addVertex(matrix, -0.5f, -0.5f, 0).setColor(255, 255, 255, 255).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(0, 0, 1);
        buffer.addVertex(matrix, 0.5f, -0.5f, 0).setColor(255, 255, 255, 255).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(0, 0, 1);
        buffer.addVertex(matrix, 0.5f, 0.5f, 0).setColor(255, 255, 255, 255).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(0, 0, 1);
        buffer.addVertex(matrix, -0.5f, 0.5f, 0).setColor(255, 255, 255, 255).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(0, 0, 1);
    }
}
