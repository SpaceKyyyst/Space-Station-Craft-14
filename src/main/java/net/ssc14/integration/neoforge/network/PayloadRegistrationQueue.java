package net.ssc14.integration.neoforge.network;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import org.jspecify.annotations.Nullable;

/** Collects payload contracts until NeoForge opens its registration event. */
public final class PayloadRegistrationQueue {
    private final Map<CustomPacketPayload.Type<?>, Registration<?>> registrations = new LinkedHashMap<>();
    private boolean consumed;

    public synchronized <T extends CustomPacketPayload> void addServerbound(CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
        add(new Registration<>(type, codec, handler, null, Direction.SERVERBOUND));
    }

    public synchronized <T extends CustomPacketPayload> void addClientbound(CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
        add(new Registration<>(type, codec, handler, null, Direction.CLIENTBOUND));
    }

    public synchronized <T extends CustomPacketPayload> void addBidirectional(CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
        addBidirectional(type, codec, handler, handler);
    }

    /** A null client handler is supplied later through NeoForge's client-only registration event. */
    public synchronized <T extends CustomPacketPayload> void addBidirectional(CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> serverHandler,
            @Nullable IPayloadHandler<T> clientHandler) {
        add(new Registration<>(type, codec, serverHandler, clientHandler, Direction.BIDIRECTIONAL));
    }

    public synchronized void registerAll(Target target) {
        Objects.requireNonNull(target, "target");
        if (consumed) throw new IllegalStateException("Payload registration queue was already consumed");
        registrations.values().forEach(registration -> registration.register(target));
        consumed = true;
    }

    private void add(Registration<?> registration) {
        if (consumed) throw new IllegalStateException("Cannot register payloads after the queue was consumed");
        if (registrations.putIfAbsent(registration.type(), registration) != null) {
            throw new IllegalStateException("Duplicate network payload: " + registration.type().id());
        }
    }

    public interface Target {
        <T extends CustomPacketPayload> void serverbound(CustomPacketPayload.Type<T> type,
                StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler);

        <T extends CustomPacketPayload> void clientbound(CustomPacketPayload.Type<T> type,
                StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler);

        <T extends CustomPacketPayload> void bidirectional(CustomPacketPayload.Type<T> type,
                StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> serverHandler,
                @Nullable IPayloadHandler<T> clientHandler);
    }

    private enum Direction {
        SERVERBOUND,
        CLIENTBOUND,
        BIDIRECTIONAL
    }

    private record Registration<T extends CustomPacketPayload>(CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler,
            @Nullable IPayloadHandler<T> clientHandler, Direction direction) {
        private Registration {
            Objects.requireNonNull(type, "type");
            Objects.requireNonNull(codec, "codec");
            Objects.requireNonNull(handler, "handler");
            Objects.requireNonNull(direction, "direction");
        }

        private void register(Target target) {
            switch (direction) {
                case SERVERBOUND -> target.serverbound(type, codec, handler);
                case CLIENTBOUND -> target.clientbound(type, codec, handler);
                case BIDIRECTIONAL -> target.bidirectional(type, codec, handler, clientHandler);
            }
        }
    }
}
