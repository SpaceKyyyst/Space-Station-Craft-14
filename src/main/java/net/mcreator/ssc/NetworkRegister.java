
package net.mcreator.ssc;

import java.util.Set;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber
public class NetworkRegister {

    static Set<String> networkCompatibilityContracts() {
        return Set.of(
                "clientbound unversioned " + net.mcreator.ssc.network.MedicalPresentationMessage.TYPE.id(),
                "clientbound unversioned " + net.mcreator.ssc.network.GasAnalyserData.TYPE.id(),
                "clientbound unversioned " + MultitoolDataPacket.TYPE.id());
    }

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("ssc_14");
        registrar.playToClient(net.mcreator.ssc.network.MedicalPresentationMessage.TYPE,
                net.mcreator.ssc.network.MedicalPresentationMessage.CODEC,
                net.mcreator.ssc.network.MedicalPresentationMessage::handle);
        registrar.playToClient(net.mcreator.ssc.network.GasAnalyserData.TYPE,
                net.mcreator.ssc.network.GasAnalyserData.CODEC,
                net.mcreator.ssc.network.GasAnalyserData::handle);

        // Регистрируем пакет на клиентскую сторону
        registrar.playToClient(
            MultitoolDataPacket.TYPE,
            MultitoolDataPacket.CODEC,
            MultitoolDataPacket::handleClient
        );
    }
}
