// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class helmet_atmospheric_equipped<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "helmet_atmospheric_equipped"), "main");
	private final ModelPart helmet;

	public helmet_atmospheric_equipped(ModelPart root) {
		this.helmet = root.getChild("helmet");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition helmet = partdefinition.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(0, 17).addBox(-4.0F, -2.0F, -5.0F, 8.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(66, 49).addBox(-1.0F, -7.0F, -5.0F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(-3.0F, -9.0F, -4.0F, 6.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition cube_r1 = helmet.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(30, 70).addBox(-0.5381F, -0.1913F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -8.0F, 4.0F, -0.0674F, -0.1611F, 0.3981F));

		PartDefinition cube_r2 = helmet.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(66, 55).addBox(-1.4619F, -0.1913F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -8.0F, 4.0F, -0.0674F, 0.1611F, -0.3981F));

		PartDefinition cube_r3 = helmet.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(60, 7).addBox(-3.0F, 2.0F, -1.5F, 7.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(48, 62).addBox(-1.5F, 0.0F, -2.0F, 4.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -9.0F, 4.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r4 = helmet.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(38, 66).addBox(0.0F, 0.0F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 22).addBox(0.0F, 3.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -7.0F, -5.0F, 0.0F, -0.3927F, 0.0F));

		PartDefinition cube_r5 = helmet.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(30, 66).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 16).addBox(-3.0F, 3.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -7.0F, -5.0F, 0.0F, 0.3927F, 0.0F));

		PartDefinition cube_r6 = helmet.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(44, 16).addBox(-1.5F, 0.0F, -7.0F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -8.0F, 4.0F, 0.0F, -0.1745F, 0.0F));

		PartDefinition cube_r7 = helmet.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(16, 44).addBox(-0.5F, 0.0F, -7.0F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -8.0F, 4.0F, 0.0F, 0.1745F, 0.0F));

		PartDefinition cube_r8 = helmet.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(24, 41).addBox(2.0F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -1.0F, -5.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r9 = helmet.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(24, 38).addBox(-3.0F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -1.0F, -5.0F, 0.0F, 0.7854F, 0.0F));

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