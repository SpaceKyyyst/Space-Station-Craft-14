// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class Modelhardsuit_helmet_salvage_equipped<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "hardsuit_helmet_salvage_equipped"), "main");
	private final ModelPart helmet;

	public Modelhardsuit_helmet_salvage_equipped(ModelPart root) {
		this.helmet = root.getChild("helmet");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition helmet = partdefinition.addOrReplaceChild("helmet",
				CubeListBuilder.create().texOffs(0, 0)
						.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(32, 0)
						.addBox(-5.0F, -7.0F, -1.0F, 10.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(64, 52)
						.addBox(-5.0F, -10.0F, 1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition cube_r1 = helmet
				.addOrReplaceChild("cube_r1",
						CubeListBuilder.create().texOffs(0, 64).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 2.0F, 4.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(5.0F, -4.0F, 1.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r2 = helmet.addOrReplaceChild("cube_r2",
				CubeListBuilder.create().texOffs(58, 42).addBox(0.0F, 0.0F, -2.0F, 1.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.0F, -4.0F, 1.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition cube_r3 = helmet
				.addOrReplaceChild("cube_r3",
						CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -2.0F, -9.0F, 6.0F, 7.0F, 9.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, -8.0F, 4.0F, 0.1745F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		helmet.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
	}
}