package net.mcreator.ssc;

import io.netty.buffer.Unpooled;
import java.util.List;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.level.ChunkPos;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DecalPacketCodecTest {
    @Test
    void preservesSignedChunkCoordinatesOnTheExistingWire() {
        var message = new DecalNetwork.ClientboundChunkDecalsPacket(new ChunkPos(12, -47), List.of());
        RegistryFriendlyByteBuf buffer = new RegistryFriendlyByteBuf(Unpooled.buffer(), RegistryAccess.EMPTY);
        try {
            DecalNetwork.ClientboundChunkDecalsPacket.STREAM_CODEC.encode(buffer, message);
            assertEquals(message, DecalNetwork.ClientboundChunkDecalsPacket.STREAM_CODEC.decode(buffer));
        } finally {
            buffer.release();
        }
    }

    @Test
    void rejectsListCountLargerThanRemainingPayloadBeforeAllocation() {
        RegistryFriendlyByteBuf buffer = new RegistryFriendlyByteBuf(Unpooled.buffer(), RegistryAccess.EMPTY);
        try {
            buffer.writeLong(0);
            buffer.writeVarInt(Integer.MAX_VALUE);
            assertThrows(IllegalArgumentException.class,
                    () -> DecalNetwork.ClientboundChunkDecalsPacket.STREAM_CODEC.decode(buffer));
        } finally {
            buffer.release();
        }
    }
}
