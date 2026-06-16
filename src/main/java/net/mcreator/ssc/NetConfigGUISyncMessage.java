
package net.mcreator.ssc;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.mcreator.ssc.world.inventory.NetConfigGUIMenu;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber
public record NetConfigGUISyncMessage(Map<String, Object> state) implements CustomPacketPayload {
    public static final Type<NetConfigGUISyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Ssc14Mod.MODID, "net_config_gui_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, NetConfigGUISyncMessage> STREAM_CODEC = StreamCodec.of(
            (RegistryFriendlyByteBuf buffer, NetConfigGUISyncMessage message) -> {
                buffer.writeInt(message.state.size());
                for (Map.Entry<String, Object> entry : message.state.entrySet()) {
                    buffer.writeUtf(entry.getKey());
                    Object value = entry.getValue();
                    if (value instanceof String s) {
                        buffer.writeByte(0);
                        buffer.writeUtf(s);
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
                int size = buffer.readInt();
                Map<String, Object> state = new HashMap<>();
                for (int i = 0; i < size; i++) {
                    String key = buffer.readUtf();
                    byte type = buffer.readByte();
                    Object value = switch (type) {
                        case 0 -> buffer.readUtf();
                        case 1 -> buffer.readBoolean();
                        case 2 -> buffer.readInt();
                        default -> buffer.readUtf();
                    };
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
	            System.out.println("[SSC14-DEBUG-CLIENT] Получен пакет синхронизации! Размер state: " + message.state.size());
	            System.out.println("[SSC14-DEBUG-CLIENT] L1_text из пакета: " + message.state.get("L1_text"));
	            
	            if (player.containerMenu instanceof NetConfigGUIMenu menu) {
	                System.out.println("[SSC14-DEBUG-CLIENT] Применяем state к menu.menuState");
	                menu.menuState.clear();
	                menu.menuState.putAll(message.state);
	                System.out.println("[SSC14-DEBUG-CLIENT] После применения menuState размер: " + menu.menuState.size());
	            } else {
	                System.out.println("[SSC14-DEBUG-CLIENT] ОШИБКА: containerMenu не NetConfigGUIMenu! Тип: " + 
	                    (player.containerMenu != null ? player.containerMenu.getClass().getSimpleName() : "null"));
	            }
	        }).exceptionally(e -> {
	            context.connection().disconnect(net.minecraft.network.chat.Component.literal(e.getMessage()));
	            return null;
	        });
	    }
	}

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        Ssc14Mod.addNetworkMessage(NetConfigGUISyncMessage.TYPE, NetConfigGUISyncMessage.STREAM_CODEC, NetConfigGUISyncMessage::handleData);
    }
}
