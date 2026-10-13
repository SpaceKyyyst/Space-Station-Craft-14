// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class suit_eva_prisoner_equipped<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "suit_eva_prisoner_equipped"), "main");
	private final ModelPart torso;
	private final ModelPart r_arm;
	private final ModelPart l_arm;
	private final ModelPart r_leg;
	private final ModelPart l_leg;

	public suit_eva_prisoner_equipped(ModelPart root) {
		this.torso = root.getChild("torso");
		this.r_arm = root.getChild("r_arm");
		this.l_arm = root.getChild("l_arm");
		this.r_leg = root.getChild("r_leg");
		this.l_leg = root.getChild("l_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 11.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(32, 0).addBox(-4.0F, 4.0F, -3.0F, 8.0F, 11.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition r_arm = partdefinition.addOrReplaceChild("r_arm", CubeListBuilder.create().texOffs(32, 17).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(28, 33).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-6.0F, 4.0F, 0.0F));

		PartDefinition l_arm = partdefinition.addOrReplaceChild("l_arm", CubeListBuilder.create().texOffs(28, 33).mirror().addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
		.texOffs(32, 17).mirror().addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(6.0F, 4.0F, 0.0F));

		PartDefinition r_leg = partdefinition.addOrReplaceChild("r_leg", CubeListBuilder.create().texOffs(44, 33).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 49).addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(44, 46).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.2F))
		.texOffs(18, 49).addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.0F, 13.0F, 0.0F));

		PartDefinition l_leg = partdefinition.addOrReplaceChild("l_leg", CubeListBuilder.create().texOffs(18, 49).mirror().addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.25F)).mirror(false)
		.texOffs(44, 46).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(0, 49).mirror().addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(44, 33).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 13.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		torso.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		r_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		l_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		r_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		l_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}