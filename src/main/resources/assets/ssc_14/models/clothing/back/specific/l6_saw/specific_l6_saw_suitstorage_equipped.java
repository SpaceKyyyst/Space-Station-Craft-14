// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class specific_l6_saw_suitstorage_equipped<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "specific_l6_saw_suitstorage_equipped"), "main");
	private final ModelPart torso;

	public specific_l6_saw_suitstorage_equipped(ModelPart root) {
		this.torso = root.getChild("torso");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition cube_r1 = torso.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(20, 25).addBox(-2.0F, 0.0F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(7.6569F, 12.8284F, 4.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r2 = torso.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(24, 13).addBox(0.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(2.7071F, 12.1213F, 4.5F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r3 = torso.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(26, 19).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.2929F, 12.1213F, 4.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r4 = torso.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(24, 0).addBox(-3.5F, -0.5F, -1.0F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.4142F, 12.1213F, 4.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r5 = torso.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 21).addBox(-3.5F, -1.5F, -1.5F, 7.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.8284F, 10.7071F, 4.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r6 = torso.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(24, 7).addBox(-3.5F, -0.5F, -0.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(2.8839F, 6.9948F, 4.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r7 = torso.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(24, 3).addBox(-2.0F, -0.5F, -1.5F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.6464F, 6.1109F, 4.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r8 = torso.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(24, 9).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5858F, 7.1716F, 4.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r9 = torso.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(20, 21).addBox(-4.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.7175F, 3.9896F, 4.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r10 = torso.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 15).addBox(-5.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.6568F, 1.5147F, 4.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r11 = torso.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 27).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.1924F, -1.3137F, 4.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r12 = torso.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 19).addBox(-6.0F, -0.5F, -0.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0711F, 1.5147F, 4.5F, 0.0F, 0.0F, 0.7854F));

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