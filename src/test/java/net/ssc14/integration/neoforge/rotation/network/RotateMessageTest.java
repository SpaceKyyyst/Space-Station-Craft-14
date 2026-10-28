package net.ssc14.integration.neoforge.rotation.network;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.netty.buffer.Unpooled;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import org.junit.jupiter.api.Test;

class RotateMessageTest {
    @Test
    void preservesExistingWireFields() {
        var expected = new RotateMessage(2, Integer.MAX_VALUE);
        var buffer = new RegistryFriendlyByteBuf(Unpooled.buffer(), RegistryAccess.EMPTY);
        try {
            RotateMessage.STREAM_CODEC.encode(buffer, expected);
            assertEquals(expected, RotateMessage.STREAM_CODEC.decode(buffer));
        } finally {
            buffer.release();
        }
    }
}
