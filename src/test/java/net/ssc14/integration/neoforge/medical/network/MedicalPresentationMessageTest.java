package net.ssc14.integration.neoforge.medical.network;

import net.ssc14.client.medical.MedicalPresentationHandler;

import io.netty.buffer.Unpooled;
import java.lang.reflect.Proxy;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MedicalPresentationMessageTest {
    private static final UUID PLAYER = UUID.fromString("a0e6084e-5e3a-4da2-a1d4-f767f9e7e3ae");
    private static final Identifier LEVEL = Identifier.parse("minecraft:overworld");

    @Test
    void wireRoundTripPreservesEntryRecoveryAndExactRecipient() {
        for (boolean active : new boolean[] {true, false}) {
            var buffer = new FriendlyByteBuf(Unpooled.buffer());
            try {
                MedicalPresentationMessage.CODEC.encode(buffer, new MedicalPresentationMessage(42, PLAYER, LEVEL, active));
                var received = MedicalPresentationMessage.CODEC.decode(buffer);
                assertEquals(active, received.active());
                assertTrue(received.matches(42, PLAYER, LEVEL));
                assertFalse(received.matches(43, PLAYER, LEVEL), "An old incarnation must not affect a respawned player");
                assertFalse(received.matches(42, new UUID(0, 0), LEVEL), "Entity ID reuse must not affect another player");
                assertFalse(received.matches(42, PLAYER, Identifier.parse("minecraft:the_nether")), "Old-world packets must be ignored");
                assertEquals(0, buffer.readableBytes());
            } finally {
                buffer.release();
            }
        }
    }

    @Test
    void dimensionInputHasABoundedDecode() {
        var buffer = new FriendlyByteBuf(Unpooled.buffer());
        try {
            buffer.writeVarInt(42);
            buffer.writeUUID(PLAYER);
            buffer.writeUtf("x".repeat(257));
            buffer.writeBoolean(true);
            assertThrows(io.netty.handler.codec.DecoderException.class, () -> MedicalPresentationMessage.CODEC.decode(buffer));
        } finally {
            buffer.release();
        }
    }

    @Test
    void serverboundInvocationRejectsBeforeResolvingAPlayer() {
        var context = (IPayloadContext) Proxy.newProxyInstance(IPayloadContext.class.getClassLoader(), new Class<?>[] {IPayloadContext.class},
                (proxy, method, args) -> {
                    if (method.getName().equals("flow")) return PacketFlow.SERVERBOUND;
                    throw new AssertionError("Serverbound presentation must never access " + method.getName());
                });
        MedicalPresentationHandler.handle(new MedicalPresentationMessage(42, PLAYER, LEVEL, true), context);
    }
}
