package net.mcreator.ssc.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.ssc.entity.IDConsoleENTITYEntity;
import net.mcreator.ssc.client.model.ModelConsole_of_ID_DisConnected_ENTITY;

public class IDConsoleENTITYRenderer extends MobRenderer<IDConsoleENTITYEntity, LivingEntityRenderState, ModelConsole_of_ID_DisConnected_ENTITY> {
	private IDConsoleENTITYEntity entity = null;

	public IDConsoleENTITYRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelConsole_of_ID_DisConnected_ENTITY(context.bakeLayer(ModelConsole_of_ID_DisConnected_ENTITY.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void extractRenderState(IDConsoleENTITYEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		this.entity = entity;
	}

	@Override
	public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
		return ResourceLocation.parse("ssc_14:textures/entities/id_console_texture_desconnected_entity.png");
	}
}