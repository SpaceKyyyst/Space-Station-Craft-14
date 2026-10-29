package net.mcreator.ssc.access;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.ssc14.content.menu.access.AccessConfigMENUMenu;
import net.ssc14.content.block.airlock.AirlockConfiguratorService;

/** Transitional event adapter until the access subsystem leaves the legacy namespace. */
@EventBusSubscriber
public final class LegacyAccessConfigMenuEvents {
    private LegacyAccessConfigMenuEvents() {
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity().containerMenu instanceof AccessConfigMENUMenu) {
            AirlockConfiguratorService.synchronize(event.getEntity(), false);
        }
    }

    @SubscribeEvent
    public static void onContainerOpen(PlayerContainerEvent.Open event) {
        if (event.getContainer() instanceof AccessConfigMENUMenu) {
            AirlockConfiguratorService.synchronize(event.getEntity(), true);
        }
    }
}
