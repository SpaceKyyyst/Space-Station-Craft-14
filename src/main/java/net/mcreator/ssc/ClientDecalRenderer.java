
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
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import org.joml.Matrix4f;
import java.util.List;

public class ClientDecalRenderer {
    private static boolean registered = false;

    public static void ensureRegistered() {
        if (registered) return;
        registered = true;
        
        // ИСПРАВЛЕНО: Регистрация слушателей по новым стандартам шины NeoForge 26.x
        NeoForge.EVENT_BUS.addListener(RenderLevelStageEvent.class, event -> {
            if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
                renderDecals(event);
            }
        });
        
        NeoForge.EVENT_BUS.addListener(ClientPlayerNetworkEvent.LoggingOut.class, e -> {
            DecalNetwork.CLIENT_DECAL_STORAGE.clear();
        });
    }

    private static void renderDecals(RenderLevelStageEvent event) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level == null || DecalNetwork.CLIENT_DECAL_STORAGE.isEmpty()) return;
        if (event.getCamera() == null) return;
        
        PoseStack poseStack = event.getPoseStack();
        if (poseStack == null || poseStack.last() == null) return;
        
        MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();
        if (bufferSource == null) return;
        
        Vec3 cameraPos = event.getCamera().getPosition();
        double renderX = cameraPos.x;
        double renderY = cameraPos.y;
        double renderZ = cameraPos.z;

        for (var entry : DecalNetwork.CLIENT_DECAL_STORAGE.entrySet()) {
            List<DecalData> decals = entry.getValue();
            if (decals == null || decals.isEmpty()) continue;
            int totalDecals = decals.size();
            for (int i = 0; i != totalDecals; i++) {
                DecalData decal = decals.get(i);
                ResourceLocation texture = ResourceLocation.fromNamespaceAndPath("ssc_14", "textures/" + decal.decalId() + ".png");
                VertexConsumer buffer = bufferSource.getBuffer(RenderType.entityCutout(texture));
                
                poseStack.pushPose();
                double x = decal.pos().getX() - renderX + 0.5;
                double y = decal.pos().getY() - renderY + 0.5;
                double z = decal.pos().getZ() - renderZ + 0.5;
                poseStack.translate(x, y, z);
                
                Direction face = decal.face();
                float offset = 0.505f;
                poseStack.translate(face.getStepX() * offset, face.getStepY() * offset, face.getStepZ() * offset);
                
                applyFaceRotation(poseStack, face, decal.rotation());
                
                int blockLight = level.getBrightness(LightLayer.BLOCK, decal.pos().relative(face));
                int skyLight = level.getBrightness(LightLayer.SKY, decal.pos().relative(face));
                int packedLight = LightTexture.pack(blockLight, skyLight);
                
                renderQuad(poseStack.last().pose(), buffer, packedLight, decal.color());
                poseStack.popPose();
            }
        }
        bufferSource.endBatch();
    }

    private static void applyFaceRotation(PoseStack stack, Direction face, int rotation) {
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

    private static void renderQuad(Matrix4f matrix, VertexConsumer buffer, int packedLight, int color) {
        int a = (color >> 24) & 0xFF;
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        if (color == -1) {
            r = 255; g = 255; b = 255; a = 255;
        }
        // ИСПРАВЛЕНО: Соответствие цепочки методов VertexConsumer стандартам отрисовки 26.x
        buffer.addVertex(matrix, -0.5f, -0.5f, 0).setColor(r, g, b, a).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(0, 0, 1);
        buffer.addVertex(matrix, 0.5f, -0.5f, 0).setColor(r, g, b, a).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(0, 0, 1);
        buffer.addVertex(matrix, 0.5f, 0.5f, 0).setColor(r, g, b, a).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(0, 0, 1);
        buffer.addVertex(matrix, -0.5f, 0.5f, 0).setColor(r, g, b, a).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(0, 0, 1);
    }
}
