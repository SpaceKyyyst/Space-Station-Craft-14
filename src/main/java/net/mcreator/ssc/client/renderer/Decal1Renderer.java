
package net.mcreator.ssc.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.mcreator.ssc.entity.Decal1Entity;
import net.mcreator.ssc.client.model.Modeldecal_1;

public class Decal1Renderer extends MobRenderer<Decal1Entity, LivingEntityRenderState, Modeldecal_1> {
    private final ResourceLocation entityTexture = ResourceLocation.parse("ssc_14:textures/entities/decal_1.png");

    public Decal1Renderer(EntityRendererProvider.Context context) {
        // shadowRadius = 0f — убираем тень под декалью
        super(context, new Modeldecal_1(context.bakeLayer(Modeldecal_1.LAYER_LOCATION)), 0.0f);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public void extractRenderState(Decal1Entity entity, LivingEntityRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
        return entityTexture;
    }

    // 🔑 КРИТИЧНО: должно быть TRUE, иначе модель НЕ рендерится!
    @Override
    protected boolean isBodyVisible(LivingEntityRenderState state) {
        return true;
    }
}
