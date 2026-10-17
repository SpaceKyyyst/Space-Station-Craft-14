package net.mcreator.ssc.client.model;

import net.mcreator.ssc.math.AirlockMotion;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

/** Block model with its origin at the centre of the doorway's floor plane; Y points down. */
public final class AirlockDoorModel {
    private static final float THICKNESS = 6;
    private static final float ABOVE_FLOOR = AirlockMotion.HEIGHT - AirlockMotion.BELOW_FLOOR;
    private static final int TEXTURE_SIZE = 128;
    private final ModelPart root;
    private final ModelPart left;
    private final ModelPart right;
    private final int widthBlocks;

    public AirlockDoorModel(int widthBlocks) {
        this.widthBlocks = widthBlocks;
        root = layer(widthBlocks).bakeRoot();
        left = root.getChild("left_leaf");
        right = root.getChild("right_leaf");
    }

    public static LayerDefinition layer(int widthBlocks) {
        float leaf = AirlockMotion.leafWidth(widthBlocks);
        float halfWidth = leaf + AirlockMotion.FRAME_WIDTH;
        var mesh = new MeshDefinition();
        var parts = mesh.getRoot();
        parts.addOrReplaceChild("frame", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-halfWidth, -ABOVE_FLOOR, -THICKNESS / 2,
                        AirlockMotion.FRAME_WIDTH, AirlockMotion.HEIGHT, THICKNESS)
                .texOffs(0, 0).addBox(leaf, -ABOVE_FLOOR, -THICKNESS / 2,
                        AirlockMotion.FRAME_WIDTH, AirlockMotion.HEIGHT, THICKNESS), PartPose.ZERO);
        // The below-floor portion belongs to the leaves, so it opens with the passage.
        parts.addOrReplaceChild("left_leaf", CubeListBuilder.create().texOffs(16, 0)
                .addBox(-leaf, -ABOVE_FLOOR, -THICKNESS / 2, leaf, AirlockMotion.HEIGHT, THICKNESS), PartPose.ZERO);
        parts.addOrReplaceChild("right_leaf", CubeListBuilder.create().texOffs(72, 0)
                .addBox(0, -ABOVE_FLOOR, -THICKNESS / 2, leaf, AirlockMotion.HEIGHT, THICKNESS), PartPose.ZERO);
        return LayerDefinition.create(mesh, TEXTURE_SIZE, TEXTURE_SIZE);
    }

    public void setOpenness(float openness) {
        var pose = AirlockMotion.pose(widthBlocks, openness);
        left.resetPose();
        right.resetPose();
        left.x = pose.leftOffset();
        right.x = pose.rightOffset();
        left.xScale = pose.leafScale();
        right.xScale = pose.leafScale();
    }

    public ModelPart root() {
        return root;
    }
}
