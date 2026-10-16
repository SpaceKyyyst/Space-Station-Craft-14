package net.mcreator.ssc.network;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.mcreator.ssc.Ssc14Mod;
import net.mcreator.ssc.network.validation.GasAnalyzerReadings;
import net.mcreator.ssc.network.validation.PayloadLimits;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@EventBusSubscriber
public record GasAnalyzerSnapshotMessage(Identifier dimension, BlockPos position, float pressurePa,
        float temperatureKelvin, float totalMoles, float oxygenMoles, float nitrogenMoles) implements CustomPacketPayload {
    public static final Type<GasAnalyzerSnapshotMessage> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath(Ssc14Mod.MODID, "gas_analyzer_snapshot"));
    public static final StreamCodec<RegistryFriendlyByteBuf, GasAnalyzerSnapshotMessage> STREAM_CODEC = StreamCodec.of(
            (buffer, message) -> {
                buffer.writeUtf(message.dimension.toString(), PayloadLimits.MAX_KEY_LENGTH);
                buffer.writeBlockPos(message.position);
                buffer.writeFloat(message.pressurePa);
                buffer.writeFloat(message.temperatureKelvin);
                buffer.writeFloat(message.totalMoles);
                buffer.writeFloat(message.oxygenMoles);
                buffer.writeFloat(message.nitrogenMoles);
            }, buffer -> new GasAnalyzerSnapshotMessage(
                    Identifier.parse(buffer.readUtf(PayloadLimits.MAX_KEY_LENGTH)), buffer.readBlockPos(),
                    buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat()));

    public GasAnalyzerSnapshotMessage {
        Objects.requireNonNull(dimension, "Dimension");
        position = Objects.requireNonNull(position, "Sample position").immutable();
        PayloadLimits.requireText(dimension.toString(), PayloadLimits.MAX_KEY_LENGTH);
        new GasAnalyzerReadings(pressurePa, temperatureKelvin, totalMoles, oxygenMoles, nitrogenMoles);
    }

    @Override
    public Type<GasAnalyzerSnapshotMessage> type() {
        return TYPE;
    }

    public static void handleData(GasAnalyzerSnapshotMessage message, IPayloadContext context) {
        if (context.flow() == PacketFlow.CLIENTBOUND) {
            context.enqueueWork(() -> GasAnalyzerClientCache.accept(context.player().level(), message));
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        Ssc14Mod.addClientboundNetworkMessage(TYPE, STREAM_CODEC, GasAnalyzerSnapshotMessage::handleData);
    }
}
