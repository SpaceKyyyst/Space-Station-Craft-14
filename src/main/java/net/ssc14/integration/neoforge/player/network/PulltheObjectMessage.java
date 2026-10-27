package net.ssc14.integration.neoforge.player.network;

import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.ssc14.common.Ssc14Identity;

public record PulltheObjectMessage(int eventType, int pressedms) implements CustomPacketPayload {
	public static final Type<PulltheObjectMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, "key_pullthe_object"));
	public static final StreamCodec<RegistryFriendlyByteBuf, PulltheObjectMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, PulltheObjectMessage message) -> {
		buffer.writeInt(message.eventType);
		buffer.writeInt(message.pressedms);
	}, (RegistryFriendlyByteBuf buffer) -> new PulltheObjectMessage(buffer.readInt(), buffer.readInt()));

	@Override
	public Type<PulltheObjectMessage> type() {
		return TYPE;
	}

}
