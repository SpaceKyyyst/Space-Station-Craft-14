// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class specific_m-90gl_backpack_equipped<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "specific_m-90gl_backpack_equipped"), "main");
	private final ModelPart torso;

	public specific_m-90gl_backpack_equipped(ModelPart root) {
		this.torso = root.getChild("torso");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 5).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 11.0F, 4.0F, new CubeDeformation(0.5F))
		.texOffs(22, 27).addBox(0.5412F, 7.3066F, 2.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition cube_r1 = torso.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(12, 28).addBox(0.0F, -1.0F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0021F, 6.9895F, 4.5F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r2 = torso.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 24).addBox(-0.5F, -2.5F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.5855F, 10.0974F, 3.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r3 = torso.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 24).addBox(-6.0F, -2.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(7.1669F, 12.2158F, 3.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r4 = torso.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(22, 24).addBox(0.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(2.9958F, 3.9937F, 3.0F, 0.0F, 0.0F, 1.1781F));

		PartDefinition cube_r5 = torso.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(8, 28).addBox(0.0F, -0.5F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0719F, 3.611F, 3.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r6 = torso.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(24, 15).addBox(-1.5F, -0.5F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4186F, 3.8816F, 3.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r7 = torso.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(24, 7).addBox(-3.0F, -1.0F, 0.0F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6892F, 7.4651F, 3.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r8 = torso.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(4, 28).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.1512F, 7.2737F, 3.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r9 = torso.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(24, 18).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.9197F, 7.6236F, 3.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r10 = torso.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(24, 5).addBox(-3.5F, 0.0F, -1.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.1617F, 2.2717F, 3.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r11 = torso.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(24, 12).addBox(-1.5F, -0.5F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.742F, 0.6617F, 3.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r12 = torso.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(24, 9).addBox(-3.0F, -1.0F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-4.3952F, 0.9323F, 3.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition cube_r13 = torso.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -1.5F, -1.0F, 18.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1913F, 5.5381F, 3.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r14 = torso.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 28).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.3942F, 1.4406F, 3.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r15 = torso.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(16, 20).addBox(-3.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(0, 20).addBox(-3.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.2325F, 5.169F, 3.0F, 0.0F, 0.0F, 0.3927F));

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