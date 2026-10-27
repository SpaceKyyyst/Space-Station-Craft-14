package net.ssc14.integration.neoforge.rotation.network;

import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;

import net.ssc14.common.Ssc14Identity;

public record RotateMessage(int eventType, int pressedms) implements CustomPacketPayload {
	public static final Type<RotateMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, "key_rotate"));
	public static final StreamCodec<RegistryFriendlyByteBuf, RotateMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, RotateMessage message) -> {
		buffer.writeInt(message.eventType);
		buffer.writeInt(message.pressedms);
	}, (RegistryFriendlyByteBuf buffer) -> new RotateMessage(buffer.readInt(), buffer.readInt()));

	@Override
	public Type<RotateMessage> type() {
		return TYPE;
	}

}
