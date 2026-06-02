// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class Modelbelt<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "belt"), "main");
	private final ModelPart torso;
	private final ModelPart r_leg;
	private final ModelPart l_leg;

	public Modelbelt(ModelPart root) {
		this.torso = root.getChild("torso");
		this.r_leg = root.getChild("r_leg");
		this.l_leg = root.getChild("l_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(
				-4.0F, 0.0F, -2.0F, 8.0F, 11.0F, 4.0F, new CubeDeformation(0.525F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition r_leg = partdefinition.addOrReplaceChild("r_leg", CubeListBuilder.create().texOffs(0, 15).addBox(
				-2.0F, 1.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(-2.0F, 13.0F, 0.0F));

		PartDefinition l_leg = partdefinition.addOrReplaceChild("l_leg", CubeListBuilder.create().texOffs(16, 15)
				.addBox(-2.0F, 1.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.5F)),
				PartPose.offset(2.0F, 13.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		torso.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		r_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		l_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
	}
}