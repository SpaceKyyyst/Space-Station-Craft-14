
package net.mcreator.ssc;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber
public class NetworkRegister {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("ssc_14");
        
        // Регистрируем пакет на клиентскую сторону
        registrar.playToClient(
            MultitoolDataPacket.TYPE, 
            MultitoolDataPacket.CODEC, 
            MultitoolDataPacket::handleClient
        );
    }
}
