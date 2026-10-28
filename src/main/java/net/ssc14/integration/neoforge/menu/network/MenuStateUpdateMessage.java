package net.ssc14.integration.neoforge.menu.network;

import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.core.network.MenuStateRules;
import net.ssc14.core.network.PayloadLimits;

public record MenuStateUpdateMessage(int elementType, String name, Object elementState) implements CustomPacketPayload {
	public MenuStateUpdateMessage {
		MenuStateRules.requireValid(elementType, name, elementState);
	}
	public static final Type<MenuStateUpdateMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, "menustate_update"));
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

}
