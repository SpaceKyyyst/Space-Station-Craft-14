// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class Modelgloves_equipped<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "gloves_equipped"), "main");
	private final ModelPart r_hand;
	private final ModelPart l_hand;

	public Modelgloves_equipped(ModelPart root) {
		this.r_hand = root.getChild("r_hand");
		this.l_hand = root.getChild("l_hand");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition r_hand = partdefinition.addOrReplaceChild("r_hand",
				CubeListBuilder.create().texOffs(0, 0).mirror()
						.addBox(-2.0F, 6.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.05F)).mirror(false),
				PartPose.offset(-5.0F, 4.0F, 0.0F));

		PartDefinition l_hand = partdefinition.addOrReplaceChild("l_hand", CubeListBuilder.create().texOffs(0, 0)
				.addBox(-1.0F, 6.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.05F)),
				PartPose.offset(5.0F, 4.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		r_hand.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		l_hand.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
	}
}