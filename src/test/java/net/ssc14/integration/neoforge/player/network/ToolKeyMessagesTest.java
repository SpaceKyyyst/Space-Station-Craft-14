package net.ssc14.integration.neoforge.player.network;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.netty.buffer.Unpooled;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import org.junit.jupiter.api.Test;

class ToolKeyMessagesTest {
    @Test
    void specifyPreservesExistingWireFields() {
        var expected = new SpecifyMessage(0, 37);
        var buffer = buffer();
        try {
            SpecifyMessage.STREAM_CODEC.encode(buffer, expected);
            assertEquals(expected, SpecifyMessage.STREAM_CODEC.decode(buffer));
        } finally { buffer.release(); }
    }

    @Test
    void worldObjectCheckPreservesExistingWireFields() {
        var expected = new WorldObjectCheckMessage(1, Integer.MAX_VALUE);
        var buffer = buffer();
        try {
            WorldObjectCheckMessage.STREAM_CODEC.encode(buffer, expected);
            assertEquals(expected, WorldObjectCheckMessage.STREAM_CODEC.decode(buffer));
        } finally { buffer.release(); }
    }

    private static RegistryFriendlyByteBuf buffer() {
        return new RegistryFriendlyByteBuf(Unpooled.buffer(), RegistryAccess.EMPTY);
    }
}
