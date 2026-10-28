package net.ssc14.integration.neoforge.player.network;

import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;

import net.ssc14.common.Ssc14Identity;
import net.ssc14.integration.neoforge.network.CompatibilityPayloadRegistrar;

@EventBusSubscriber
public record InteractionMessage(int eventType, int pressedms) implements CustomPacketPayload {
	private static final String PROTOCOL_VERSION = "ssc_14";
	public static final Type<InteractionMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, "key_interaction"));
	public static final StreamCodec<RegistryFriendlyByteBuf, InteractionMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, InteractionMessage message) -> {
		buffer.writeInt(message.eventType);
		buffer.writeInt(message.pressedms);
	}, (RegistryFriendlyByteBuf buffer) -> new InteractionMessage(buffer.readInt(), buffer.readInt()));

	@Override
	public Type<InteractionMessage> type() {
		return TYPE;
	}

	@SubscribeEvent
	public static void registerMessage(RegisterPayloadHandlersEvent event) {
		var registrar = new CompatibilityPayloadRegistrar(
				event.registrar(PROTOCOL_VERSION), PROTOCOL_VERSION);
		registrar.playToServer(TYPE, STREAM_CODEC, (message, context) -> { });
	}
}
