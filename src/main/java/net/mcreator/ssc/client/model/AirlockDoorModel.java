package net.mcreator.ssc.client.model;

import net.mcreator.ssc.math.AirlockMotion;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

/** Block model with its origin at the centre of the doorway's floor plane; Y points down. */
public final class AirlockDoorModel extends net.minecraft.client.model.Model<Float> {
    private static final float THICKNESS = 6;
    private static final float ABOVE_FLOOR = AirlockMotion.HEIGHT - AirlockMotion.BELOW_FLOOR;
    private static final int TEXTURE_SIZE = 128;
    private final ModelPart left;
    private final ModelPart right;
    private final int widthBlocks;

    public AirlockDoorModel(int widthBlocks) {
        super(layer(widthBlocks).bakeRoot(), net.minecraft.client.renderer.rendertype.RenderTypes::entityCutout);
        this.widthBlocks = widthBlocks;
        left = root.getChild("left_leaf");
        right = root.getChild("right_leaf");
        // Block atlases use explicit face UVs, not the unfolded box layout of entity models.
        root.visit(new com.mojang.blaze3d.vertex.PoseStack(), (pose, path, index, cube) -> {
            for (int face = 0; face < cube.polygons.length; face++) {
                var polygon = cube.polygons[face];
                var vertices = polygon.vertices().clone();
                for (int i = 0; i < vertices.length; i++) {
                    var vertex = vertices[i];
                    float u;
                    float v;
                    if (Math.abs(polygon.normal().z()) > 0.5f) {
                        u = (vertex.x() / widthBlocks + 8) / 64f;
                        v = vertex.y() <= 0 ? (vertex.y() + ABOVE_FLOOR) / 64f : 0.375f + vertex.y() / 128f;
                    } else if (Math.abs(polygon.normal().x()) > 0.5f) {
                        u = (vertex.z() + THICKNESS / 2) / 64f;
                        v = vertex.y() <= 0 ? 0.5f + (vertex.y() + ABOVE_FLOOR) / 64f : 0.75f + vertex.y() / 64f;
                    } else {
                        u = (vertex.x() / widthBlocks + 20) / 64f;
                        v = (vertex.z() + THICKNESS / 2 + 64) / 128f;
                    }
                    vertices[i] = vertex.remap(u, v);
                }
                cube.polygons[face] = new ModelPart.Polygon(vertices, polygon.normal());
            }
        });
    }

    public static LayerDefinition layer(int widthBlocks) {
        float leaf = AirlockMotion.leafWidth(widthBlocks);
        float halfWidth = leaf + AirlockMotion.FRAME_WIDTH;
        var mesh = new MeshDefinition();
        var parts = mesh.getRoot();
        parts.addOrReplaceChild("frame", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-halfWidth, -ABOVE_FLOOR, -THICKNESS / 2,
                        AirlockMotion.FRAME_WIDTH, ABOVE_FLOOR, THICKNESS)
                .addBox(-halfWidth, 0, -THICKNESS / 2, AirlockMotion.FRAME_WIDTH, AirlockMotion.BELOW_FLOOR, THICKNESS)
                .texOffs(0, 0).addBox(leaf, -ABOVE_FLOOR, -THICKNESS / 2,
                        AirlockMotion.FRAME_WIDTH, ABOVE_FLOOR, THICKNESS)
                .addBox(leaf, 0, -THICKNESS / 2, AirlockMotion.FRAME_WIDTH, AirlockMotion.BELOW_FLOOR, THICKNESS), PartPose.ZERO);
        // The below-floor portion belongs to the leaves, so it opens with the passage.
        parts.addOrReplaceChild("left_leaf", CubeListBuilder.create().texOffs(16, 0)
                .addBox(-leaf, -ABOVE_FLOOR, -THICKNESS / 2, leaf, ABOVE_FLOOR, THICKNESS)
                .addBox(-leaf, 0, -THICKNESS / 2, leaf, AirlockMotion.BELOW_FLOOR, THICKNESS), PartPose.ZERO);
        parts.addOrReplaceChild("right_leaf", CubeListBuilder.create().texOffs(32, 0)
                .addBox(0, -ABOVE_FLOOR, -THICKNESS / 2, leaf, ABOVE_FLOOR, THICKNESS)
                .addBox(0, 0, -THICKNESS / 2, leaf, AirlockMotion.BELOW_FLOOR, THICKNESS), PartPose.ZERO);
        return LayerDefinition.create(mesh, 64, TEXTURE_SIZE);
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

    @Override
    public void setupAnim(Float openness) {
        setOpenness(openness);
    }
}
