package net.mcreator.ssc.network;

import net.minecraft.network.protocol.PacketFlow;
import net.mcreator.ssc.Ssc14Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.client.atmosphere.GasAnalyzerClientCache;
import net.ssc14.integration.neoforge.atmosphere.network.GasAnalyzerSnapshotMessage;

/** Transitional registration adapter until the shared legacy network bootstrap is migrated. */
@EventBusSubscriber
public final class GasAnalyzerSnapshotRegistration {
    private GasAnalyzerSnapshotRegistration() {
    }

    public static void handleData(GasAnalyzerSnapshotMessage message, IPayloadContext context) {
        if (context.flow() == PacketFlow.CLIENTBOUND) {
            context.enqueueWork(() -> GasAnalyzerClientCache.accept(context.player().level(), message));
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        Ssc14Mod.addClientboundNetworkMessage(
                GasAnalyzerSnapshotMessage.TYPE,
                GasAnalyzerSnapshotMessage.STREAM_CODEC,
                GasAnalyzerSnapshotRegistration::handleData);
    }
}
