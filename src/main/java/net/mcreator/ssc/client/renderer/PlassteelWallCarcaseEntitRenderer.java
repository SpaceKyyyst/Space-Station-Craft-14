package net.mcreator.ssc.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.ssc.entity.PlassteelWallCarcaseEntitEntity;
import net.mcreator.ssc.client.model.Modelframe_of_plasstal_wall_JAVA;

public class PlassteelWallCarcaseEntitRenderer extends MobRenderer<PlassteelWallCarcaseEntitEntity, LivingEntityRenderState, Modelframe_of_plasstal_wall_JAVA> {
	private PlassteelWallCarcaseEntitEntity entity = null;

	public PlassteelWallCarcaseEntitRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelframe_of_plasstal_wall_JAVA(context.bakeLayer(Modelframe_of_plasstal_wall_JAVA.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void extractRenderState(PlassteelWallCarcaseEntitEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		this.entity = entity;
	}

	@Override
	public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
		return ResourceLocation.parse("ssc_14:textures/entities/frame_of_plasstal_wall_java_texture.png");
	}
}