package net.mcreator.ssc.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.mcreator.ssc.client.model.AirlockDoorModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;

/** Uses immutable extracted progress; shared model parts are posed only during submission rendering. */
public final class AirlockDoorRenderer<T extends BlockEntity> implements BlockEntityRenderer<T, AirlockDoorRenderer.State> {
    private static final int NO_OUTLINE = 0;
    private final Identifier texture;
    private final Identifier panelTexture;
    private final AirlockDoorModel model = new AirlockDoorModel(1);

    public AirlockDoorRenderer(Identifier texture) {
        this.texture = texture;
        this.panelTexture = Identifier.fromNamespaceAndPath(texture.getNamespace(),
                texture.getPath().replace(".png", "_panel_open.png"));
    }

    public static final class State extends BlockEntityRenderState {
        float openness;
        float rotation;
        boolean panelOpen;
    }

    @Override
    public State createRenderState() { return new State(); }

    @Override
    public void extractRenderState(T entity, State state, float partialTick, Vec3 camera,
            ModelFeatureRenderer.CrumblingOverlay overlay) {
        BlockEntityRenderer.super.extractRenderState(entity, state, partialTick, camera, overlay);
        var block = entity.getBlockState();
        var panel = block.getBlock().getStateDefinition().getProperty("panel_open");
        state.panelOpen = panel instanceof net.minecraft.world.level.block.state.properties.BooleanProperty value && block.getValue(value);
        var property = block.getBlock().getStateDefinition().getProperty("arl_variat");
        int frame = property instanceof IntegerProperty value ? block.getValue(value) : 0;
        state.openness = switch (frame) { case 11 -> 1f; case 14 -> 0.9f; case 13 -> 0.8f; case 12 -> 0.2f; default -> 0f; };
        var data = entity.getPersistentData();
        if (entity.getLevel() != null && frame >= 12 && frame <= 15 && data.contains("_ssc14_visual_started")) {
            int action = data.getIntOr("_ssc14_visual_action", 0);
            float elapsed = entity.getLevel().getGameTime() - data.getLongOr("_ssc14_visual_started", 0) + partialTick;
            float progress = Math.clamp(elapsed / 12f, 0f, 1f);
            if (action == 1) state.openness = progress;
            else if (action == 2) state.openness = 1f - progress;
        }
        Direction facing = block.getValue(HorizontalDirectionalBlock.FACING);
        state.rotation = -facing.toYRot();
    }

    @Override
    public void submit(State state, PoseStack poses, SubmitNodeCollector collector, CameraRenderState camera) {
        poses.pushPose();
        poses.translate(0.5, 0, 0.5);
        poses.mulPose(Axis.YP.rotationDegrees(state.rotation));
        poses.scale(1, -1, -1);
        collector.submitModel(model, state.openness, poses, state.panelOpen ? panelTexture : texture, state.lightCoords,
                OverlayTexture.NO_OVERLAY, NO_OUTLINE, state.breakProgress);
        poses.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen() { return true; }
}
