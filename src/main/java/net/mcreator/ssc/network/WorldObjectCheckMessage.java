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

import net.mcreator.ssc.procedures.WorldObjectCheck_Pr_2Procedure;
import net.mcreator.ssc.procedures.WorldObjectCheck_PrProcedure;
import net.mcreator.ssc.Ssc14Mod;

@EventBusSubscriber
public record WorldObjectCheckMessage(int eventType, int pressedms) implements CustomPacketPayload {
	public static final Type<WorldObjectCheckMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Ssc14Mod.MODID, "key_world_object_check"));
	public static final StreamCodec<RegistryFriendlyByteBuf, WorldObjectCheckMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, WorldObjectCheckMessage message) -> {
		buffer.writeInt(message.eventType);
		buffer.writeInt(message.pressedms);
	}, (RegistryFriendlyByteBuf buffer) -> new WorldObjectCheckMessage(buffer.readInt(), buffer.readInt()));

	@Override
	public Type<WorldObjectCheckMessage> type() {
		return TYPE;
	}

	public static void handleData(final WorldObjectCheckMessage message, final IPayloadContext context) {
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

			WorldObjectCheck_PrProcedure.execute(entity);
		}
		if (type == 1) {

			WorldObjectCheck_Pr_2Procedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		Ssc14Mod.addNetworkMessage(WorldObjectCheckMessage.TYPE, WorldObjectCheckMessage.STREAM_CODEC, WorldObjectCheckMessage::handleData);
	}
}