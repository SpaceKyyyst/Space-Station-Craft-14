package net.ssc14.integration.neoforge.player.network;

import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.ssc14.common.Ssc14Identity;

public record CrawlMessage(int eventType, int pressedms) implements CustomPacketPayload {
	public static final Type<CrawlMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, "key_crawl"));
	public static final StreamCodec<RegistryFriendlyByteBuf, CrawlMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, CrawlMessage message) -> {
		buffer.writeInt(message.eventType);
		buffer.writeInt(message.pressedms);
	}, (RegistryFriendlyByteBuf buffer) -> new CrawlMessage(buffer.readInt(), buffer.readInt()));

	@Override
	public Type<CrawlMessage> type() {
		return TYPE;
	}

}
