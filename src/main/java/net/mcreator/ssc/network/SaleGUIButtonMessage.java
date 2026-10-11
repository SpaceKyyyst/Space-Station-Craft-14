
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

import net.mcreator.ssc.procedures.Sale_GUI_buttonSALEProcedure;
import net.mcreator.ssc.Ssc14Mod;

@EventBusSubscriber
public record SaleGUIButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {
	public static final Type<SaleGUIButtonMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Ssc14Mod.MODID, "sale_gui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SaleGUIButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, SaleGUIButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new SaleGUIButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));

	@Override
	public Type<SaleGUIButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final SaleGUIButtonMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		if (!world.getChunkSource().hasChunk(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z)))
			return;
			
		if (!world.isClientSide()) {
			if (buttonID == 0) {
				// Передаем координаты консоли и игрока для проведения транзакции сбыта
				Sale_GUI_buttonSALEProcedure.execute(world, x, y, z, entity);
			}
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		Ssc14Mod.addNetworkMessage(SaleGUIButtonMessage.TYPE, SaleGUIButtonMessage.STREAM_CODEC, SaleGUIButtonMessage::handleData);
	}
}
