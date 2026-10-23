package net.mcreator.ssc;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

/** Records a contract only after NeoForge accepts the corresponding registration. */
final class CompatibilityPayloadRegistrar {
    private static final Set<String> REGISTERED_CONTRACTS = ConcurrentHashMap.newKeySet();

    private final PayloadRegistrar delegate;
    private final String version;

    CompatibilityPayloadRegistrar(PayloadRegistrar delegate, String version) {
        this.delegate = delegate;
        this.version = version;
    }

    <T extends CustomPacketPayload> void playToServer(CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
        delegate.playToServer(type, codec, handler);
        record("serverbound", type);
    }

    <T extends CustomPacketPayload> void playToClient(CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
        delegate.playToClient(type, codec, handler);
        record("clientbound", type);
    }

    <T extends CustomPacketPayload> void playBidirectional(CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> clientHandler,
            IPayloadHandler<T> serverHandler) {
        delegate.playBidirectional(type, codec, clientHandler, serverHandler);
        record("bidirectional", type);
    }

    static Set<String> registeredContracts() {
        return Set.copyOf(new TreeSet<>(REGISTERED_CONTRACTS));
    }

    private void record(String direction, CustomPacketPayload.Type<?> type) {
        String contract = direction + " " + version + " " + type.id();
        if (!REGISTERED_CONTRACTS.add(contract)) {
            throw new IllegalStateException("Duplicate observed payload contract: " + contract);
        }
    }
}
