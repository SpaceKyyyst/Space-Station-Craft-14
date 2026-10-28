package net.mcreator.ssc.integration;

import net.mcreator.ssc.procedures.ID_Code_CHECKProcedure;
import net.mcreator.ssc.procedures.IDconsole_OPENMENUProcedure;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.ssc14.content.menu.access.IDcodeMenu;

/** Compatibility event adapter kept outside the canonical menu container. */
@EventBusSubscriber
public final class LegacyIdCodeMenuEvents {
    private LegacyIdCodeMenuEvents() { }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity().containerMenu instanceof IDcodeMenu menu) {
            ID_Code_CHECKProcedure.execute(menu.world, menu.x, menu.y, menu.z, event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onContainerOpen(PlayerContainerEvent.Open event) {
        if (event.getContainer() instanceof IDcodeMenu menu) {
            IDconsole_OPENMENUProcedure.execute(menu.world, menu.x, menu.y, menu.z);
        }
    }
}
