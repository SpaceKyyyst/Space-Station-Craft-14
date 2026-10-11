// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class Modelfelinid_m<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "felinid_m"), "main");
	private final ModelPart head;
	private final ModelPart cat_ears;
	private final ModelPart torso;
	private final ModelPart tail;
	private final ModelPart tail_2;
	private final ModelPart tail_3;
	private final ModelPart l_arm;
	private final ModelPart r_arm;
	private final ModelPart r_leg;
	private final ModelPart l_leg;

	public Modelfelinid_m(ModelPart root) {
		this.head = root.getChild("head");
		this.cat_ears = this.head.getChild("cat_ears");
		this.torso = root.getChild("torso");
		this.tail = this.torso.getChild("tail");
		this.tail_2 = this.tail.getChild("tail_2");
		this.tail_3 = this.tail_2.getChild("tail_3");
		this.l_arm = root.getChild("l_arm");
		this.r_arm = root.getChild("r_arm");
		this.r_leg = root.getChild("r_leg");
		this.l_leg = root.getChild("l_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(
				-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition cat_ears = head.addOrReplaceChild("cat_ears", CubeListBuilder.create().texOffs(14, 43)
				.addBox(0.0F, -2.0F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(14, 46)
				.addBox(1.0F, -3.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(14, 46).mirror()
				.addBox(-5.0F, -3.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(14, 43)
				.mirror().addBox(-5.0F, -2.0F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(1.0F, -8.0F, -0.5F));

		PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 16).addBox(
				-4.0F, 0.0F, -2.0F, 8.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition tail = torso
				.addOrReplaceChild("tail",
						CubeListBuilder.create().texOffs(0, 43).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 10.0F, 2.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition tail_2 = tail
				.addOrReplaceChild("tail_2",
						CubeListBuilder.create().texOffs(0, 50).addBox(-1.001F, -2.0F, 0.0F, 2.0F, 2.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 1.0F, 4.0F, 0.6109F, 0.0F, 0.0F));

		PartDefinition tail_3 = tail_2.addOrReplaceChild("tail_3",
				CubeListBuilder.create().texOffs(0, 57).addBox(-1.001F, 0.3486F, -0.9385F, 2.0F, 2.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -1.963F, 6.0005F, -0.3927F, 0.0F, 0.0F));

		PartDefinition l_arm = partdefinition.addOrReplaceChild("l_arm",
				CubeListBuilder.create().texOffs(0, 31)
						.addBox(-1.0F, -2.0F, -2.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(40, 26)
						.addBox(-1.0F, 6.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(5.0F, 4.0F, 0.0F));

		PartDefinition r_arm = partdefinition.addOrReplaceChild("r_arm",
				CubeListBuilder.create().texOffs(32, 0)
						.addBox(-2.0F, -2.0F, -2.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(40, 34)
						.addBox(-2.0F, 6.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-5.0F, 4.0F, 0.0F));

		PartDefinition r_leg = partdefinition.addOrReplaceChild("r_leg",
				CubeListBuilder.create().texOffs(24, 29)
						.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(40, 19)
						.addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.0F, 13.0F, 0.0F));

		PartDefinition l_leg = partdefinition.addOrReplaceChild("l_leg",
				CubeListBuilder.create().texOffs(24, 16)
						.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(40, 12)
						.addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offset(2.0F, 13.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		torso.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		l_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		r_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		r_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		l_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
	}
}