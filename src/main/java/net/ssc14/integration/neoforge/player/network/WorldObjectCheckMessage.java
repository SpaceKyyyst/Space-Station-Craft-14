package net.ssc14.integration.neoforge.player.network;

import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.ssc14.common.Ssc14Identity;

public record WorldObjectCheckMessage(int eventType, int pressedms) implements CustomPacketPayload {
	public static final Type<WorldObjectCheckMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, "key_world_object_check"));
	public static final StreamCodec<RegistryFriendlyByteBuf, WorldObjectCheckMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, WorldObjectCheckMessage message) -> {
		buffer.writeInt(message.eventType);
		buffer.writeInt(message.pressedms);
	}, (RegistryFriendlyByteBuf buffer) -> new WorldObjectCheckMessage(buffer.readInt(), buffer.readInt()));

	@Override
	public Type<WorldObjectCheckMessage> type() {
		return TYPE;
	}

}
