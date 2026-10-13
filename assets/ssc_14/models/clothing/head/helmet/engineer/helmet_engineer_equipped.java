// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class helmet_engineer_equipped<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "helmet_engineer_equipped"), "main");
	private final ModelPart helmet;

	public helmet_engineer_equipped(ModelPart root) {
		this.helmet = root.getChild("helmet");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition helmet = partdefinition.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(0, 17).addBox(-4.0F, -2.0F, -5.0F, 8.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(28, 10).addBox(-1.0F, -7.0F, -5.0F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(-5.0F, -7.0F, -4.0F, 10.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(28, 0).addBox(-3.0F, -9.0F, -4.0F, 6.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition cube_r1 = helmet.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(26, 67).addBox(-0.5F, -3.0F, -3.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 42).addBox(-0.5F, 0.0F, -7.0F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -8.0F, 4.0F, 0.0F, 0.1745F, 0.0F));

		PartDefinition cube_r2 = helmet.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(50, 56).addBox(-3.0F, 2.0F, -1.5F, 7.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(66, 23).addBox(-1.5F, 0.0F, -2.0F, 4.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -9.0F, 4.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r3 = helmet.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(50, 10).addBox(-1.5F, 0.0F, -7.0F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -8.0F, 4.0F, 0.0F, -0.1745F, 0.0F));

		PartDefinition cube_r4 = helmet.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(34, 71).addBox(0.0F, 0.0F, 0.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -7.0F, -5.0F, 0.0F, -0.3927F, 0.0F));

		PartDefinition cube_r5 = helmet.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(26, 71).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -7.0F, -5.0F, 0.0F, 0.3927F, 0.0F));

		PartDefinition cube_r6 = helmet.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(72, 52).addBox(0.0F, -1.0F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -1.0F, -5.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r7 = helmet.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(72, 49).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -1.0F, -5.0F, 0.0F, 0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		helmet.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}