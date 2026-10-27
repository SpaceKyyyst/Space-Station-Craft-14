
package net.mcreator.ssc;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.Identifier; // ИСПРАВЛЕНО
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.mcreator.ssc.world.inventory.NetConfigGUIMenu;
import net.ssc14.core.network.PayloadLimits;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber
public record NetConfigGUISyncMessage(Map<String, Object> state) implements CustomPacketPayload {
    public NetConfigGUISyncMessage {
        state = PayloadLimits.snapshot(state);
    }
    // ИСПРАВЛЕНО: Регистрация типа пакета через Identifier.fromNamespaceAndPath
    public static final Type<NetConfigGUISyncMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Ssc14Mod.MODID, "net_config_gui_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, NetConfigGUISyncMessage> STREAM_CODEC = StreamCodec.of(
            (RegistryFriendlyByteBuf buffer, NetConfigGUISyncMessage message) -> {
                buffer.writeInt(message.state.size());
                for (Map.Entry<String, Object> entry : message.state.entrySet()) {
                    buffer.writeUtf(entry.getKey(), PayloadLimits.MAX_KEY_LENGTH);
                    Object value = entry.getValue();
                    if (value instanceof String s) {
                        buffer.writeByte(0);
                        buffer.writeUtf(s, PayloadLimits.MAX_TEXT_LENGTH);
                    } else if (value instanceof Boolean b) {
                        buffer.writeByte(1);
                        buffer.writeBoolean(b);
                    } else if (value instanceof Integer i) {
                        buffer.writeByte(2);
                        buffer.writeInt(i);
                    } else {
                        buffer.writeByte(3);
                        buffer.writeUtf(String.valueOf(value));
                    }
                }
            },
            (RegistryFriendlyByteBuf buffer) -> {
                int size = PayloadLimits.requireCount(buffer.readInt(), PayloadLimits.MAX_STATE_ENTRIES);
                Map<String, Object> state = new HashMap<>();
                int textLength = 0;
                for (int i = 0; i < size; i++) {
                    String key = buffer.readUtf(PayloadLimits.MAX_KEY_LENGTH);
                    byte type = buffer.readByte();
                    Object value = switch (type) {
                        case 0, 3 -> buffer.readUtf(PayloadLimits.MAX_TEXT_LENGTH);
                        case 1 -> buffer.readBoolean();
                        case 2 -> buffer.readInt();
                        default -> throw new IllegalArgumentException("Unknown GUI state type: " + type);
                    };
                    textLength += key.length() + (value instanceof String text ? text.length() : 0);
                    PayloadLimits.requireCount(textLength, PayloadLimits.MAX_TOTAL_TEXT_LENGTH);
                    if (state.containsKey(key)) {
                        throw new IllegalArgumentException("Duplicate GUI state key");
                    }
                    state.put(key, value);
                }
                return new NetConfigGUISyncMessage(state);
            }
    );

    @Override
    public Type<NetConfigGUISyncMessage> type() {
        return TYPE;
    }

	public static void handleData(final NetConfigGUISyncMessage message, final IPayloadContext context) {
	    if (context.flow() == PacketFlow.CLIENTBOUND) {
	        context.enqueueWork(() -> {
	            Player player = context.player();
	            if (player.containerMenu instanceof NetConfigGUIMenu menu) {
	                menu.menuState.clear();
	                menu.menuState.putAll(message.state);
	            }
	        }).exceptionally(e -> {
	            Ssc14Mod.LOGGER.error("Failed to apply network configurator state", e);
	            context.connection().disconnect(net.minecraft.network.chat.Component.literal("Invalid network configurator state"));
	            return null;
	        });
	    }
	}

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        Ssc14Mod.addClientboundNetworkMessage(NetConfigGUISyncMessage.TYPE, NetConfigGUISyncMessage.STREAM_CODEC, NetConfigGUISyncMessage::handleData);
    }
}
