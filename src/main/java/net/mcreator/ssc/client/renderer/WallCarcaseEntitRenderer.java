package net.mcreator.ssc.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.ssc.entity.WallCarcaseEntitEntity;
import net.mcreator.ssc.client.model.Modelframe_of_wall_JAVA;

public class WallCarcaseEntitRenderer extends MobRenderer<WallCarcaseEntitEntity, LivingEntityRenderState, Modelframe_of_wall_JAVA> {
	private WallCarcaseEntitEntity entity = null;

	public WallCarcaseEntitRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelframe_of_wall_JAVA(context.bakeLayer(Modelframe_of_wall_JAVA.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void extractRenderState(WallCarcaseEntitEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		this.entity = entity;
	}

	@Override
	public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
		return ResourceLocation.parse("ssc_14:textures/entities/texture_of_frame_wall_ent.png");
	}
}