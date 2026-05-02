package net.mcreator.ssc.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;

import net.mcreator.ssc.Ssc14Mod;

@EventBusSubscriber
public record InteractionMessage(int eventType, int pressedms) implements CustomPacketPayload {
	public static final Type<InteractionMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Ssc14Mod.MODID, "key_interaction"));
	public static final StreamCodec<RegistryFriendlyByteBuf, InteractionMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, InteractionMessage message) -> {
		buffer.writeInt(message.eventType);
		buffer.writeInt(message.pressedms);
	}, (RegistryFriendlyByteBuf buffer) -> new InteractionMessage(buffer.readInt(), buffer.readInt()));

	@Override
	public Type<InteractionMessage> type() {
		return TYPE;
	}

	public static void handleData(final InteractionMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> {
			}).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		Ssc14Mod.addNetworkMessage(InteractionMessage.TYPE, InteractionMessage.STREAM_CODEC, InteractionMessage::handleData);
	}
}