package net.ssc14.integration.neoforge.player.network;

import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.ssc14.common.Ssc14Identity;

public record DCMopenMessage(int eventType, int pressedms) implements CustomPacketPayload {
	public static final Type<DCMopenMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, "key_dc_mopen"));
	public static final StreamCodec<RegistryFriendlyByteBuf, DCMopenMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, DCMopenMessage message) -> {
		buffer.writeInt(message.eventType);
		buffer.writeInt(message.pressedms);
	}, (RegistryFriendlyByteBuf buffer) -> new DCMopenMessage(buffer.readInt(), buffer.readInt()));

	@Override
	public Type<DCMopenMessage> type() {
		return TYPE;
	}

}
