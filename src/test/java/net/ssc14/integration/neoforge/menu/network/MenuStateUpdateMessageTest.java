package net.ssc14.integration.neoforge.menu.network;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.netty.buffer.Unpooled;
import java.util.List;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import org.junit.jupiter.api.Test;

class MenuStateUpdateMessageTest {
    @Test
    void preservesEveryExistingValueType() {
        for (var expected : List.of(
                new MenuStateUpdateMessage(0, "label", "ready"),
                new MenuStateUpdateMessage(1, "enabled", true),
                new MenuStateUpdateMessage(2, "progress", 12.5D))) {
            var buffer = new RegistryFriendlyByteBuf(Unpooled.buffer(), RegistryAccess.EMPTY);
            try {
                MenuStateUpdateMessage.STREAM_CODEC.encode(buffer, expected);
                assertEquals(expected, MenuStateUpdateMessage.STREAM_CODEC.decode(buffer));
            } finally {
                buffer.release();
            }
        }
    }
}
