// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class specific_wt550_suitstorage_equipped<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "specific_wt550_suitstorage_equipped"), "main");
	private final ModelPart torso;

	public specific_wt550_suitstorage_equipped(ModelPart root) {
		this.torso = root.getChild("torso");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition cube_r1 = torso.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 19).addBox(-0.5F, -2.5F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.6569F, 11.364F, 4.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r2 = torso.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(22, 20).addBox(-0.5F, -1.5F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.6569F, 9.9497F, 4.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r3 = torso.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(24, 9).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.9497F, 9.2426F, 4.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r4 = torso.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(12, 21).addBox(0.0F, -0.5F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.2426F, 6.4142F, 4.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r5 = torso.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(6, 21).addBox(0.0F, 0.0F, -0.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 6.4142F, 4.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r6 = torso.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(24, 7).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0607F, 6.7678F, 4.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r7 = torso.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(16, 20).addBox(-1.0F, -2.0F, -0.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.2426F, 5.0F, 4.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r8 = torso.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(6, 19).addBox(-2.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offsetAndRotation(-3.6517F, 3.409F, 4.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r9 = torso.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(24, 11).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.7678F, 0.4038F, 4.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r10 = torso.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(16, 15).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.182F, 7.4749F, 4.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r11 = torso.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 15).addBox(-3.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.7071F, 4.2929F, 4.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r12 = torso.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(24, 0).addBox(-0.5F, -1.5F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8284F, 1.4645F, 4.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r13 = torso.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(24, 5).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.8891F, 0.4038F, 4.0F, 0.0F, 0.0F, 0.7854F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		torso.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}