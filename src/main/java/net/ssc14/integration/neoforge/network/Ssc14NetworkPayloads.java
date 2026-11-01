package net.ssc14.integration.neoforge.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.ssc14.common.Ssc14Identity;
import org.jspecify.annotations.Nullable;

/** Canonical owner of the mod payload-registration lifecycle. */
public final class Ssc14NetworkPayloads {
    private static final String NETWORK_VERSION = Ssc14Identity.MOD_ID;
    private static final PayloadRegistrationQueue QUEUE = new PayloadRegistrationQueue();

    private Ssc14NetworkPayloads() { }

    public static <T extends CustomPacketPayload> void addServerbound(CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
        QUEUE.addServerbound(type, codec, handler);
    }

    public static <T extends CustomPacketPayload> void addClientbound(CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
        QUEUE.addClientbound(type, codec, handler);
    }

    public static <T extends CustomPacketPayload> void addBidirectional(CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
        QUEUE.addBidirectional(type, codec, handler);
    }

    /** A null client handler is registered separately on the physical client. */
    public static <T extends CustomPacketPayload> void addBidirectional(CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> serverHandler,
            @Nullable IPayloadHandler<T> clientHandler) {
        QUEUE.addBidirectional(type, codec, serverHandler, clientHandler);
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        var registrar = new CompatibilityPayloadRegistrar(event.registrar(NETWORK_VERSION), NETWORK_VERSION);
        QUEUE.registerAll(new CompatibilityTarget(registrar));
    }

    private record CompatibilityTarget(CompatibilityPayloadRegistrar registrar)
            implements PayloadRegistrationQueue.Target {
        @Override
        public <T extends CustomPacketPayload> void serverbound(CustomPacketPayload.Type<T> type,
                StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
            registrar.playToServer(type, codec, handler);
        }

        @Override
        public <T extends CustomPacketPayload> void clientbound(CustomPacketPayload.Type<T> type,
                StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
            registrar.playToClient(type, codec, handler);
        }

        @Override
        public <T extends CustomPacketPayload> void bidirectional(CustomPacketPayload.Type<T> type,
                StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> serverHandler,
                @Nullable IPayloadHandler<T> clientHandler) {
            registrar.playBidirectional(type, codec, clientHandler, serverHandler);
        }
    }
}
