// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class specific_hristov_suitstorage_equipped<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "specific_hristov_suitstorage_equipped"), "main");
	private final ModelPart torso;

	public specific_hristov_suitstorage_equipped(ModelPart root) {
		this.torso = root.getChild("torso");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(32, 26).addBox(3.5F, -12.0F, 4.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.125F))
		.texOffs(28, 17).addBox(4.0F, -12.0F, 4.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 18).addBox(4.0F, -6.0F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.125F))
		.texOffs(36, 20).addBox(4.0F, -6.0F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 14).addBox(5.0F, 0.0F, 6.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(32, 30).addBox(7.0F, 0.0F, 5.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(10, 35).addBox(3.5F, 4.0F, 5.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 35).addBox(3.5F, 7.0F, 5.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 0).addBox(3.5F, 1.5F, 6.25F, 2.0F, 9.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(32, 17).addBox(4.0F, 2.0F, 6.75F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 22).addBox(4.0F, 5.5F, 7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.025F))
		.texOffs(6, 15).addBox(4.0F, -2.0F, 5.0F, 1.0F, 19.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(10, 15).addBox(4.0F, -2.0F, 5.0F, 1.0F, 19.0F, 1.0F, new CubeDeformation(0.125F))
		.texOffs(0, 15).addBox(3.5F, -2.0F, 4.0F, 2.0F, 19.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 0).addBox(3.5F, 3.0F, 2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(32, 6).addBox(3.5F, 7.0F, 3.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 15).addBox(4.5F, 7.0F, 1.0F, 0.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(24, 11).addBox(4.0F, 15.0F, 1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 35).addBox(4.0F, 17.0F, 1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(22, 28).addBox(3.5F, 17.0F, 3.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition cube_r1 = torso.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(28, 11).addBox(-1.0F, -2.0F, -4.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 11.0F, 4.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition cube_r2 = torso.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(36, 17).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 4.0F, 5.0F, 0.0F, -0.3927F, 0.0F));

		PartDefinition cube_r3 = torso.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(26, 32).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(4.5F, 4.0F, 2.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r4 = torso.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(22, 32).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -2.0F, 4.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition cube_r5 = torso.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(18, 15).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 19.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, -2.0F, 5.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r6 = torso.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(14, 15).addBox(0.0F, 0.0F, -1.0F, 1.0F, 19.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -2.0F, 5.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r7 = torso.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(8, 35).addBox(0.0F, -0.5F, -0.5F, 0.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -5.5F, 4.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition cube_r8 = torso.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(6, 35).addBox(0.0F, -0.5F, -0.5F, 0.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -5.5F, 4.0F, 0.0F, 0.0F, 0.1745F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		torso.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}