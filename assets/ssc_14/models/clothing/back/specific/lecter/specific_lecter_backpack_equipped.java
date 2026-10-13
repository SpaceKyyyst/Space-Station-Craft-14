// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class specific_lecter_backpack_equipped<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "specific_lecter_backpack_equipped"), "main");
	private final ModelPart torso;

	public specific_lecter_backpack_equipped(ModelPart root) {
		this.torso = root.getChild("torso");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 11.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition cube_r1 = torso.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(24, 14).addBox(-2.0F, -0.5F, 0.0F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.4749F, 3.818F, 3.5F, 0.0F, 1.5708F, 0.7854F));

		PartDefinition cube_r2 = torso.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 25).addBox(-0.5F, -1.5F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.364F, 14.0711F, 3.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r3 = torso.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(24, 0).addBox(-2.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7175F, 11.5962F, 3.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r4 = torso.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(24, 3).addBox(-2.5F, -1.0F, -0.5F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5962F, 11.5962F, 3.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r5 = torso.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(12, 25).addBox(0.0F, 0.0F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(4.2426F, 7.0F, 3.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r6 = torso.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(6, 25).addBox(0.0F, 0.0F, -0.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.4142F, 3.5F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r7 = torso.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(24, 12).addBox(-2.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.4749F, 5.9393F, 3.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r8 = torso.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(12, 27).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0607F, 8.7678F, 3.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r9 = torso.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(24, 6).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.182F, 6.6464F, 3.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r10 = torso.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(18, 21).addBox(-4.0F, -2.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-3.5355F, 7.7071F, 3.5F, 0.0F, 0.0F, 1.1781F));

		PartDefinition cube_r11 = torso.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(18, 20).addBox(-3.5F, 0.0F, -0.5F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0607F, 1.6967F, 3.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r12 = torso.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 20).addBox(-3.0F, -1.0F, -1.5F, 6.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.2856F, 1.0073F, 3.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r13 = torso.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 15).addBox(-7.5F, -1.5F, -1.0F, 15.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.1213F, 4.1716F, 3.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r14 = torso.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(24, 10).addBox(-4.0F, 0.0F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-3.5355F, -0.7782F, 3.5F, 0.0F, 0.0F, 0.5236F));

		PartDefinition cube_r15 = torso.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(20, 25).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.1317F, -1.1317F, 3.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r16 = torso.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(26, 25).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.7782F, -2.1924F, 3.5F, 0.0F, 0.0F, 0.7854F));

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