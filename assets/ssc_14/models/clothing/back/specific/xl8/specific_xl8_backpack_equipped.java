// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class specific_xl8_backpack_equipped<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "specific_xl8_backpack_equipped"), "main");
	private final ModelPart torso;

	public specific_xl8_backpack_equipped(ModelPart root) {
		this.torso = root.getChild("torso");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 11.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition cube_r1 = torso.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(24, 13).addBox(0.0F, -0.5F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.182F, 7.4749F, 3.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r2 = torso.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(24, 5).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.7175F, -1.0104F, 3.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r3 = torso.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(22, 23).addBox(0.0F, -2.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-9.7782F, 1.4645F, 3.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r4 = torso.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(22, 27).addBox(-2.0F, 0.0F, -0.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.4142F, 5.0F, 3.0F, 0.0F, 0.0F, 1.1781F));

		PartDefinition cube_r5 = torso.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(6, 28).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.7678F, 6.0607F, 3.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r6 = torso.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 23).addBox(-4.5F, -1.5F, -1.0F, 9.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.1213F, 2.1716F, 3.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r7 = torso.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(24, 20).addBox(-2.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9393F, 2.5251F, 3.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r8 = torso.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(24, 0).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9393F, 8.182F, 3.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r9 = torso.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(24, 12).addBox(-3.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9497F, 5.7071F, 3.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r10 = torso.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(24, 18).addBox(0.0F, 0.0F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0711F, 7.8284F, 3.0F, 0.0F, 0.0F, 1.0472F));

		PartDefinition cube_r11 = torso.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 15).addBox(-5.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9497F, 8.5355F, 3.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r12 = torso.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 19).addBox(0.0F, -2.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-2.7071F, 8.5355F, 3.0F, 0.0F, 0.0F, 0.48F));

		PartDefinition cube_r13 = torso.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.5459F, 12.4246F, 3.0F, 0.0F, 0.0F, 0.7854F));

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