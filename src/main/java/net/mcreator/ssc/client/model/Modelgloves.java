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
public class Modelgloves extends EntityModel<LivingEntityRenderState> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("ssc_14", "modelgloves"), "main");
	public final ModelPart r_hand;
	public final ModelPart l_hand;

	public Modelgloves(ModelPart root) {
		super(root);
		this.r_hand = root.getChild("r_hand");
		this.l_hand = root.getChild("l_hand");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition r_hand = partdefinition.addOrReplaceChild("r_hand", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.0F, -1.8F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.125F)).mirror(false), PartPose.offset(-5.0F, 4.0F, 0.0F));
		PartDefinition l_hand = partdefinition.addOrReplaceChild("l_hand", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.8F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.125F)), PartPose.offset(5.0F, 4.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	public void setupAnim(LivingEntityRenderState state) {
		float limbSwing = state.walkAnimationPos;
		float limbSwingAmount = state.walkAnimationSpeed;
		float ageInTicks = state.ageInTicks;
		float netHeadYaw = state.yRot;
		float headPitch = state.xRot;

	}
}