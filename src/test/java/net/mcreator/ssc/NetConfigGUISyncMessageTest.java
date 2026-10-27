package net.mcreator.ssc;

import io.netty.buffer.Unpooled;
import java.util.HashMap;
import java.util.Map;
import net.ssc14.integration.neoforge.network.NetConfigGUISyncMessage;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NetConfigGUISyncMessageTest {
    @Test
    void codecRoundTripsSnapshotAfterSourceMutation() {
        Map<String, Object> source = new HashMap<>(Map.of("L1_text", "Switch", "L1_active", true, "selected_trigger_idx", 0));
        NetConfigGUISyncMessage message = new NetConfigGUISyncMessage(source);
        source.clear();
        RegistryFriendlyByteBuf buffer = buffer();
        try {
            NetConfigGUISyncMessage.STREAM_CODEC.encode(buffer, message);
            assertEquals(message.state(), NetConfigGUISyncMessage.STREAM_CODEC.decode(buffer).state());
            assertEquals(0, buffer.readableBytes());
        } finally {
            buffer.release();
        }
    }

    @Test
    void rejectsNegativeOrOversizedCountsBeforeReadingEntries() {
        for (int count : new int[] {-1, Integer.MAX_VALUE}) {
            RegistryFriendlyByteBuf buffer = buffer();
            try {
                buffer.writeInt(count);
                assertThrows(IllegalArgumentException.class, () -> NetConfigGUISyncMessage.STREAM_CODEC.decode(buffer));
            } finally {
                buffer.release();
            }
        }
    }

    @Test
    void rejectsUnknownTypeAndDuplicateKeys() {
        RegistryFriendlyByteBuf buffer = buffer();
        try {
            buffer.writeInt(1).writeUtf("name").writeByte(4);
            assertThrows(IllegalArgumentException.class, () -> NetConfigGUISyncMessage.STREAM_CODEC.decode(buffer));
            buffer.clear();
            buffer.writeInt(2).writeUtf("name").writeByte(1).writeBoolean(true);
            buffer.writeUtf("name").writeByte(1).writeBoolean(false);
            assertThrows(IllegalArgumentException.class, () -> NetConfigGUISyncMessage.STREAM_CODEC.decode(buffer));
        } finally {
            buffer.release();
        }
    }

    private static RegistryFriendlyByteBuf buffer() {
        return new RegistryFriendlyByteBuf(Unpooled.buffer(), RegistryAccess.EMPTY);
    }
}
