
package net.mcreator.ssc.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.chat.Component;
import net.ssc14.integration.neoforge.network.NetConfigGUISyncMessage;
import net.mcreator.ssc.world.inventory.NetConfigGUIMenu;
import net.ssc14.content.network.NetworkSavedData;
import net.ssc14.common.network.NetworkAction;
import net.ssc14.common.network.NetworkTrigger;
import net.ssc14.content.item.Ssc14Items;
import net.ssc14.integration.neoforge.menu.network.MenuButtonPayload;
import net.ssc14.integration.neoforge.network.Ssc14NetworkPayloads;

import java.util.Map;

@EventBusSubscriber
public final class NetConfigGUIButtonRegistration {
    private NetConfigGUIButtonRegistration() { }

    public static void handleData(final MenuButtonPayload message, final IPayloadContext context) {
        if (context.flow() == PacketFlow.SERVERBOUND) {
            context.enqueueWork(() -> handleButtonAction(context.player(), message.buttonId(), message.x(), message.y(), message.z())).exceptionally(e -> {
                context.connection().disconnect(Component.literal(e.getMessage()));
                return null;
            });
        }
    }

    public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
        Level world = entity.level();
        if (!(entity.containerMenu instanceof NetConfigGUIMenu menu) || !MenuPacketGuard.matches(entity, menu, x, y, z, menu.x, menu.y, menu.z))
            return;

        if (world.isClientSide() || buttonID < 0 || buttonID > 15 || menu.sourcePos == null || menu.targetPos == null
                || !entity.getMainHandItem().is(Ssc14Items.NETWORK_CONFIGURATOR.get())
                || !world.hasChunkAt(menu.sourcePos) || !world.mayInteract(entity, menu.sourcePos)
                || !(world.getBlockState(menu.sourcePos).getBlock() instanceof NetworkTrigger trigger)
                || !(world.getBlockState(menu.targetPos).getBlock() instanceof NetworkAction action)) return;

        Map<String, Object> state = menu.getMenuState();
        NetworkSavedData data = NetworkSavedData.get(world);

        if (buttonID >= 0 && buttonID <= 7) {
            String triggerId = (String) state.get("L" + (buttonID + 1) + "_id");
            Boolean isActive = (Boolean) state.get("L" + (buttonID + 1) + "_active");

            if (triggerId != null && Boolean.TRUE.equals(isActive) && trigger.getAvailableTriggers().contains(triggerId)) {
                state.put("selected_trigger_idx", buttonID);
                state.put("selected_trigger_id", triggerId);
            }
        } else if (buttonID >= 8 && buttonID <= 15) {
            int rightIndex = buttonID - 7;
            String actionId = (String) state.get("R" + rightIndex + "_id");
            Boolean isActive = (Boolean) state.get("R" + rightIndex + "_active");

            if (actionId != null && Boolean.TRUE.equals(isActive) && action.getAvailableActions().contains(actionId)) {
                Integer selectedTriggerIdx = (Integer) state.get("selected_trigger_idx");
                String selectedTriggerId = (String) state.get("selected_trigger_id");

                if (selectedTriggerIdx != null && selectedTriggerIdx >= 0 && selectedTriggerIdx < 8
                        && selectedTriggerId != null && trigger.getAvailableTriggers().contains(selectedTriggerId)) {
                    boolean connectionExists = data.connections.stream()
                        .anyMatch(c -> c.sourcePos.equals(menu.sourcePos) && c.triggerId.equals(selectedTriggerId)
                                    && c.targetPos.equals(menu.targetPos) && c.actionId.equals(actionId));

                    if (connectionExists) {
                        data.removeConnection(menu.sourcePos, selectedTriggerId, menu.targetPos, actionId);
                        if (entity instanceof ServerPlayer sp) sp.sendSystemMessage(Component.literal("Связь удалена!"));
                    } else {
                        data.addConnection(menu.sourcePos, selectedTriggerId, menu.targetPos, actionId);
                        if (entity instanceof ServerPlayer sp) sp.sendSystemMessage(Component.literal("Связь создана!"));
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
                    state.remove("selected_trigger_id");
                } else {
                    if (entity instanceof ServerPlayer sp) sp.sendSystemMessage(Component.literal("Сначала выберите триггер слева!"));
                }
            }
        }

        if (entity instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new NetConfigGUISyncMessage(state));
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        var channel = MenuButtonPayload.Channel.NETWORK_CONFIG;
        Ssc14NetworkPayloads.addServerbound(channel.type(), MenuButtonPayload.codec(channel), NetConfigGUIButtonRegistration::handleData);
    }
}
