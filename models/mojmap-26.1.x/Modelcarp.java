// Made with Blockbench 5.2.0
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class Modelcarp<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "carp"), "main");
	private final ModelPart carp;
	private final ModelPart head;
	private final ModelPart head_2;
	private final ModelPart tail;
	private final ModelPart tail_2;
	private final ModelPart tail_3;
	private final ModelPart fin_l;
	private final ModelPart fin_r;
	private final ModelPart fin_up;

	public Modelcarp(ModelPart root) {
		this.carp = root.getChild("carp");
		this.head = this.carp.getChild("head");
		this.head_2 = this.head.getChild("head_2");
		this.tail = this.carp.getChild("tail");
		this.tail_2 = this.tail.getChild("tail_2");
		this.tail_3 = this.tail_2.getChild("tail_3");
		this.fin_l = this.carp.getChild("fin_l");
		this.fin_r = this.carp.getChild("fin_r");
		this.fin_up = this.carp.getChild("fin_up");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition carp = partdefinition.addOrReplaceChild("carp",
				CubeListBuilder.create().texOffs(0, 0)
						.addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(36, 44)
						.mirror().addBox(0.0F, 4.0F, 1.0F, 0.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(0.0F, 19.0F, 0.0F));

		PartDefinition head = carp.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 20).addBox(-4.0F,
				-4.0F, -4.0F, 8.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -5.0F));

		PartDefinition head_2 = head.addOrReplaceChild("head_2", CubeListBuilder.create().texOffs(0, 39).addBox(-3.0F,
				-3.0F, -3.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));

		PartDefinition tail = carp.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(24, 33).addBox(-3.5F,
				-3.5F, -1.0F, 7.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));

		PartDefinition tail_2 = tail.addOrReplaceChild("tail_2", CubeListBuilder.create().texOffs(21, 44).addBox(-1.5F,
				-2.0F, -1.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

		PartDefinition tail_3 = tail_2.addOrReplaceChild("tail_3",
				CubeListBuilder.create().texOffs(40, 0).mirror()
						.addBox(0.0F, -5.0F, -1.0F, 0.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(0.0F, 0.0F, 2.0F));

		PartDefinition fin_l = carp.addOrReplaceChild("fin_l", CubeListBuilder.create(),
				PartPose.offset(5.0F, 5.0F, -4.0F));

		PartDefinition cube_r1 = fin_l.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(46, 33).mirror()
						.addBox(0.0F, 0.0F, -1.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition fin_r = carp.addOrReplaceChild("fin_r", CubeListBuilder.create(),
				PartPose.offset(-5.0F, 5.0F, -4.0F));

		PartDefinition cube_r2 = fin_r
				.addOrReplaceChild("cube_r2",
						CubeListBuilder.create().texOffs(46, 33).addBox(0.0F, 0.0F, -1.0F, 0.0F, 5.0F, 6.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition fin_up = carp.addOrReplaceChild("fin_up",
				CubeListBuilder.create().texOffs(0, 21).mirror()
						.addBox(0.0F, -6.0F, 0.0F, 0.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(0.0F, -5.0F, -4.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		carp.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
	}
}