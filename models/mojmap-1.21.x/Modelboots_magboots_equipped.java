// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class Modelboots_magboots_equipped<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "boots_magboots_equipped"), "main");
	private final ModelPart r_foot;
	private final ModelPart l_foot;

	public Modelboots_magboots_equipped(ModelPart root) {
		this.r_foot = root.getChild("r_foot");
		this.l_foot = root.getChild("l_foot");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition r_foot = partdefinition.addOrReplaceChild("r_foot",
				CubeListBuilder.create().texOffs(0, 0)
						.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.175F)).texOffs(0, 13)
						.addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.25F)).texOffs(16, 0)
						.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.3F)).texOffs(0, 20)
						.addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.375F)),
				PartPose.offset(-2.0F, 13.0F, 0.0F));

		PartDefinition cube_r1 = r_foot
				.addOrReplaceChild("cube_r1",
						CubeListBuilder.create().texOffs(18, 13).addBox(-2.0F, -4.0F, -1.0F, 4.0F, 5.0F, 2.0F,
								new CubeDeformation(0.3F)),
						PartPose.offsetAndRotation(0.0F, 9.0F, 1.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition l_foot = partdefinition.addOrReplaceChild("l_foot", CubeListBuilder.create().texOffs(0, 0)
				.mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.175F)).mirror(false)
				.texOffs(0, 13).mirror().addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.25F))
				.mirror(false).texOffs(16, 0).mirror()
				.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.3F)).mirror(false).texOffs(0, 20)
				.mirror().addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.375F)).mirror(false),
				PartPose.offset(2.0F, 13.0F, 0.0F));

		PartDefinition cube_r2 = l_foot.addOrReplaceChild("cube_r2",
				CubeListBuilder.create().texOffs(18, 13).mirror()
						.addBox(-2.0F, -4.0F, -1.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.3F)).mirror(false),
				PartPose.offsetAndRotation(0.0F, 9.0F, 1.0F, -0.2618F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		r_foot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		l_foot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
	}
}