package net.mcreator.ssc.client.renderer;

import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.ssc.entity.MouseWhiteEntity;
import net.mcreator.ssc.client.model.Modelmouses;

public class MouseWhiteRenderer extends MobRenderer<MouseWhiteEntity, LivingEntityRenderState, Modelmouses> {
	private final Identifier entityTexture = Identifier.parse("ssc_14:textures/entities/mouse_white_texture.png");

	public MouseWhiteRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelmouses(context.bakeLayer(Modelmouses.LAYER_LOCATION)), 0.1f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void extractRenderState(MouseWhiteEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState state) {
		return entityTexture;
	}
}