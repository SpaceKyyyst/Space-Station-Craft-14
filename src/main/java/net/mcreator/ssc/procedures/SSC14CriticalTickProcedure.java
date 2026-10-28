package net.mcreator.ssc.procedures;

import net.ssc14.content.medical.MedicalCriticalTickHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = "ssc_14")
public final class SSC14CriticalTickProcedure {
    private SSC14CriticalTickProcedure() { }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        MedicalCriticalTickHandler.onPlayerTick(event.getEntity());
    }

}
