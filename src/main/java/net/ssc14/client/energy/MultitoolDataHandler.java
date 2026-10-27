package net.ssc14.client.energy;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.integration.neoforge.energy.network.MultitoolDataPacket;

/** Applies clientbound multitool telemetry to the client cache. */
public final class MultitoolDataHandler {
    private MultitoolDataHandler() { }

    public static void handle(MultitoolDataPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            MultitoolClientCache.currentMode = packet.mode();
            MultitoolClientCache.currentPower = packet.current();
            MultitoolClientCache.batteryPower = packet.battery();
            MultitoolClientCache.theoreticalSupply = packet.theoretical();
            MultitoolClientCache.idealConsumption = packet.ideal();
            MultitoolClientCache.inputStored = packet.inStored();
            MultitoolClientCache.inputMax = packet.inMax();
            MultitoolClientCache.outputStored = packet.outStored();
            MultitoolClientCache.outputMax = packet.outMax();
        });
    }
}
