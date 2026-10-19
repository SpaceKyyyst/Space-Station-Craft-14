package net.mcreator.ssc.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;

import net.mcreator.ssc.rotation.RotateCommand;
import net.mcreator.ssc.Ssc14Mod;

@EventBusSubscriber
public record RotateMessage(int eventType, int pressedms) implements CustomPacketPayload {
	public static final Type<RotateMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Ssc14Mod.MODID, "key_rotate"));
	public static final StreamCodec<RegistryFriendlyByteBuf, RotateMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, RotateMessage message) -> {
		buffer.writeInt(message.eventType);
		buffer.writeInt(message.pressedms);
	}, (RegistryFriendlyByteBuf buffer) -> new RotateMessage(buffer.readInt(), buffer.readInt()));

	@Override
	public Type<RotateMessage> type() {
		return TYPE;
	}

	public static void handleData(final RotateMessage message, final IPayloadContext context) {
		// Registration uses PayloadRegistrar's MAIN/serverbound handler.
		if (context.flow() == PacketFlow.SERVERBOUND && context.player() instanceof ServerPlayer player) {
			RotateCommand.execute(player, message.eventType);
		}
	}

	public static void pressAction(Player entity, int type, int pressedms) {
		if (entity instanceof ServerPlayer player) {
			RotateCommand.execute(player, type);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		Ssc14Mod.addServerboundNetworkMessage(RotateMessage.TYPE, RotateMessage.STREAM_CODEC, RotateMessage::handleData);
	}
}
