package net.ssc14.integration.neoforge.atmosphere.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.ssc14.integration.neoforge.network.CompatibilityPayloadRegistrar;

/** Registers the compatibility gas-analyser payload under its established protocol version. */
@EventBusSubscriber(modid = "ssc_14")
public final class GasAnalyserPayloadRegistration {
    private static final String PROTOCOL_VERSION = "ssc_14";

    private GasAnalyserPayloadRegistration() { }

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        var registrar = new CompatibilityPayloadRegistrar(
                event.registrar(PROTOCOL_VERSION), PROTOCOL_VERSION);
        registrar.playToClient(GasAnalyserData.TYPE, GasAnalyserData.CODEC,
                GasAnalyserData::handle);
    }
}
