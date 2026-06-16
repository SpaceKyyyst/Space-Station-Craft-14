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
public class Modelmouses extends EntityModel<LivingEntityRenderState> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("ssc_14", "modelmouses"), "main");
	public final ModelPart Mouse;
	public final ModelPart Head;
	public final ModelPart Leg_LF;
	public final ModelPart Leg_RF;
	public final ModelPart Leg_LB;
	public final ModelPart Leg_RB;

	public Modelmouses(ModelPart root) {
		super(root);
		this.Mouse = root.getChild("Mouse");
		this.Head = this.Mouse.getChild("Head");
		this.Leg_LF = this.Mouse.getChild("Leg_LF");
		this.Leg_RF = this.Mouse.getChild("Leg_RF");
		this.Leg_LB = this.Mouse.getChild("Leg_LB");
		this.Leg_RB = this.Mouse.getChild("Leg_RB");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition Mouse = partdefinition.addOrReplaceChild("Mouse",
				CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 15).addBox(0.0F, -3.0F, 3.0F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 22.0F, 0.0F));
		PartDefinition Head = Mouse.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -2.0F));
		PartDefinition cube_r1 = Head.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(10, 15).mirror().addBox(-4.0F, -2.0F, -2.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(10, 15).addBox(0.0F, -2.0F, -2.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
						.texOffs(0, 8).addBox(-2.0F, -2.0F, -5.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(16, 10).addBox(-1.0F, -2.5F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.5F, 0.0F, 2.0F, 0.7854F, 0.0F, 0.0F));
		PartDefinition Leg_LF = Mouse.addOrReplaceChild("Leg_LF", CubeListBuilder.create().texOffs(16, 8).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 1.0F, -1.5F));
		PartDefinition Leg_RF = Mouse.addOrReplaceChild("Leg_RF", CubeListBuilder.create().texOffs(16, 8).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 1.0F, -1.5F));
		PartDefinition Leg_LB = Mouse.addOrReplaceChild("Leg_LB", CubeListBuilder.create().texOffs(16, 8).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 1.0F, 2.5F));
		PartDefinition Leg_RB = Mouse.addOrReplaceChild("Leg_RB", CubeListBuilder.create().texOffs(16, 8).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 1.0F, 2.5F));
		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	public void setupAnim(LivingEntityRenderState state) {
		float limbSwing = state.walkAnimationPos;
		float limbSwingAmount = state.walkAnimationSpeed;
		float ageInTicks = state.ageInTicks;
		float netHeadYaw = state.yRot;
		float headPitch = state.xRot;

		this.Head.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.Head.xRot = headPitch / (180F / (float) Math.PI);
		this.Leg_LF.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;
		this.Leg_LB.xRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
		this.Leg_RF.xRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
		this.Leg_RB.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;
	}
}