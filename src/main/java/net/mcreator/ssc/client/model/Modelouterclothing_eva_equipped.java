package net.mcreator.ssc.client.model;

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
public class Modelouterclothing_eva_equipped extends EntityModel<LivingEntityRenderState> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("ssc_14", "modelouterclothing_eva_equipped"), "main");
	public final ModelPart torso;
	public final ModelPart r_arm;
	public final ModelPart l_arm;
	public final ModelPart r_leg;
	public final ModelPart l_leg;

	public Modelouterclothing_eva_equipped(ModelPart root) {
		super(root);
		this.torso = root.getChild("torso");
		this.r_arm = root.getChild("r_arm");
		this.l_arm = root.getChild("l_arm");
		this.r_leg = root.getChild("r_leg");
		this.l_leg = root.getChild("l_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition torso = partdefinition.addOrReplaceChild("torso",
				CubeListBuilder.create().texOffs(0, 21).addBox(-4.0F, -11.0F, -3.0F, 8.0F, 11.0F, 6.0F, new CubeDeformation(0.5F)).texOffs(0, 0).addBox(-4.0F, -11.0F, -3.0F, 8.0F, 15.0F, 6.0F, new CubeDeformation(0.725F)),
				PartPose.offset(0.0F, 13.0F, 0.0F));
		PartDefinition r_arm = partdefinition.addOrReplaceChild("r_arm",
				CubeListBuilder.create().texOffs(28, 26).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.475F)).texOffs(42, 26).addBox(-2.0F, 6.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)),
				PartPose.offset(-5.0F, 4.0F, 0.0F));
		PartDefinition l_arm = partdefinition.addOrReplaceChild("l_arm",
				CubeListBuilder.create().texOffs(0, 38).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.475F)).texOffs(44, 0).addBox(-1.0F, 6.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)),
				PartPose.offset(5.0F, 4.0F, 0.0F));
		PartDefinition r_leg = partdefinition.addOrReplaceChild("r_leg",
				CubeListBuilder.create().texOffs(28, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.475F)).texOffs(14, 38).addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.5F)),
				PartPose.offset(-2.0F, 13.0F, 0.0F));
		PartDefinition l_leg = partdefinition.addOrReplaceChild("l_leg",
				CubeListBuilder.create().texOffs(28, 13).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.475F)).texOffs(32, 38).addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.5F)),
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