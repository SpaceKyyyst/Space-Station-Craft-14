
package net.mcreator.ssc;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = "ssc_14")
public class LocalChatEventHandler {

    private static final double NORMAL_RADIUS = 10.0;
    private static final double WHISPER_RADIUS = 3.0;

    @SubscribeEvent
    public static void onPlayerChat(ServerChatEvent event) {
        Player sender = event.getPlayer();
        String originalText = event.getMessage().getString();
        
        MessageType type = MessageType.NORMAL;
        String messageText = originalText;
        
        if (originalText.startsWith(";")) {							// РАЦИЯ [ОБЩИЙ]
            type = MessageType.RADIO;
            messageText = originalText.substring(1).trim();
        } else if (originalText.startsWith(",")) {					// ШЁПОТ
            type = MessageType.WHISPER;
            messageText = originalText.substring(1).trim();
        } else if (originalText.startsWith("%")) {					// ЭМОЦИИ
            type = MessageType.EMOTE;
            messageText = originalText.substring(1).trim();
        } else if (originalText.startsWith("_")) {					// LOOC
            type = MessageType.LOOC;
            messageText = originalText.substring(1).trim();
        } else if (originalText.startsWith("[")) {					// OOC
            type = MessageType.OOC;
            messageText = originalText.substring(1).trim();
        }
        
        String playerName = sender.getDisplayName().getString();
        if (playerName == null || playerName.isEmpty()) {
            playerName = "Неизвестный";
        }
        
        boolean hasHeadset = hasHeadset(sender);
        
        event.setCanceled(true);
        
        // Обработка рации
        if (type == MessageType.RADIO) {
            Component radioMessage = Component.literal("[Общий] " + playerName + " говорит, \"" + messageText + "\"")
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x27DA33)));
            
            if (hasHeadset) {
                for (Player target : sender.level().players()) {
                    if (sender.level() == target.level() && hasHeadset(target)) {
                        target.displayClientMessage(radioMessage, false);
                    }
                }
            }
            
            Component whisperMessage = Component.literal(playerName + " шепчет, \"" + messageText + "\"")
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xAAAAAA)).withItalic(true));
            
            for (Player target : sender.level().players()) {
                if (sender.distanceTo(target) <= WHISPER_RADIUS) {
                    target.displayClientMessage(whisperMessage, false);
                }
            }
            
            return;
        }
        
        // Обработка OOC (глобальный)
        if (type == MessageType.OOC) {
            Component oocMessage = Component.literal("OOC: " + playerName + ": " + messageText)
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x5AA0D2)));
            
            // Отправляем всем игрокам на сервере
            var server = ServerLifecycleHooks.getCurrentServer();
            if (server != null) {
                for (Player target : server.getPlayerList().getPlayers()) {
                    target.displayClientMessage(oocMessage, false);
                }
            }
            
            return;
        }
        
        // Обработка LOOC (локальный)
        if (type == MessageType.LOOC) {
            Component loocMessage = Component.literal("LOOC: " + playerName + ": " + messageText)
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x46D7D2)));
            
            for (Player target : sender.level().players()) {
                if (sender.distanceTo(target) <= NORMAL_RADIUS) {
                    target.displayClientMessage(loocMessage, false);
                }
            }
            
            return;
        }
        
        // Обработка остальных типов (шепот, эмоции, обычная речь)
        Component formattedMessage;
        double radius = 0;
        
        switch (type) {
            case WHISPER:
                formattedMessage = Component.literal(playerName + " шепчет, \"" + messageText + "\"")
                    .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xAAAAAA)).withItalic(true));
                radius = WHISPER_RADIUS;
                break;
                
            case EMOTE:
                formattedMessage = Component.literal(playerName + " " + messageText)
                    .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xD3D3D3)).withItalic(true));
                radius = NORMAL_RADIUS;
                break;
                
            default:
                formattedMessage = Component.literal(playerName + " говорит, \"" + messageText + "\"")
                    .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFFF)));
                radius = NORMAL_RADIUS;
                break;
        }
        
        for (Player target : sender.level().players()) {
            if (sender.distanceTo(target) <= radius) {
                target.displayClientMessage(formattedMessage, false);
            }
        }
    }
    
    private static boolean hasHeadset(Player player) {
        var headsetTag = ItemTags.create(ResourceLocation.fromNamespaceAndPath("ssc14", "headsets"));
        
        var inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && stack.is(headsetTag)) {
                return true;
            }
        }
        
        try {
            CuriosApi.getCuriosInventory(player).ifPresent(invHandler -> {
                var earHandler = invHandler.getStacksHandler("ear");
                if (earHandler.isPresent()) {
                    IItemHandler handler = earHandler.get().getStacks();
                    for (int i = 0; i < handler.getSlots(); i++) {
                        ItemStack stack = handler.getStackInSlot(i);
                        if (!stack.isEmpty() && stack.is(headsetTag)) {
                            foundHeadset.set(true);
                        }
                    }
                }
            });
        } catch (Exception e) {
            System.err.println("Curios check error: " + e.getMessage());
        }
        
        boolean result = foundHeadset.get();
        foundHeadset.set(false);
        return result;
    }
    
    private static final java.util.concurrent.atomic.AtomicBoolean foundHeadset = new java.util.concurrent.atomic.AtomicBoolean(false);
    
    private enum MessageType {
        NORMAL,
        WHISPER,
        EMOTE,
        RADIO,
        LOOC,
        OOC
    }
}
