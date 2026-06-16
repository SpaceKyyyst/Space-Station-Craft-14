package net.mcreator.ssc.client.model;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.EntityModel;

// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class Modelmothroach extends EntityModel<LivingEntityRenderState> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("ssc_14", "modelmothroach"), "main");
	public final ModelPart mothroach;
	public final ModelPart head;
	public final ModelPart leg_L1;
	public final ModelPart leg_L2;
	public final ModelPart leg_L3;
	public final ModelPart leg_R1;
	public final ModelPart leg_R2;
	public final ModelPart leg_R3;

	public Modelmothroach(ModelPart root) {
		super(root);
		this.mothroach = root.getChild("mothroach");
		this.head = this.mothroach.getChild("head");
		this.leg_L1 = this.mothroach.getChild("leg_L1");
		this.leg_L2 = this.mothroach.getChild("leg_L2");
		this.leg_L3 = this.mothroach.getChild("leg_L3");
		this.leg_R1 = this.mothroach.getChild("leg_R1");
		this.leg_R2 = this.mothroach.getChild("leg_R2");
		this.leg_R3 = this.mothroach.getChild("leg_R3");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition mothroach = partdefinition.addOrReplaceChild("mothroach",
				CubeListBuilder.create().texOffs(0, 27).addBox(-3.0F, -6.0F, -6.0F, 6.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 14).addBox(-2.5F, -5.0F, -4.0F, 5.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(19, 17)
						.addBox(-1.5F, -4.5F, 5.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-3.5F, -6.0F, -4.0F, 7.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(16, 27)
						.addBox(-2.5F, -5.0F, 6.0F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition head = mothroach.addOrReplaceChild("head", CubeListBuilder.create().texOffs(28, 14).addBox(-1.0F, -4.0F, 0.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -1.0F, -8.0F));
		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(28, 19).mirror().addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(-1.0F, -4.0F, 1.0F, 0.0F, 0.0F, 0.1745F));
		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(28, 19).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -4.0F, 1.0F, 0.0F, 0.0F, -0.1745F));
		PartDefinition leg_L1 = mothroach.addOrReplaceChild("leg_L1", CubeListBuilder.create().texOffs(28, 21).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -1.5F, -2.5F));
		PartDefinition leg_L2 = mothroach.addOrReplaceChild("leg_L2", CubeListBuilder.create().texOffs(28, 21).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -1.5F, -0.5F));
		PartDefinition leg_L3 = mothroach.addOrReplaceChild("leg_L3", CubeListBuilder.create().texOffs(28, 21).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -1.5F, 1.5F));
		PartDefinition leg_R1 = mothroach.addOrReplaceChild("leg_R1", CubeListBuilder.create().texOffs(28, 21).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -1.5F, 1.5F));
		PartDefinition leg_R2 = mothroach.addOrReplaceChild("leg_R2", CubeListBuilder.create().texOffs(28, 21).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -1.5F, -0.5F));
		PartDefinition leg_R3 = mothroach.addOrReplaceChild("leg_R3", CubeListBuilder.create().texOffs(28, 21).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -1.5F, -2.5F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public void setupAnim(LivingEntityRenderState state) {
		float limbSwing = state.walkAnimationPos;
		float limbSwingAmount = state.walkAnimationSpeed;
		float ageInTicks = state.ageInTicks;
		float netHeadYaw = state.yRot;
		float headPitch = state.xRot;

		this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.head.xRot = headPitch / (180F / (float) Math.PI);
		this.leg_L3.xRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
		this.leg_R3.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
		this.leg_R1.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
		this.leg_R2.xRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
		this.leg_L1.xRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
		this.leg_L2.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
	}
}