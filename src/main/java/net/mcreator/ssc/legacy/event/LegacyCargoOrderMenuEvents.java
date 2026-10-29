package net.mcreator.ssc.legacy.event;

import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;

/** @deprecated Use the canonical commerce cargo event handler. */
@Deprecated(forRemoval = false)
public final class LegacyCargoOrderMenuEvents {
    private LegacyCargoOrderMenuEvents() { }
    public static void onContainerOpen(PlayerContainerEvent.Open event) {
        net.ssc14.content.commerce.cargo.CargoOrderMenuEvents.onContainerOpen(event);
    }
}
