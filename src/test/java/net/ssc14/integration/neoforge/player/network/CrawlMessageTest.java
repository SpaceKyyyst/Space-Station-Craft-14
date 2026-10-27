package net.ssc14.integration.neoforge.player.network;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.netty.buffer.Unpooled;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import org.junit.jupiter.api.Test;

class CrawlMessageTest {
    @Test
    void preservesExistingWireFields() {
        var expected = new CrawlMessage(0, 125);
        var buffer = new RegistryFriendlyByteBuf(Unpooled.buffer(), RegistryAccess.EMPTY);
        try {
            CrawlMessage.STREAM_CODEC.encode(buffer, expected);
            assertEquals(expected, CrawlMessage.STREAM_CODEC.decode(buffer));
        } finally {
            buffer.release();
        }
    }
}
