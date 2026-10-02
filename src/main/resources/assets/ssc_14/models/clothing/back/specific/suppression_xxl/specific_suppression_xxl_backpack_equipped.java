// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class specific_suppression_xxl_backpack_equipped<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "specific_suppression_xxl_backpack_equipped"), "main");
	private final ModelPart torso;
	private final ModelPart hmg_suppression_xxl;

	public specific_suppression_xxl_backpack_equipped(ModelPart root) {
		this.torso = root.getChild("torso");
		this.hmg_suppression_xxl = this.torso.getChild("hmg_suppression_xxl");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 11.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition hmg_suppression_xxl = torso.addOrReplaceChild("hmg_suppression_xxl", CubeListBuilder.create().texOffs(0, 24).addBox(7.0F, -3.0F, -1.5F, 9.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(24, 5).addBox(6.5F, -2.5F, -1.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(24, 13).addBox(5.0F, -3.0F, -2.5F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 15).addBox(2.0F, -4.0F, -3.5F, 5.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(24, 24).addBox(-2.0F, -3.25F, -0.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.125F))
		.texOffs(10, 30).addBox(0.0F, -2.0F, 0.75F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.125F))
		.texOffs(24, 0).addBox(-4.0F, -3.0F, -1.5F, 9.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(24, 26).addBox(-1.0F, -2.0F, 1.5F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 9).addBox(-3.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 32).addBox(-2.0F, 1.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(24, 22).addBox(-8.0F, 3.0F, -0.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 30).addBox(-6.0F, -2.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(24, 29).addBox(-8.0F, -2.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 5.0F, 3.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r1 = hmg_suppression_xxl.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(10, 32).addBox(-2.0F, -3.0F, -0.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-2.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

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