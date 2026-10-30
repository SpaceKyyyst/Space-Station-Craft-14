
package net.ssc14.client.renderer;

import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.fx.FXHelper;
import com.lowdragmc.photon.client.fx.EntityEffectExecutor;
import com.lowdragmc.photon.client.fx.FXRuntime;

import com.mojang.logging.LogUtils;

import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.multiplayer.ClientLevel;

import net.ssc14.content.entity.TestSunEntity;

import org.slf4j.Logger;

import java.util.IdentityHashMap;
import java.util.Map;

public class TestSunEntityRenderer extends MobRenderer<
        TestSunEntity,
        LivingEntityRenderState,
        TestSunEntityRenderer.TestSunModel> {

    private static final Logger LOGGER = LogUtils.getLogger();

    private final Identifier entityTexture =
            Identifier.parse("ssc_14:textures/entities/chair_entiti.png");

    // Храним исполнители, чтобы не терять их после запуска.
    private static final Map<TestSunEntity, EntityEffectExecutor>
            ACTIVE_EXECUTORS = new IdentityHashMap<>();

    public TestSunEntityRenderer(EntityRendererProvider.Context context) {
        super(
                context,
                new TestSunModel(
                        context.bakeLayer(TestSunModel.LAYER_LOCATION)
                ),
                0.5f
        );
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public void extractRenderState(
            TestSunEntity entity,
            LivingEntityRenderState state,
            float partialTicks
    ) {
        super.extractRenderState(entity, state, partialTicks);
        initPhotonEffect(entity);
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return entityTexture;
    }

    /**
     * Запускает Photon FX, привязанный к конкретной сущности.
     */
    private void initPhotonEffect(TestSunEntity entity) {
        if (ACTIVE_EXECUTORS.containsKey(entity)) {
            return;
        }

        if (!(entity.level() instanceof ClientLevel clientLevel)) {
            return;
        }

        Identifier fxId = Identifier.parse("ssc_14:sun");
        FX fx = FXHelper.getFX(fxId);

        if (fx == null) {
            LOGGER.warn("Photon FX ssc_14:sun not found");
            return;
        }

        EntityEffectExecutor executor = new EntityEffectExecutor(
                fx,
                clientLevel,
                entity,
                EntityEffectExecutor.AutoRotate.NONE
        );

        executor.setOffset(0, -0.25, 0);
        executor.setRotation(0, 90, 0);
        executor.setForcedDeath(true);
        executor.setAllowMulti(false);

        // Запуск после настройки всех параметров.
        executor.start();

        FXRuntime runtime = executor.getRuntime();

        if (runtime == null) {
            LOGGER.warn(
                    "Photon FX runtime was not created for entity {}",
                    entity.getId()
            );
            return;
        }

        ACTIVE_EXECUTORS.put(entity, executor);

        LOGGER.info(
                "Photon FX started for entity {}",
                entity.getId()
        );
    }

    /**
     * Удаляет исполнитель и эффект при удалении сущности.
     */
    public static void stopEffect(TestSunEntity entity) {
        EntityEffectExecutor executor = ACTIVE_EXECUTORS.remove(entity);

        if (executor != null) {
            FXRuntime runtime = executor.getRuntime();

            if (runtime != null) {
                runtime.destroy(true);
            }
        }
    }

    /**
     * Очищает эффекты удалённых сущностей.
     * Вызывать каждый клиентский тик.
     */
    public static void tickAllEffects() {
        ACTIVE_EXECUTORS.entrySet().removeIf(entry -> {
            TestSunEntity entity = entry.getKey();
            EntityEffectExecutor executor = entry.getValue();

            if (!entity.isAlive()) {
                FXRuntime runtime = executor.getRuntime();

                if (runtime != null) {
                    runtime.destroy(true);
                }

                return true;
            }

            return false;
        });
    }

    public static class TestSunModel
            extends EntityModel<LivingEntityRenderState> {

        public static final ModelLayerLocation LAYER_LOCATION =
                new ModelLayerLocation(
                        Identifier.parse("ssc_14:test_sun"),
                        "main"
                );

        public TestSunModel(ModelPart root) {
            super(root);
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshDefinition = new MeshDefinition();
            PartDefinition partDefinition = meshDefinition.getRoot();

            partDefinition.addOrReplaceChild(
                    "cube",
                    CubeListBuilder.create()
                            .texOffs(0, 0)
                            .addBox(
                                    -4.0F,
                                    -8.0F,
                                    -4.0F,
                                    8.0F,
                                    8.0F,
                                    8.0F
                            ),
                    PartPose.ZERO
            );

            return LayerDefinition.create(meshDefinition, 64, 32);
        }

        @Override
        public void setupAnim(LivingEntityRenderState state) {
            // Внешний вид создаётся эффектом Photon.
        }
    }
}
