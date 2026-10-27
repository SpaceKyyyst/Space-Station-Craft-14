package net.ssc14.integration.neoforge.chemistry.network;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.netty.buffer.Unpooled;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import org.junit.jupiter.api.Test;

class ProcessReactionsMessageTest {
    @Test
    void preservesExistingWireFields() {
        var expected = new ProcessReactionsMessage(17, ItemStack.EMPTY, "hotbar_transfer");
        var buffer = new RegistryFriendlyByteBuf(Unpooled.buffer(), RegistryAccess.EMPTY);
        try {
            ProcessReactionsMessage.STREAM_CODEC.encode(buffer, expected);
            assertEquals(expected, ProcessReactionsMessage.STREAM_CODEC.decode(buffer));
        } finally {
            buffer.release();
        }
    }
}
