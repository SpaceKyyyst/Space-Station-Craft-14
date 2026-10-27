package net.ssc14.integration.neoforge.player.network;

import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.ssc14.common.Ssc14Identity;

public record SpecifyMessage(int eventType, int pressedms) implements CustomPacketPayload {
	public static final Type<SpecifyMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, "key_specify"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SpecifyMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, SpecifyMessage message) -> {
		buffer.writeInt(message.eventType);
		buffer.writeInt(message.pressedms);
	}, (RegistryFriendlyByteBuf buffer) -> new SpecifyMessage(buffer.readInt(), buffer.readInt()));

	@Override
	public Type<SpecifyMessage> type() {
		return TYPE;
	}

}
