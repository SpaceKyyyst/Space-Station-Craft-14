package net.mcreator.ssc.init;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.util.context.ContextKey;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;

/** @deprecated Use {@link net.ssc14.client.Ssc14RenderStateModifiers}. */
@Deprecated(forRemoval = false)
public final class Ssc14ModRenderStateModifiers {
    public static final ContextKey<LivingEntity> LIVING_ENTITY =
            net.ssc14.client.Ssc14RenderStateModifiers.LIVING_ENTITY;

    private Ssc14ModRenderStateModifiers() { }

    public static void registerModifiers(RegisterRenderStateModifiersEvent event) {
        net.ssc14.client.Ssc14RenderStateModifiers.registerModifiers(event);
    }

    public static LivingEntityRenderState cloneRenderState(
            LivingEntityRenderState newState, LivingEntityRenderState originalState) {
        return net.ssc14.client.Ssc14RenderStateModifiers.cloneRenderState(newState, originalState);
    }
}
