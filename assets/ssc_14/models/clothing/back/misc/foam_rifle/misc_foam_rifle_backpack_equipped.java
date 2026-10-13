// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class misc_foam_rifle_backpack_equipped<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "misc_foam_rifle_backpack_equipped"), "main");
	private final ModelPart torso;

	public misc_foam_rifle_backpack_equipped(ModelPart root) {
		this.torso = root.getChild("torso");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 11.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition cube_r1 = torso.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(24, 5).addBox(0.0F, -1.0F, -1.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-9.7782F, 1.8787F, 3.5F, 0.0F, 0.0F, 0.3491F));

		PartDefinition cube_r2 = torso.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 24).addBox(-2.5F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.4246F, 0.818F, 3.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r3 = torso.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 19).addBox(-4.0F, -1.5F, -1.0F, 8.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5962F, 0.1109F, 3.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r4 = torso.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(24, 8).addBox(0.0F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-2.0F, 1.1716F, 3.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r5 = torso.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(24, 12).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.1213F, 4.7071F, 3.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r6 = torso.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(24, 0).addBox(-2.0F, -2.5F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3535F, 4.3535F, 3.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r7 = torso.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(20, 19).addBox(-2.5F, -1.0F, -1.5F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.182F, 7.182F, 3.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r8 = torso.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 15).addBox(-4.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9497F, 7.5355F, 3.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r9 = torso.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(24, 14).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.6066F, 11.0711F, 3.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r10 = torso.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(14, 24).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(8.253F, 12.8388F, 3.5F, 0.0F, 0.0F, 0.7854F));

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