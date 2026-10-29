package net.ssc14.integration.neoforge.menu.airlock;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.ssc14.content.menu.airlock.CabelPannelAirlockMenu;

/** Canonical lifecycle adapter for the migrated airlock wire-panel menu. */
@EventBusSubscriber
public final class AirlockPanelMenuEvents {
    private AirlockPanelMenuEvents() {
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        var player = event.getEntity();
        if (!player.level().isClientSide()
                && player.containerMenu instanceof CabelPannelAirlockMenu menu
                && !menu.stillValid(player)) {
            player.closeContainer();
        }
    }
}
