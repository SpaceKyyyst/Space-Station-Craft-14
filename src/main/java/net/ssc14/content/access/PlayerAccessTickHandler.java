package net.ssc14.content.access;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = "ssc_14")
public final class PlayerAccessTickHandler {
    private PlayerAccessTickHandler() { }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        PlayerAccessService.tick(event.getEntity());
    }
}

