package net.mcreator.ssc.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.SectionPos;

import net.mcreator.ssc.procedures.*;
import net.mcreator.ssc.Ssc14Mod;

@EventBusSubscriber
public record MicrowaweGUIButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {
	public static final Type<MicrowaweGUIButtonMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Ssc14Mod.MODID, "microwawe_gui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, MicrowaweGUIButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, MicrowaweGUIButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new MicrowaweGUIButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));

	@Override
	public Type<MicrowaweGUIButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final MicrowaweGUIButtonMessage message, final IPayloadContext context) {
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

			MicrwGUIbut1Procedure.execute(world, x, y, z);
		}
		if (buttonID == 1) {

			MicrwGUIbut5Procedure.execute(world, x, y, z);
		}
		if (buttonID == 2) {

			MicrwGUIbut10Procedure.execute(world, x, y, z);
		}
		if (buttonID == 3) {

			MicrwGUIbut15Procedure.execute(world, x, y, z);
		}
		if (buttonID == 4) {

			MicrwGUIbut20Procedure.execute(world, x, y, z);
		}
		if (buttonID == 5) {

			MicrwGUIbut25Procedure.execute(world, x, y, z);
		}
		if (buttonID == 6) {

			MicrwGUIbutCProcedure.execute(world, x, y, z);
		}
		if (buttonID == 7) {

			MicrwGUIbut30Procedure.execute(world, x, y, z);
		}
		if (buttonID == 8) {

			MicrwGUIbutEProcedure.execute(world, x, y, z);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		Ssc14Mod.addNetworkMessage(MicrowaweGUIButtonMessage.TYPE, MicrowaweGUIButtonMessage.STREAM_CODEC, MicrowaweGUIButtonMessage::handleData);
	}
}