
package net.mcreator.ssc.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Pose;

import net.mcreator.ssc.entity.CorpseEntity;
import net.mcreator.ssc.client.model.Modelhuman_m;

public class CorpseRenderer extends MobRenderer<CorpseEntity, LivingEntityRenderState, Modelhuman_m> {
    private static final ResourceLocation TEXTURE = ResourceLocation.parse("ssc_14:textures/entities/human_m_texture.png");

    public CorpseRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelhuman_m(context.bakeLayer(Modelhuman_m.LAYER_LOCATION)), 0.4f);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public void extractRenderState(CorpseEntity entity, LivingEntityRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        
        // 🔧 Принудительно устанавливаем позу для рендера
        if (entity.getPose() == Pose.SWIMMING) {
            // Некоторые модели автоматически учитывают позу, но если нет — можно добавить кастомную логику
            // Например, поворот модели на 90 градусов вокруг оси X для "лежания"
        }
    }

    @Override
    public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
        return TEXTURE;
    }
}
