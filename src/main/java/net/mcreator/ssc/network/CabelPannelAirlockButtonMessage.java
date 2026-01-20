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
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.procedures.*;
import net.mcreator.ssc.Ssc14Mod;

@EventBusSubscriber
public record CabelPannelAirlockButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {

	public static final Type<CabelPannelAirlockButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Ssc14Mod.MODID, "cabel_pannel_airlock_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, CabelPannelAirlockButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, CabelPannelAirlockButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new CabelPannelAirlockButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));
	@Override
	public Type<CabelPannelAirlockButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final CabelPannelAirlockButtonMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			CPcabel1buttonProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 1) {

			CPcabel2buttonProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 2) {

			CPcabel3buttonProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 3) {

			CPcabel4buttonProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 4) {

			CPcabel5buttonProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 5) {

			CPcabel6buttonProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 6) {

			CPcabel7buttonProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 7) {

			CPcabel8buttonProcedure.execute(world, x, y, z, entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		Ssc14Mod.addNetworkMessage(CabelPannelAirlockButtonMessage.TYPE, CabelPannelAirlockButtonMessage.STREAM_CODEC, CabelPannelAirlockButtonMessage::handleData);
	}
}