package net.ssc14.integration.neoforge.menu.network;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.netty.buffer.Unpooled;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import org.junit.jupiter.api.Test;

class MenuButtonPayloadTest {
    @Test
    void preservesEveryLegacyButtonChannelWireShape() {
        for (MenuButtonPayload.Channel channel : MenuButtonPayload.Channel.values()) {
            var expected = new MenuButtonPayload(channel, 109, -17, 64, 23);
            var buffer = new RegistryFriendlyByteBuf(Unpooled.buffer(), RegistryAccess.EMPTY);
            try {
                var codec = MenuButtonPayload.codec(channel);
                codec.encode(buffer, expected);
                assertEquals(expected, codec.decode(buffer));
            } finally {
                buffer.release();
            }
        }
    }
}
