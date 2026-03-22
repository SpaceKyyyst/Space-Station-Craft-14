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

import net.mcreator.ssc.procedures.MacFrameGUIBlockUpdateProcedure;
import net.mcreator.ssc.Ssc14Mod;

@EventBusSubscriber
public record MacFrameGUISlotMessage(int slotID, int x, int y, int z, int changeType, int meta) implements CustomPacketPayload {

	public static final Type<MacFrameGUISlotMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Ssc14Mod.MODID, "mac_frame_gui_slots"));
	public static final StreamCodec<RegistryFriendlyByteBuf, MacFrameGUISlotMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, MacFrameGUISlotMessage message) -> {
		buffer.writeInt(message.slotID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
		buffer.writeInt(message.changeType);
		buffer.writeInt(message.meta);
	}, (RegistryFriendlyByteBuf buffer) -> new MacFrameGUISlotMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));
	@Override
	public Type<MacFrameGUISlotMessage> type() {
		return TYPE;
	}

	public static void handleData(final MacFrameGUISlotMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleSlotAction(context.player(), message.slotID, message.changeType, message.meta, message.x, message.y, message.z)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleSlotAction(Player entity, int slot, int changeType, int meta, int x, int y, int z) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (slot == 0 && changeType == 0) {

			MacFrameGUIBlockUpdateProcedure.execute(world, x, y, z);
		}
		if (slot == 1 && changeType == 0) {

			MacFrameGUIBlockUpdateProcedure.execute(world, x, y, z);
		}
		if (slot == 2 && changeType == 0) {

			MacFrameGUIBlockUpdateProcedure.execute(world, x, y, z);
		}
		if (slot == 3 && changeType == 0) {

			MacFrameGUIBlockUpdateProcedure.execute(world, x, y, z);
		}
		if (slot == 4 && changeType == 0) {

			MacFrameGUIBlockUpdateProcedure.execute(world, x, y, z);
		}
		if (slot == 5 && changeType == 0) {

			MacFrameGUIBlockUpdateProcedure.execute(world, x, y, z);
		}
		if (slot == 6 && changeType == 0) {

			MacFrameGUIBlockUpdateProcedure.execute(world, x, y, z);
		}
		if (slot == 7 && changeType == 0) {

			MacFrameGUIBlockUpdateProcedure.execute(world, x, y, z);
		}
		if (slot == 8 && changeType == 0) {

			MacFrameGUIBlockUpdateProcedure.execute(world, x, y, z);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		Ssc14Mod.addNetworkMessage(MacFrameGUISlotMessage.TYPE, MacFrameGUISlotMessage.STREAM_CODEC, MacFrameGUISlotMessage::handleData);
	}
}