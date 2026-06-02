// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class Modelcloaks_cap<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "cloaks_cap"), "main");
	private final ModelPart head;
	private final ModelPart torso;
	private final ModelPart r_arm;
	private final ModelPart l_arm;
	private final ModelPart r_leg;
	private final ModelPart l_leg;

	public Modelcloaks_cap(ModelPart root) {
		this.head = root.getChild("head");
		this.torso = root.getChild("torso");
		this.r_arm = root.getChild("r_arm");
		this.l_arm = root.getChild("l_arm");
		this.r_leg = root.getChild("r_leg");
		this.l_leg = root.getChild("l_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(
				-4.0F, -2.0F, -1.0F, 8.0F, 3.0F, 5.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(
				-4.0F, 0.0F, -2.0F, 8.0F, 11.0F, 5.0F, new CubeDeformation(0.76F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition r_arm = partdefinition.addOrReplaceChild("r_arm", CubeListBuilder.create().texOffs(0, 24)
				.addBox(-2.0F, -2.0F, -2.0F, 3.0F, 16.0F, 4.0F, new CubeDeformation(0.75F)),
				PartPose.offset(-5.0F, 4.0F, 0.0F));

		PartDefinition l_arm = partdefinition.addOrReplaceChild("l_arm",
				CubeListBuilder.create().texOffs(0, 24).mirror()
						.addBox(-1.0F, -2.0F, -2.0F, 3.0F, 16.0F, 4.0F, new CubeDeformation(0.75F)).mirror(false),
				PartPose.offset(5.0F, 4.0F, 0.0F));

		PartDefinition r_leg = partdefinition.addOrReplaceChild("r_leg", CubeListBuilder.create().texOffs(14, 24)
				.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.76F)),
				PartPose.offset(-2.0F, 13.0F, 0.0F));

		PartDefinition l_leg = partdefinition.addOrReplaceChild("l_leg",
				CubeListBuilder.create().texOffs(14, 24).mirror()
						.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.76F)).mirror(false),
				PartPose.offset(2.0F, 13.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		torso.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		r_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		l_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		r_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		l_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
	}
}