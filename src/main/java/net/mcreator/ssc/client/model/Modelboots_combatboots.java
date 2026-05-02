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

// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class Modelboots_combatboots extends EntityModel<LivingEntityRenderState> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("ssc_14", "modelboots_combatboots"), "main");
	public final ModelPart l_foot;
	public final ModelPart r_foot;

	public Modelboots_combatboots(ModelPart root) {
		super(root);
		this.l_foot = root.getChild("l_foot");
		this.r_foot = root.getChild("r_foot");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition l_foot = partdefinition.addOrReplaceChild("l_foot",
				CubeListBuilder.create().texOffs(0, 14).addBox(-2.0F, 6.5F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.25F)).texOffs(0, 0).addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.25F)).texOffs(0, 7)
						.addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.5F)).texOffs(16, 14).addBox(-2.0F, 6.5F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.5F)),
				PartPose.offset(2.0F, 13.0F, 0.0F));
		PartDefinition r_foot = partdefinition.addOrReplaceChild("r_foot",
				CubeListBuilder.create().texOffs(0, 14).mirror().addBox(-2.0F, 6.5F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false).texOffs(0, 0).mirror().addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.25F))
						.mirror(false).texOffs(0, 7).mirror().addBox(-2.0F, 9.0F, -3.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.5F)).mirror(false).texOffs(16, 14).mirror().addBox(-2.0F, 6.5F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.5F))
						.mirror(false),
				PartPose.offset(-2.0F, 13.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	public void setupAnim(LivingEntityRenderState state) {
		float limbSwing = state.walkAnimationPos;
		float limbSwingAmount = state.walkAnimationSpeed;
		float ageInTicks = state.ageInTicks;
		float netHeadYaw = state.yRot;
		float headPitch = state.xRot;

	}
}