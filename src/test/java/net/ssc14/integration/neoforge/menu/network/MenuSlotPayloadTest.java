package net.ssc14.integration.neoforge.menu.network;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.netty.buffer.Unpooled;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import org.junit.jupiter.api.Test;

class MenuSlotPayloadTest {
    @Test
    void preservesBothLegacySlotChannels() {
        for (MenuSlotPayload.Channel channel : MenuSlotPayload.Channel.values()) {
            var expected = new MenuSlotPayload(channel, 8, -12, 64, 21, 2, 99);
            var buffer = new RegistryFriendlyByteBuf(Unpooled.buffer(), RegistryAccess.EMPTY);
            try {
                var codec = MenuSlotPayload.codec(channel);
                codec.encode(buffer, expected);
                assertEquals(expected, codec.decode(buffer));
            } finally {
                buffer.release();
            }
        }
    }
}
