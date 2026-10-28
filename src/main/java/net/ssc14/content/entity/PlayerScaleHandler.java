package net.ssc14.content.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/** Applies the default player scale when a player joins the server. */
@EventBusSubscriber
public final class PlayerScaleHandler {
    private static final double DEFAULT_SCALE = 0.9D;

    private PlayerScaleHandler() {
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getAttributes().hasAttribute(Attributes.SCALE)) {
            entity.getAttribute(Attributes.SCALE).setBaseValue(DEFAULT_SCALE);
        }
    }
}
