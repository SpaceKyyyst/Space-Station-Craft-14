// Made with Blockbench 5.0.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class ModelConsole_of_ID_DisConnected_ENTITY<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "console_of_id_disconnected_entity"), "main");
	private final ModelPart bb_main;

	public ModelConsole_of_ID_DisConnected_ENTITY(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main",
				CubeListBuilder.create().texOffs(0, 35)
						.addBox(-6.0F, -18.0F, 4.0F, 12.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 15)
						.addBox(-7.0F, -13.0F, -4.0F, 14.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(52, 0)
						.addBox(-6.0F, -13.5F, -6.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(52, 0)
						.addBox(6.0F, -13.5F, -6.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(52, 0)
						.addBox(4.0F, -13.5F, -6.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(52, 0)
						.addBox(-4.0F, -13.5F, -6.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-7.0F, -16.0F, -7.0F, 14.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(32, 50)
						.addBox(-6.5F, -16.25F, -6.0F, 13.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(32, 35).addBox(-7.5F, -11.0004F, -4.0019F, 15.0F, 11.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -13.7707F, 3.3947F, -0.3927F, 0.0F, 0.0F));

		PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2",
				CubeListBuilder.create().texOffs(42, 15).addBox(-6.0F, -14.0F, -4.0F, 13.0F, 14.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.5F, -5.0F, 3.0F, -0.3927F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}