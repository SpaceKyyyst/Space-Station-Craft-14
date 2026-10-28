package net.ssc14.integration.neoforge.network;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
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

        @Override
        public <T extends CustomPacketPayload> void serverbound(CustomPacketPayload.Type<T> type,
                StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
            registrations.add("serverbound:" + type.id());
        }

        @Override
        public <T extends CustomPacketPayload> void clientbound(CustomPacketPayload.Type<T> type,
                StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
            registrations.add("clientbound:" + type.id());
        }

        @Override
        public <T extends CustomPacketPayload> void bidirectional(CustomPacketPayload.Type<T> type,
                StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
            registrations.add("bidirectional:" + type.id());
        }
    }
}
