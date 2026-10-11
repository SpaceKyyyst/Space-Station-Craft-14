package net.mcreator.ssc.client.model;

import net.minecraft.resources.Identifier;
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

// Made with Blockbench 5.2.0
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class Modelunath_m extends EntityModel<LivingEntityRenderState> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath("ssc_14", "modelunath_m"), "main");
	public final ModelPart head;
	public final ModelPart deco;
	public final ModelPart torso;
	public final ModelPart tail;
	public final ModelPart seg2;
	public final ModelPart seg3;
	public final ModelPart l_arm;
	public final ModelPart r_arm;
	public final ModelPart r_leg;
	public final ModelPart l_leg;

	public Modelunath_m(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.deco = this.head.getChild("deco");
		this.torso = root.getChild("torso");
		this.tail = this.torso.getChild("tail");
		this.seg2 = this.tail.getChild("seg2");
		this.seg3 = this.seg2.getChild("seg3");
		this.l_arm = root.getChild("l_arm");
		this.r_arm = root.getChild("r_arm");
		this.r_leg = root.getChild("r_leg");
		this.l_leg = root.getChild("l_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition head = partdefinition.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(48, 0).addBox(-2.0F, -3.0F, -6.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 2.0F, 0.0F));
		PartDefinition deco = head.addOrReplaceChild("deco", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -8.0F));
		PartDefinition cube_r1 = deco.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(52, 57).addBox(0.0F, -5.0F, 0.0F, 6.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 1.0F, 9.0F, 0.0F, -2.3562F, 0.0F));
		PartDefinition cube_r2 = deco.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(52, 57).addBox(0.0F, -5.0F, 0.0F, 6.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 1.0F, 9.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));
		PartDefinition tail = torso.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(23, 54).addBox(-2.0F, -2.0F, -0.5F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.0F, 1.0F, -0.7854F, 0.0F, 0.0F));
		PartDefinition seg2 = tail.addOrReplaceChild("seg2", CubeListBuilder.create().texOffs(12, 37).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.5F, 0.1963F, 0.0F, 0.0F));
		PartDefinition seg3 = seg2.addOrReplaceChild("seg3", CubeListBuilder.create().texOffs(25, 42).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.5F, 0.3927F, 0.0F, 0.0F));
		PartDefinition l_arm = partdefinition.addOrReplaceChild("l_arm",
				CubeListBuilder.create().texOffs(0, 31).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(40, 26).addBox(-1.0F, 6.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(5.0F, 4.0F, 0.0F));
		PartDefinition r_arm = partdefinition.addOrReplaceChild("r_arm",
				CubeListBuilder.create().texOffs(32, 0).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(40, 34).addBox(-2.0F, 6.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-5.0F, 4.0F, 0.0F));
		PartDefinition r_leg = partdefinition.addOrReplaceChild("r_leg",
				CubeListBuilder.create().texOffs(24, 29).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(40, 19).addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.0F, 13.0F, 0.0F));
		PartDefinition l_leg = partdefinition.addOrReplaceChild("l_leg",
				CubeListBuilder.create().texOffs(24, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(40, 12).addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offset(2.0F, 13.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public void setupAnim(LivingEntityRenderState state) {
		float limbSwing = state.walkAnimationPos;
		float limbSwingAmount = state.walkAnimationSpeed;
		float ageInTicks = state.ageInTicks;
		float netHeadYaw = state.yRot;
		float headPitch = state.xRot;

	}
}