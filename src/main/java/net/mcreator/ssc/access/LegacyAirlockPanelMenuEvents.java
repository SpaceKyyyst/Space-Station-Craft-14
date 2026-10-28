package net.mcreator.ssc.access;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.ssc14.content.menu.airlock.CabelPannelAirlockMenu;

/** Transitional lifecycle adapter for the migrated airlock wire-panel menu. */
@EventBusSubscriber
public final class LegacyAirlockPanelMenuEvents {
    private LegacyAirlockPanelMenuEvents() {
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
