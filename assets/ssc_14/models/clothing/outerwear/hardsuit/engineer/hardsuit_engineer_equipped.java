// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class hardsuit_engineer_equipped<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "hardsuit_engineer_equipped"), "main");
	private final ModelPart torso;
	private final ModelPart r_arm;
	private final ModelPart l_arm;
	private final ModelPart r_leg;
	private final ModelPart l_leg;

	public hardsuit_engineer_equipped(ModelPart root) {
		this.torso = root.getChild("torso");
		this.r_arm = root.getChild("r_arm");
		this.l_arm = root.getChild("l_arm");
		this.r_leg = root.getChild("r_leg");
		this.l_leg = root.getChild("l_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 11.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(60, 62).addBox(-4.0F, 7.0F, -4.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(50, 49).addBox(-4.5F, 0.0F, 2.0F, 9.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(56, 7).addBox(-4.0F, 1.3045F, -4.5307F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition cube_r1 = torso.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(60, 66).addBox(-2.0F, -3.0F, -1.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.8457F, -3.2242F, 0.3927F, 0.0F, 0.0F));

		PartDefinition cube_r2 = torso.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(68, 17).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.4567F, -5.2961F, 0.4215F, 0.3614F, 0.1572F));

		PartDefinition cube_r3 = torso.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(68, 56).addBox(0.0F, 0.0F, 0.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.4567F, -5.2961F, 0.4215F, -0.3614F, -0.1572F));

		PartDefinition cube_r4 = torso.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(52, 71).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 4.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r5 = torso.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(42, 71).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, -4.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition r_arm = partdefinition.addOrReplaceChild("r_arm", CubeListBuilder.create().texOffs(34, 10).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(34, 26).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(66, 33).addBox(-3.5F, 4.0F, -2.5F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(14, 67).addBox(-3.0F, -3.0F, -3.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 4.0F, 0.0F));

		PartDefinition l_arm = partdefinition.addOrReplaceChild("l_arm", CubeListBuilder.create().texOffs(16, 38).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 38).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(66, 41).addBox(0.5F, 4.0F, -2.5F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(68, 10).addBox(1.0F, -3.0F, -3.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 4.0F, 0.0F));

		PartDefinition r_leg = partdefinition.addOrReplaceChild("r_leg", CubeListBuilder.create().texOffs(32, 55).addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(50, 23).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(50, 36).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.2F))
		.texOffs(32, 62).addBox(-2.5F, 4.0F, -2.5F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 13.0F, 0.0F));

		PartDefinition l_leg = partdefinition.addOrReplaceChild("l_leg", CubeListBuilder.create().texOffs(56, 0).addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(16, 54).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 54).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.2F))
		.texOffs(0, 67).addBox(0.0F, 2.0F, -3.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(46, 62).addBox(-0.5F, 4.0F, -2.5F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 13.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		torso.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		r_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		l_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		r_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		l_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}