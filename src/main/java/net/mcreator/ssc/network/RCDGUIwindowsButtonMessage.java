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

import net.mcreator.ssc.procedures.*;
import net.mcreator.ssc.Ssc14Mod;

@EventBusSubscriber
public record RCDGUIwindowsButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {
	public static final Type<RCDGUIwindowsButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Ssc14Mod.MODID, "rcdgu_iwindows_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, RCDGUIwindowsButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, RCDGUIwindowsButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new RCDGUIwindowsButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));

	@Override
	public Type<RCDGUIwindowsButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final RCDGUIwindowsButtonMessage message, final IPayloadContext context) {
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

			RCDGUIgrilleButtonProcedure.execute(entity);
		}
		if (buttonID == 2) {

			RCDGUIarmWindowButtonProcedure.execute(entity);
		}
		if (buttonID == 3) {

			RCDGUIwindowButtonProcedure.execute(entity);
		}
		if (buttonID == 4) {

			RCDGUIdirArmWindowButtonProcedure.execute(entity);
		}
		if (buttonID == 5) {

			RCDGUIdirwindowButtonProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		Ssc14Mod.addNetworkMessage(RCDGUIwindowsButtonMessage.TYPE, RCDGUIwindowsButtonMessage.STREAM_CODEC, RCDGUIwindowsButtonMessage::handleData);
	}
}