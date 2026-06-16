package net.mcreator.ssc.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.ssc.entity.MouseGrayEntity;
import net.mcreator.ssc.client.model.Modelmouses;

public class MouseGrayRenderer extends MobRenderer<MouseGrayEntity, LivingEntityRenderState, Modelmouses> {
	private MouseGrayEntity entity = null;
	private final ResourceLocation entityTexture = ResourceLocation.parse("ssc_14:textures/entities/mouse_gray_texture.png");

	public MouseGrayRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelmouses(context.bakeLayer(Modelmouses.LAYER_LOCATION)), 0.1f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void extractRenderState(MouseGrayEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		this.entity = entity;
	}

	@Override
	public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
		return entityTexture;
	}
}