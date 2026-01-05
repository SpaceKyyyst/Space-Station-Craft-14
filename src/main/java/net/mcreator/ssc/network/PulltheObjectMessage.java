package net.mcreator.ssc.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;

import net.mcreator.ssc.procedures.PulltheObject_PrProcedure;
import net.mcreator.ssc.procedures.PulltheObject_Pr2Procedure;
import net.mcreator.ssc.Ssc14Mod;

@EventBusSubscriber
public record PulltheObjectMessage(int eventType, int pressedms) implements CustomPacketPayload {
	public static final Type<PulltheObjectMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Ssc14Mod.MODID, "key_pullthe_object"));
	public static final StreamCodec<RegistryFriendlyByteBuf, PulltheObjectMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, PulltheObjectMessage message) -> {
		buffer.writeInt(message.eventType);
		buffer.writeInt(message.pressedms);
	}, (RegistryFriendlyByteBuf buffer) -> new PulltheObjectMessage(buffer.readInt(), buffer.readInt()));

	@Override
	public Type<PulltheObjectMessage> type() {
		return TYPE;
	}

	public static void handleData(final PulltheObjectMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> {
				pressAction(context.player(), message.eventType, message.pressedms);
			}).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void pressAction(Player entity, int type, int pressedms) {
		Level world = entity.level();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(entity.blockPosition()))
			return;
		if (type == 0) {

			PulltheObject_PrProcedure.execute(entity);
		}
		if (type == 1) {

			PulltheObject_Pr2Procedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		Ssc14Mod.addNetworkMessage(PulltheObjectMessage.TYPE, PulltheObjectMessage.STREAM_CODEC, PulltheObjectMessage::handleData);
	}
}