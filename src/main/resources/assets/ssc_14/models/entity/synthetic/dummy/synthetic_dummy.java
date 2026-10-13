// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class synthetic_dummy<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "synthetic_dummy"), "main");
	private final ModelPart synthetic_dummy;
	private final ModelPart head;
	private final ModelPart torso;
	private final ModelPart r_arm;
	private final ModelPart l_arm;
	private final ModelPart r_leg;
	private final ModelPart l_leg;

	public synthetic_dummy(ModelPart root) {
		this.synthetic_dummy = root.getChild("synthetic_dummy");
		this.head = this.synthetic_dummy.getChild("head");
		this.torso = this.synthetic_dummy.getChild("torso");
		this.r_arm = this.synthetic_dummy.getChild("r_arm");
		this.l_arm = this.synthetic_dummy.getChild("l_arm");
		this.r_leg = this.synthetic_dummy.getChild("r_leg");
		this.l_leg = this.synthetic_dummy.getChild("l_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition synthetic_dummy = partdefinition.addOrReplaceChild("synthetic_dummy", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = synthetic_dummy.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -22.0F, 0.0F));

		PartDefinition torso = synthetic_dummy.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -22.0F, 0.0F));

		PartDefinition r_arm = synthetic_dummy.addOrReplaceChild("r_arm", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(40, 34).addBox(-2.0F, 6.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -20.0F, 0.0F));

		PartDefinition l_arm = synthetic_dummy.addOrReplaceChild("l_arm", CubeListBuilder.create().texOffs(0, 31).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(40, 26).addBox(-1.0F, 6.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -20.0F, 0.0F));

		PartDefinition r_leg = synthetic_dummy.addOrReplaceChild("r_leg", CubeListBuilder.create().texOffs(24, 29).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(40, 19).addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -11.0F, 0.0F));

		PartDefinition l_leg = synthetic_dummy.addOrReplaceChild("l_leg", CubeListBuilder.create().texOffs(24, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(40, 12).addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -11.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		synthetic_dummy.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}