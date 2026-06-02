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
import net.minecraft.core.SectionPos;

import net.mcreator.ssc.procedures.RCDGUIwallButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUItileButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIsheatingButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIcatwalkButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIbackButtonProcedure;
import net.mcreator.ssc.Ssc14Mod;

@EventBusSubscriber
public record RCDGUIwallfloorButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {
	public static final Type<RCDGUIwallfloorButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Ssc14Mod.MODID, "rcdgu_iwallfloor_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, RCDGUIwallfloorButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, RCDGUIwallfloorButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new RCDGUIwallfloorButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));

	@Override
	public Type<RCDGUIwallfloorButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final RCDGUIwallfloorButtonMessage message, final IPayloadContext context) {
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
		if (!world.getChunkSource().hasChunk(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z)))
			return;
		if (buttonID == 0) {

			RCDGUIbackButtonProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 1) {

			RCDGUIwallButtonProcedure.execute(entity);
		}
		if (buttonID == 2) {

			RCDGUIcatwalkButtonProcedure.execute(entity);
		}
		if (buttonID == 3) {

			RCDGUItileButtonProcedure.execute(entity);
		}
		if (buttonID == 4) {

			RCDGUIsheatingButtonProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		Ssc14Mod.addNetworkMessage(RCDGUIwallfloorButtonMessage.TYPE, RCDGUIwallfloorButtonMessage.STREAM_CODEC, RCDGUIwallfloorButtonMessage::handleData);
	}
}