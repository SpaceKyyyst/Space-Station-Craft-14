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
public record VendingAut1GUIButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {
	public static final Type<VendingAut1GUIButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Ssc14Mod.MODID, "vending_aut_1_gui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, VendingAut1GUIButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, VendingAut1GUIButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new VendingAut1GUIButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));

	@Override
	public Type<VendingAut1GUIButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final VendingAut1GUIButtonMessage message, final IPayloadContext context) {
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

			VendAutGUIButton1Procedure.execute(world, x, y, z);
		}
		if (buttonID == 1) {

			VendAutGUIButton2Procedure.execute(world, x, y, z);
		}
		if (buttonID == 2) {

			VendAutGUIButton3Procedure.execute(world, x, y, z);
		}
		if (buttonID == 3) {

			VendAutGUIButton4Procedure.execute(world, x, y, z);
		}
		if (buttonID == 4) {

			VendAutGUIButton5Procedure.execute(world, x, y, z);
		}
		if (buttonID == 5) {

			VendAutGUIButton6Procedure.execute(world, x, y, z);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		Ssc14Mod.addNetworkMessage(VendingAut1GUIButtonMessage.TYPE, VendingAut1GUIButtonMessage.STREAM_CODEC, VendingAut1GUIButtonMessage::handleData);
	}
}