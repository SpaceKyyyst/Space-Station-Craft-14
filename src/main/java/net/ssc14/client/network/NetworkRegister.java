package net.ssc14.client.network;

import net.ssc14.integration.neoforge.energy.network.MultitoolDataPacket;
import net.ssc14.integration.neoforge.network.CompatibilityPayloadRegistrar;
import net.ssc14.client.energy.MultitoolDataHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

/** Registers the SSC14 play payloads while keeping client handlers in the client layer. */
@EventBusSubscriber
public final class NetworkRegister {
    private static final String PROTOCOL_VERSION = "ssc_14";

    private NetworkRegister() { }

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        CompatibilityPayloadRegistrar registrar = new CompatibilityPayloadRegistrar(
                event.registrar(PROTOCOL_VERSION), PROTOCOL_VERSION);
        registrar.playToClient(
                net.ssc14.integration.neoforge.medical.network.MedicalPresentationMessage.TYPE,
                net.ssc14.integration.neoforge.medical.network.MedicalPresentationMessage.CODEC,
                net.ssc14.client.medical.MedicalPresentationHandler::handle);
        registrar.playToClient(
                MultitoolDataPacket.TYPE,
                MultitoolDataPacket.CODEC,
                MultitoolDataHandler::handle);
    }
}
