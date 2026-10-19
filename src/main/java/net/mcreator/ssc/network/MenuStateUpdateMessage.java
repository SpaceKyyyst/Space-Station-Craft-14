package net.mcreator.ssc.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.client.Minecraft;

import net.mcreator.ssc.init.Ssc14ModScreens;
import net.mcreator.ssc.init.Ssc14ModMenus;
import net.mcreator.ssc.Ssc14Mod;
import net.mcreator.ssc.network.validation.MenuStateRules;
import net.mcreator.ssc.network.validation.PayloadLimits;

@EventBusSubscriber
public record MenuStateUpdateMessage(int elementType, String name, Object elementState) implements CustomPacketPayload {
	public MenuStateUpdateMessage {
		MenuStateRules.requireValid(elementType, name, elementState);
	}
	public static final Type<MenuStateUpdateMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Ssc14Mod.MODID, "menustate_update"));
	public static final StreamCodec<RegistryFriendlyByteBuf, MenuStateUpdateMessage> STREAM_CODEC = StreamCodec.of(MenuStateUpdateMessage::write, MenuStateUpdateMessage::read);

	public static void write(FriendlyByteBuf buffer, MenuStateUpdateMessage message) {
		buffer.writeInt(message.elementType);
		buffer.writeUtf(message.name, PayloadLimits.MAX_KEY_LENGTH);
		if (message.elementType == 0) {
			buffer.writeUtf((String) message.elementState, PayloadLimits.MAX_TEXT_LENGTH);
		} else if (message.elementType == 1) {
			buffer.writeBoolean((boolean) message.elementState);
		} else if (message.elementType == 2 && message.elementState instanceof Number n) {
			buffer.writeDouble(n.doubleValue());
		}
	}

	public static MenuStateUpdateMessage read(FriendlyByteBuf buffer) {
		int elementType = buffer.readInt();
		String name = buffer.readUtf(PayloadLimits.MAX_KEY_LENGTH);
		Object elementState = null;
		if (elementType == 0) {
			elementState = buffer.readUtf(PayloadLimits.MAX_TEXT_LENGTH);
		} else if (elementType == 1) {
			elementState = buffer.readBoolean();
		} else if (elementType == 2) {
			elementState = buffer.readDouble();
		}
		return new MenuStateUpdateMessage(elementType, name, elementState);
	}

	@Override
	public Type<MenuStateUpdateMessage> type() {
		return TYPE;
	}

	public static void handleMenuState(final MenuStateUpdateMessage message, final IPayloadContext context) {
		context.enqueueWork(() -> {
			if (context.player().containerMenu instanceof Ssc14ModMenus.MenuAccessor menu) {
				if (context.flow() == PacketFlow.SERVERBOUND
						&& (!MenuPacketGuard.canEditMenuState(context.player())
								|| !MenuStateRules.acceptsClientField(context.player().containerMenu.getClass().getSimpleName(),
										message.elementType, message.name))) {
					return;
				}
				menu.getMenuState().put(message.elementType + ":" + message.name, message.elementState);
				if (context.flow() == PacketFlow.CLIENTBOUND && Minecraft.getInstance().screen instanceof Ssc14ModScreens.ScreenAccessor accessor) {
					accessor.updateMenuState(message.elementType, message.name, message.elementState);
				}
			}
		}).exceptionally(e -> {
			Ssc14Mod.LOGGER.error("Failed to apply menu state", e);
			context.connection().disconnect(Component.literal("Invalid menu state"));
			return null;
		});
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		Ssc14Mod.addBidirectionalNetworkMessage(MenuStateUpdateMessage.TYPE, MenuStateUpdateMessage.STREAM_CODEC, MenuStateUpdateMessage::handleMenuState);
	}
}
