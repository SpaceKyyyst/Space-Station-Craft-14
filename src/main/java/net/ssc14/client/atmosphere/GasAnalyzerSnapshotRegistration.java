package net.ssc14.client.atmosphere;

import net.minecraft.network.protocol.PacketFlow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.integration.neoforge.atmosphere.network.GasAnalyzerSnapshotMessage;
import net.ssc14.integration.neoforge.network.Ssc14NetworkPayloads;

/** Clientbound gas-analyzer snapshot registration and dispatch. */
@EventBusSubscriber
public final class GasAnalyzerSnapshotRegistration {
    private GasAnalyzerSnapshotRegistration() { }

    public static void handle(GasAnalyzerSnapshotMessage message, IPayloadContext context) {
        if (context.flow() != PacketFlow.CLIENTBOUND) return;
        context.enqueueWork(() -> GasAnalyzerClientCache.accept(context.player().level(), message));
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        Ssc14NetworkPayloads.addClientbound(GasAnalyzerSnapshotMessage.TYPE,
                GasAnalyzerSnapshotMessage.STREAM_CODEC, GasAnalyzerSnapshotRegistration::handle);
    }
}
