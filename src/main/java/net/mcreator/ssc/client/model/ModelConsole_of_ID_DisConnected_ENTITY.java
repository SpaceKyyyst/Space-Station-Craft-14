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

// Made with Blockbench 5.0.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class ModelConsole_of_ID_DisConnected_ENTITY extends EntityModel<LivingEntityRenderState> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("ssc_14", "model_console_of_id_dis_connected_entity"), "main");
	public final ModelPart bb_main;

	public ModelConsole_of_ID_DisConnected_ENTITY(ModelPart root) {
		super(root);
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main",
				CubeListBuilder.create().texOffs(0, 35).addBox(-6.0F, -18.0F, 4.0F, 12.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 15).addBox(-7.0F, -13.0F, -4.0F, 14.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(52, 0)
						.addBox(-6.0F, -13.5F, -6.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(52, 0).addBox(6.0F, -13.5F, -6.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(52, 0)
						.addBox(4.0F, -13.5F, -6.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(52, 0).addBox(-4.0F, -13.5F, -6.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-7.0F, -16.0F, -7.0F, 14.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(32, 50).addBox(-6.5F, -16.25F, -6.0F, 13.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(32, 35).addBox(-7.5F, -11.0004F, -4.0019F, 15.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -13.7707F, 3.3947F, -0.3927F, 0.0F, 0.0F));
		PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(42, 15).addBox(-6.0F, -14.0F, -4.0F, 13.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.5F, -5.0F, 3.0F, -0.3927F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	public void setupAnim(LivingEntityRenderState state) {
		float limbSwing = state.walkAnimationPos;
		float limbSwingAmount = state.walkAnimationSpeed;
		float ageInTicks = state.ageInTicks;
		float netHeadYaw = state.yRot;
		float headPitch = state.xRot;

	}

}