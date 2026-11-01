package net.ssc14.integration.neoforge.network;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.ssc14.integration.neoforge.menu.network.MenuStateServerRegistration;
import net.ssc14.integration.neoforge.menu.network.MenuStateUpdateMessage;
import org.junit.jupiter.api.Test;

class PayloadRegistrationQueueTest {
    private static final CustomPacketPayload.Type<TestPayload> SERVER_TYPE = type("server");
    private static final CustomPacketPayload.Type<TestPayload> CLIENT_TYPE = type("client");
    private static final CustomPacketPayload.Type<TestPayload> BIDIRECTIONAL_TYPE = type("bidirectional");
    private static final StreamCodec<RegistryFriendlyByteBuf, TestPayload> CODEC = StreamCodec.of(
            (buffer, payload) -> buffer.writeInt(payload.value()),
            buffer -> new TestPayload(buffer.readInt(), SERVER_TYPE));
    private static final IPayloadHandler<TestPayload> HANDLER = (payload, context) -> { };

    @Test
    void registersEveryDirectionExactlyOnce() {
        var queue = new PayloadRegistrationQueue();
        var target = new RecordingTarget();

        queue.addServerbound(SERVER_TYPE, CODEC, HANDLER);
        queue.addClientbound(CLIENT_TYPE, CODEC, HANDLER);
        queue.addBidirectional(BIDIRECTIONAL_TYPE, CODEC, HANDLER);
        queue.registerAll(target);

        assertEquals(List.of("serverbound:ssc_14:server", "clientbound:ssc_14:client",
                "bidirectional:ssc_14:bidirectional"), target.registrations);
        assertSame(HANDLER, target.serverHandler);
        assertSame(HANDLER, target.clientHandler);
        assertSame(CODEC, target.codec);
        assertThrows(IllegalStateException.class, () -> queue.registerAll(target));
        assertEquals(3, target.registrations.size());
    }

    @Test
    void preservesSeparateBidirectionalHandlers() {
        var queue = new PayloadRegistrationQueue();
        var target = new RecordingTarget();
        IPayloadHandler<TestPayload> clientHandler = (payload, context) -> { };

        queue.addBidirectional(BIDIRECTIONAL_TYPE, CODEC, HANDLER, clientHandler);
        queue.registerAll(target);

        assertEquals(List.of("bidirectional:ssc_14:bidirectional"), target.registrations);
        assertSame(HANDLER, target.serverHandler);
        assertSame(clientHandler, target.clientHandler);
        assertSame(CODEC, target.codec);
    }

    @Test
    void permitsClientHandlerRegistrationInClientOnlyEvent() {
        var queue = new PayloadRegistrationQueue();
        var target = new RecordingTarget();
        queue.addBidirectional(BIDIRECTIONAL_TYPE, CODEC, HANDLER, null);
        queue.registerAll(target);

        assertEquals(List.of("bidirectional:ssc_14:bidirectional"), target.registrations);
        assertSame(HANDLER, target.serverHandler);
        assertNull(target.clientHandler);
    }

    @Test
    void compatibilityRegistrarPreservesNeoForgeHandlerOrder() {
        var delegate = new RecordingRegistrar();
        var registrar = new CompatibilityPayloadRegistrar(delegate, "queue-test");
        IPayloadHandler<TestPayload> clientHandler = (payload, context) -> { };

        registrar.playBidirectional(BIDIRECTIONAL_TYPE, CODEC, clientHandler, HANDLER);

        assertEquals(1, delegate.calls);
        assertSame(BIDIRECTIONAL_TYPE, delegate.type);
        assertSame(CODEC, delegate.codec);
        assertSame(HANDLER, delegate.serverHandler);
        assertSame(clientHandler, delegate.clientHandler);
    }

    @Test
    void menuStateRegistrationDoesNotReferenceClientClasses() throws IOException {
        // Inspect bytecode without bypassing NeoForge's ClassLoadingGuardian.
        for (var type : List.of(MenuStateServerRegistration.class, MenuStateUpdateMessage.class,
                Ssc14NetworkPayloads.class, PayloadRegistrationQueue.class, CompatibilityPayloadRegistrar.class)) {
            assertNoClientReferences(type);
            for (var nested : type.getDeclaredClasses()) assertNoClientReferences(nested);
        }
    }

    private static void assertNoClientReferences(Class<?> type) throws IOException {
        try (var input = type.getResourceAsStream("/" + type.getName().replace('.', '/') + ".class")) {
            assertNotNull(input);
            // Class names and descriptors are UTF-8 constants in the class file.
            var bytecode = new String(input.readAllBytes(), StandardCharsets.ISO_8859_1).replace('.', '/');
            for (var clientPackage : List.of("net/minecraft/client/", "net/ssc14/client/",
                    "net/neoforged/neoforge/client/")) {
                assertFalse(bytecode.contains(clientPackage), type.getName() + " references " + clientPackage);
            }
        }
    }

    @Test
    void rejectsDuplicateBidirectionalPayloadTypes() {
        var queue = new PayloadRegistrationQueue();
        queue.addBidirectional(BIDIRECTIONAL_TYPE, CODEC, HANDLER, null);

        assertThrows(IllegalStateException.class,
                () -> queue.addBidirectional(BIDIRECTIONAL_TYPE, CODEC, HANDLER, HANDLER));
        assertThrows(IllegalStateException.class,
                () -> queue.addServerbound(BIDIRECTIONAL_TYPE, CODEC, HANDLER));
        assertThrows(IllegalStateException.class,
                () -> queue.addClientbound(BIDIRECTIONAL_TYPE, CODEC, HANDLER));
    }

    @Test
    void rejectsDuplicatePayloadTypes() {
        var queue = new PayloadRegistrationQueue();
        queue.addServerbound(SERVER_TYPE, CODEC, HANDLER);

        assertThrows(IllegalStateException.class,
                () -> queue.addClientbound(SERVER_TYPE, CODEC, HANDLER));
    }

    @Test
    void rejectsRegistrationAfterQueueIsConsumed() {
        var queue = new PayloadRegistrationQueue();
        queue.registerAll(new RecordingTarget());

        assertThrows(IllegalStateException.class,
                () -> queue.addServerbound(SERVER_TYPE, CODEC, HANDLER));
    }

    private static CustomPacketPayload.Type<TestPayload> type(String path) {
        return new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath("ssc_14", path));
    }

    private record TestPayload(int value, CustomPacketPayload.Type<TestPayload> type)
            implements CustomPacketPayload { }

    private static final class RecordingTarget implements PayloadRegistrationQueue.Target {
        private final List<String> registrations = new ArrayList<>();
        private IPayloadHandler<?> serverHandler;
        private IPayloadHandler<?> clientHandler;
        private StreamCodec<?, ?> codec;

        @Override
        public <T extends CustomPacketPayload> void serverbound(CustomPacketPayload.Type<T> type,
                StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
            registrations.add("serverbound:" + type.id());
            assertSame(HANDLER, handler);
            assertSame(CODEC, codec);
        }

        @Override
        public <T extends CustomPacketPayload> void clientbound(CustomPacketPayload.Type<T> type,
                StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
            registrations.add("clientbound:" + type.id());
            assertSame(HANDLER, handler);
            assertSame(CODEC, codec);
        }

        @Override
        public <T extends CustomPacketPayload> void bidirectional(CustomPacketPayload.Type<T> type,
                StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> serverHandler,
                IPayloadHandler<T> clientHandler) {
            registrations.add("bidirectional:" + type.id());
            this.serverHandler = serverHandler;
            this.clientHandler = clientHandler;
            this.codec = codec;
        }
    }

    private static final class RecordingRegistrar extends PayloadRegistrar {
        private int calls;
        private CustomPacketPayload.Type<?> type;
        private StreamCodec<?, ?> codec;
        private IPayloadHandler<?> serverHandler;
        private IPayloadHandler<?> clientHandler;

        private RecordingRegistrar() {
            super("queue-test");
        }

        @Override
        public <T extends CustomPacketPayload> PayloadRegistrar playBidirectional(CustomPacketPayload.Type<T> type,
                StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> serverHandler,
                IPayloadHandler<T> clientHandler) {
            calls++;
            this.type = type;
            this.codec = codec;
            this.serverHandler = serverHandler;
            this.clientHandler = clientHandler;
            return this;
        }
    }

}
