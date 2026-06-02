// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class Modelheadsets<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "headsets"), "main");
	private final ModelPart headset_security;

	public Modelheadsets(ModelPart root) {
		this.headset_security = root.getChild("headset_security");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition headset_security = partdefinition.addOrReplaceChild("headset_security",
				CubeListBuilder.create().texOffs(0, 5)
						.addBox(-5.0F, -5.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, -5)
						.addBox(-4.1F, -6.0F, -4.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 2.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		headset_security.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
	}
}