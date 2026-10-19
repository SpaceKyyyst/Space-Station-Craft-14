package net.mcreator.ssc.procedures;

import net.mcreator.ssc.access.PlayerAccessService;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/** Compatibility event adapter for the generated procedure name. */
@EventBusSubscriber(modid = "ssc_14")
public final class IDCardAccessProcedure {
    private IDCardAccessProcedure() { }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        PlayerAccessService.tick(event.getEntity());
    }
}
