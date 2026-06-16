// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class Modelmothroach<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "mothroach"), "main");
	private final ModelPart mothroach;
	private final ModelPart head;
	private final ModelPart leg_L1;
	private final ModelPart leg_L2;
	private final ModelPart leg_L3;
	private final ModelPart leg_R1;
	private final ModelPart leg_R2;
	private final ModelPart leg_R3;

	public Modelmothroach(ModelPart root) {
		this.mothroach = root.getChild("mothroach");
		this.head = this.mothroach.getChild("head");
		this.leg_L1 = this.mothroach.getChild("leg_L1");
		this.leg_L2 = this.mothroach.getChild("leg_L2");
		this.leg_L3 = this.mothroach.getChild("leg_L3");
		this.leg_R1 = this.mothroach.getChild("leg_R1");
		this.leg_R2 = this.mothroach.getChild("leg_R2");
		this.leg_R3 = this.mothroach.getChild("leg_R3");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition mothroach = partdefinition.addOrReplaceChild("mothroach",
				CubeListBuilder.create().texOffs(0, 27)
						.addBox(-3.0F, -6.0F, -6.0F, 6.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 14)
						.addBox(-2.5F, -5.0F, -4.0F, 5.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(19, 17)
						.addBox(-1.5F, -4.5F, 5.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-3.5F, -6.0F, -4.0F, 7.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(16, 27)
						.addBox(-2.5F, -5.0F, 6.0F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = mothroach.addOrReplaceChild("head", CubeListBuilder.create().texOffs(28, 14).addBox(-1.0F,
				-4.0F, 0.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -1.0F, -8.0F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(28, 19).mirror()
						.addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(-1.0F, -4.0F, 1.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2",
				CubeListBuilder.create().texOffs(28, 19).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(2.0F, -4.0F, 1.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition leg_L1 = mothroach.addOrReplaceChild("leg_L1", CubeListBuilder.create().texOffs(28, 21).addBox(
				-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -1.5F, -2.5F));

		PartDefinition leg_L2 = mothroach.addOrReplaceChild("leg_L2", CubeListBuilder.create().texOffs(28, 21).addBox(
				-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -1.5F, -0.5F));

		PartDefinition leg_L3 = mothroach.addOrReplaceChild("leg_L3", CubeListBuilder.create().texOffs(28, 21).addBox(
				-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -1.5F, 1.5F));

		PartDefinition leg_R1 = mothroach.addOrReplaceChild("leg_R1", CubeListBuilder.create().texOffs(28, 21).addBox(
				-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -1.5F, 1.5F));

		PartDefinition leg_R2 = mothroach.addOrReplaceChild("leg_R2", CubeListBuilder.create().texOffs(28, 21)
				.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.5F, -1.5F, -0.5F));

		PartDefinition leg_R3 = mothroach.addOrReplaceChild("leg_R3", CubeListBuilder.create().texOffs(28, 21)
				.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.5F, -1.5F, -2.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		mothroach.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.head.xRot = headPitch / (180F / (float) Math.PI);
		this.leg_L3.xRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
		this.leg_R3.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
		this.leg_R1.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
		this.leg_R2.xRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
		this.leg_L1.xRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
		this.leg_L2.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
	}
}