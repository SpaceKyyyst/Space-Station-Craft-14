
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
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.procedures.*;
import net.mcreator.ssc.Ssc14Mod;
import net.mcreator.ssc.world.inventory.OrderGUIMenu;

@EventBusSubscriber
public record OrderGUIButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {
	public static final Type<OrderGUIButtonMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Ssc14Mod.MODID, "order_gui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, OrderGUIButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, OrderGUIButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new OrderGUIButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));

	@Override
	public Type<OrderGUIButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final OrderGUIButtonMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		if (!(entity.containerMenu instanceof OrderGUIMenu menu) || !MenuPacketGuard.matches(entity, menu, x, y, z, menu.x, menu.y, menu.z))
            return;

		if (!world.isClientSide()) {
			// Если пришел ID от 0 до 14 — вызываем новую универсальную процедуру переключения вкладок
			if (buttonID >= 0 && buttonID <= 14) {
				OrderGUISwitchTabProcedure.execute(world, x, y, z, buttonID);
			}

			// Если пришел ID от 100 до 109 — вызываем процедуру покупки товара
			if (buttonID >= 100 && buttonID <= 109) {
				int targetSlot = buttonID - 100;
				CargoBuyProcedure.execute(world, x, y, z, entity, targetSlot);
			}
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		Ssc14Mod.addServerboundNetworkMessage(OrderGUIButtonMessage.TYPE, OrderGUIButtonMessage.STREAM_CODEC, OrderGUIButtonMessage::handleData);
	}
}
