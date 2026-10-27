
package net.mcreator.ssc;

import net.ssc14.integration.neoforge.network.CompatibilityPayloadRegistrar;
import net.ssc14.integration.neoforge.energy.network.MultitoolDataPacket;
import net.ssc14.client.energy.MultitoolDataHandler;
import net.ssc14.integration.neoforge.atmosphere.network.GasAnalyserData;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber
public class NetworkRegister {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final String version = "ssc_14";
        final CompatibilityPayloadRegistrar registrar = new CompatibilityPayloadRegistrar(
                event.registrar(version), version);
        registrar.playToClient(net.ssc14.integration.neoforge.medical.network.MedicalPresentationMessage.TYPE,
                net.ssc14.integration.neoforge.medical.network.MedicalPresentationMessage.CODEC,
                net.ssc14.client.medical.MedicalPresentationHandler::handle);
        registrar.playToClient(GasAnalyserData.TYPE,
                GasAnalyserData.CODEC,
                GasAnalyserData::handle);

        // Регистрируем пакет на клиентскую сторону
        registrar.playToClient(
            MultitoolDataPacket.TYPE,
            MultitoolDataPacket.CODEC,
            MultitoolDataHandler::handle
        );
    }
}
