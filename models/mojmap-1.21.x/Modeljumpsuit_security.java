// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class Modeljumpsuit_security<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "jumpsuit_security"), "main");
	private final ModelPart jumpsuit_security;
	private final ModelPart torso;
	private final ModelPart r_arm;
	private final ModelPart l_arm;
	private final ModelPart r_leg;
	private final ModelPart l_leg;

	public Modeljumpsuit_security(ModelPart root) {
		this.jumpsuit_security = root.getChild("jumpsuit_security");
		this.torso = this.jumpsuit_security.getChild("torso");
		this.r_arm = this.jumpsuit_security.getChild("r_arm");
		this.l_arm = this.jumpsuit_security.getChild("l_arm");
		this.r_leg = this.jumpsuit_security.getChild("r_leg");
		this.l_leg = this.jumpsuit_security.getChild("l_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition jumpsuit_security = partdefinition.addOrReplaceChild("jumpsuit_security",
				CubeListBuilder.create(), PartPose.offset(0.0F, 7.2F, 0.0F));

		PartDefinition torso = jumpsuit_security.addOrReplaceChild("torso",
				CubeListBuilder.create().texOffs(0, 0)
						.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 10.0F, 4.0F, new CubeDeformation(0.25F)).texOffs(0, 14)
						.addBox(-4.0F, 10.0F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.375F)),
				PartPose.offset(0.0F, 1.8F, 0.0F));

		PartDefinition r_arm = jumpsuit_security.addOrReplaceChild("r_arm",
				CubeListBuilder.create().texOffs(16, 19).mirror()
						.addBox(-2.0F, -2.0F, -2.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false),
				PartPose.offset(-5.0F, 3.8F, 0.0F));

		PartDefinition l_arm = jumpsuit_security.addOrReplaceChild("l_arm", CubeListBuilder.create().texOffs(16, 19)
				.addBox(-1.0F, -2.0F, -2.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.25F)),
				PartPose.offset(5.0F, 3.8F, 0.0F));

		PartDefinition r_leg = jumpsuit_security.addOrReplaceChild("r_leg",
				CubeListBuilder.create().texOffs(0, 19).mirror()
						.addBox(-2.0F, -0.25F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.125F)).mirror(false),
				PartPose.offset(-2.0F, 13.05F, 0.0F));

		PartDefinition l_leg = jumpsuit_security.addOrReplaceChild("l_leg", CubeListBuilder.create().texOffs(0, 19)
				.addBox(-2.0F, -0.25F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.125F)),
				PartPose.offset(2.0F, 13.05F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		jumpsuit_security.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
	}
}