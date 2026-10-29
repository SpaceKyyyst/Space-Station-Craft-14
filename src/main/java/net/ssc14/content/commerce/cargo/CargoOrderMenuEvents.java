package net.ssc14.content.commerce.cargo;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.ssc14.content.commerce.cargo.CargoOrderService;
import net.ssc14.content.menu.commerce.OrderGUIMenu;

@EventBusSubscriber
public final class CargoOrderMenuEvents {
    private CargoOrderMenuEvents() {
    }

    @SubscribeEvent
    public static void onContainerOpen(PlayerContainerEvent.Open event) {
        if (event.getContainer() instanceof OrderGUIMenu menu) {
            CargoOrderService.refreshPreview(menu, event.getEntity());
        }
    }
}
