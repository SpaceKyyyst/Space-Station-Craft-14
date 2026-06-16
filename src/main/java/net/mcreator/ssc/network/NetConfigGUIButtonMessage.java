
package net.mcreator.ssc.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.SectionPos;
import net.mcreator.ssc.Ssc14Mod;
import net.mcreator.ssc.NetConfigGUISyncMessage;
import net.mcreator.ssc.world.inventory.NetConfigGUIMenu;
import net.mcreator.ssc.NetworkSavedData;

import java.util.Map;

@EventBusSubscriber
public record NetConfigGUIButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {
    public static final Type<NetConfigGUIButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Ssc14Mod.MODID, "net_config_gui_buttons"));

    public static final StreamCodec<RegistryFriendlyByteBuf, NetConfigGUIButtonMessage> STREAM_CODEC = StreamCodec.of(
            (RegistryFriendlyByteBuf buffer, NetConfigGUIButtonMessage message) -> {
                buffer.writeInt(message.buttonID);
                buffer.writeInt(message.x);
                buffer.writeInt(message.y);
                buffer.writeInt(message.z);
            },
            (RegistryFriendlyByteBuf buffer) -> new NetConfigGUIButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt())
    );

    @Override
    public Type<NetConfigGUIButtonMessage> type() {
        return TYPE;
    }

    public static void handleData(final NetConfigGUIButtonMessage message, final IPayloadContext context) {
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

        if (!(entity.containerMenu instanceof NetConfigGUIMenu menu)) return;

        Map<String, Object> state = menu.getMenuState();
        NetworkSavedData data = NetworkSavedData.get(world);

        if (buttonID >= 0 && buttonID <= 7) {
            String triggerId = (String) state.get("L" + (buttonID + 1) + "_id");
            Boolean isActive = (Boolean) state.get("L" + (buttonID + 1) + "_active");

            if (triggerId != null && Boolean.TRUE.equals(isActive)) {
                state.put("selected_trigger_idx", buttonID);
                state.put("selected_trigger_id", triggerId);
            }
        } else if (buttonID >= 8 && buttonID <= 15) {
            int rightIndex = buttonID - 7;
            String actionId = (String) state.get("R" + rightIndex + "_id");
            Boolean isActive = (Boolean) state.get("R" + rightIndex + "_active");

            if (actionId != null && Boolean.TRUE.equals(isActive)) {
                Integer selectedTriggerIdx = (Integer) state.get("selected_trigger_idx");
                String selectedTriggerId = (String) state.get("selected_trigger_id");

                if (selectedTriggerIdx != null && selectedTriggerId != null) {
                    boolean connectionExists = data.connections.stream()
                        .anyMatch(c -> c.sourcePos.equals(menu.sourcePos) && c.triggerId.equals(selectedTriggerId)
                                    && c.targetPos.equals(menu.targetPos) && c.actionId.equals(actionId));

                    if (connectionExists) {
                        data.removeConnection(menu.sourcePos, selectedTriggerId, menu.targetPos, actionId);
                        if (entity instanceof ServerPlayer serverPlayer) {
                            serverPlayer.sendSystemMessage(Component.literal("Связь удалена!"));
                        }
                    } else {
                        data.addConnection(menu.sourcePos, selectedTriggerId, menu.targetPos, actionId);
                        if (entity instanceof ServerPlayer serverPlayer) {
                            serverPlayer.sendSystemMessage(Component.literal("Связь создана!"));
                        }
                    }

                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            String tId = (String) state.get("L" + (i + 1) + "_id");
                            String aId = (String) state.get("R" + (j + 1) + "_id");
                            if (tId != null && aId != null) {
                                boolean hasConn = data.connections.stream()
                                    .anyMatch(c -> c.sourcePos.equals(menu.sourcePos) && c.triggerId.equals(tId)
                                                && c.targetPos.equals(menu.targetPos) && c.actionId.equals(aId));
                                state.put("conn_" + i + "_" + j, hasConn);
                            }
                        }
                    }

                    state.put("selected_trigger_idx", -1);
                    state.put("selected_trigger_id", null);
                } else {
                    if (entity instanceof ServerPlayer serverPlayer) {
                        serverPlayer.sendSystemMessage(Component.literal("Сначала выберите триггер слева!"));
                    }
                }
            }
        }

        // Синхронизируем menuState с клиентом
        if (entity instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new NetConfigGUISyncMessage(state));
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        Ssc14Mod.addNetworkMessage(NetConfigGUIButtonMessage.TYPE, NetConfigGUIButtonMessage.STREAM_CODEC, NetConfigGUIButtonMessage::handleData);
    }
}
