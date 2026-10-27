
package net.ssc14.integration.neoforge.chemistry.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public record ProcessReactionsMessage(int slotIndex, ItemStack carriedStack, String targetMarker) implements CustomPacketPayload {

    // ИСПРАВЛЕНО: Регистрация ID типа сетевого пакета переведена на fromNamespaceAndPath
    public static final Type<ProcessReactionsMessage> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath("ssc_14", "process_reactions")
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ProcessReactionsMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, message -> message.slotIndex,
            ItemStack.OPTIONAL_STREAM_CODEC, message -> message.carriedStack,
            ByteBufCodecs.stringUtf8(256), message -> message.targetMarker,
            ProcessReactionsMessage::new
    );

    @Override
    public Type<ProcessReactionsMessage> type() {
        return TYPE;
    }

}
