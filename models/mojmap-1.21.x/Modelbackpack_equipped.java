// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class Modelbackpack_equipped<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "backpack_equipped"), "main");
	private final ModelPart torso;

	public Modelbackpack_equipped(ModelPart root) {
		this.torso = root.getChild("torso");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition torso = partdefinition.addOrReplaceChild("torso",
				CubeListBuilder.create().texOffs(0, 0)
						.addBox(-4.0F, -11.0F, 2.0F, 8.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(24, 0)
						.addBox(-5.0F, -5.0F, 2.0F, 10.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 15)
						.addBox(-4.0F, -11.0F, -2.0F, 8.0F, 11.0F, 4.0F, new CubeDeformation(0.25F)),
				PartPose.offset(0.0F, 13.0F, 0.0F));

		PartDefinition cube_r1 = torso.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(24, 7).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 5.0F, 4.0F,
						new CubeDeformation(0.025F)),
				PartPose.offsetAndRotation(0.0F, -11.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		torso.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
	}
}