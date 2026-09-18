
package net.mcreator.ssc;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import top.theillusivec4.curios.api.CuriosApi;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.network.Ssc14ModVariables;
import java.util.List;
import java.util.function.Supplier;

@EventBusSubscriber(modid = "ssc_14")
public class LocalChatEventHandler {
    private static final double NORMAL_RADIUS = 10.0;
    private static final double WHISPER_RADIUS = 3.0;

    private static class RadioChannel {
        final String prefix;
        final String channelName;
        final Supplier<Item> keyItem;
        final int color;

        RadioChannel(String prefix, String channelName, Supplier<Item> keyItem, int rgbColor) {
            this.prefix = prefix;
            this.channelName = channelName;
            this.keyItem = keyItem;
            this.color = rgbColor;
        }

        boolean hasConnection(Level level) {
            var vars = Ssc14ModVariables.MapVariables.get(level);
            return switch (channelName) {
                case "Общий" -> vars.Base_Radio_Frequency;
                case "Командование" -> vars.Command_Radio_Frequency;
                case "Инженерный" -> vars.Engeneer_Radio_Frequency;
                case "Медицинский" -> vars.Medical_Radio_Frequency;
                case "Научный" -> vars.RND_Radio_Frequency;
                case "Безопасность" -> vars.Sec_Radio_Frequency;
                case "Сервис" -> vars.Serv_Radio_Frequency;
                case "Снабжение" -> vars.Cargo_Radio_Frequency;
                default -> false;
            };
        }
    }

    private static final List<RadioChannel> RADIO_CHANNELS = List.of(
        new RadioChannel(":к", "Командование", () -> Ssc14ModItems.ENCRYPTION_KEY_COMMAND.get(), 0xFCD703),
        new RadioChannel(":и", "Инженерный", () -> Ssc14ModItems.ENCRYPTION_KEY_ENGENEER.get(), 0xFF733C),
        new RadioChannel(":м", "Медицинский", () -> Ssc14ModItems.ENCRYPTION_KEY_MEDICAL.get(), 0x57B8F0),
        new RadioChannel(":н", "Научный", () -> Ssc14ModItems.ENCRYPTION_KEY_RND.get(), 0xCD7CCD),
        new RadioChannel(":о", "Безопасность", () -> Ssc14ModItems.ENCRYPTION_KEY_SECURITY.get(), 0xFF4242),
        new RadioChannel(":с", "Сервис", () -> Ssc14ModItems.ENCRYPTION_KEY_SERVICE.get(), 0x539C00),
        new RadioChannel(":п", "Снабжение", () -> Ssc14ModItems.ENCRYPTION_KEY_CARGO.get(), 0xB48B57),
        new RadioChannel(";", "Общий", () -> Ssc14ModItems.ENCRYPTION_KEY_PASSANGER.get(), 0x2CDB2C)
    );

    @SubscribeEvent
    public static void onPlayerChat(ServerChatEvent event) {
        Player sender = event.getPlayer();
        String originalText = event.getMessage().getString();
        MessageType type = MessageType.NORMAL;
        String messageText = originalText;
        RadioChannel selectedChannel = null;

        for (RadioChannel channel : RADIO_CHANNELS) {
            if (originalText.startsWith(channel.prefix)) {
                type = MessageType.RADIO;
                selectedChannel = channel;
                messageText = originalText.substring(channel.prefix.length()).trim();
                break;
            }
        }

        if (type != MessageType.RADIO) {
            if (originalText.startsWith(",")) {
                type = MessageType.WHISPER;
                messageText = originalText.substring(1).trim();
            } else if (originalText.startsWith("%")) {
                type = MessageType.EMOTE;
                messageText = originalText.substring(1).trim();
            } else if (originalText.startsWith("_")) {
                type = MessageType.LOOC;
                messageText = originalText.substring(1).trim();
            } else if (originalText.startsWith("[")) {
                type = MessageType.OOC;
                messageText = originalText.substring(1).trim();
            }
        }

        String playerName = sender.getDisplayName().getString();
        if (playerName == null || playerName.isEmpty()) {
            playerName = "Неизвестный";
        }
        event.setCanceled(true);
        if (type == MessageType.RADIO && selectedChannel != null) {
            boolean hasConnection = selectedChannel.hasConnection(sender.level());
            boolean hasValidHeadset = hasConnection && hasHeadsetWithKey(sender, selectedChannel.keyItem.get());
            Component radioMessage = Component.literal("[" + selectedChannel.channelName + "] " + playerName + " говорит, \"" + messageText + "\"")
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(selectedChannel.color)));

            if (hasValidHeadset) {
                for (Player target : sender.level().players()) {
                    if (sender.level().dimension() == target.level().dimension() && hasHeadsetWithKey(target, selectedChannel.keyItem.get())) {
                        // ИСПРАВЛЕНО ПОД 1.21.4: sendSystemMessage вместо displayClientMessage
                        target.sendSystemMessage(radioMessage);
                    }
                }
            }
            Component whisperMessage = Component.literal(playerName + " шепчет, \"" + messageText + "\"")
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xAAAAAA)).withItalic(true));
            for (Player target : sender.level().players()) {
                if (sender.distanceTo(target) <= WHISPER_RADIUS) {
                    // ИСПРАВЛЕНО ПОД 1.21.4: sendSystemMessage
                    target.sendSystemMessage(whisperMessage);
                }
            }
            return;
        }

        if (type == MessageType.OOC) {
            Component oocMessage = Component.literal("OOC: " + playerName + ": " + messageText)
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x5AA0D2)));
            var server = ServerLifecycleHooks.getCurrentServer();
            if (server != null) {
                for (Player target : server.getPlayerList().getPlayers()) {
                    // ИСПРАВЛЕНО ПОД 1.21.4: sendSystemMessage
                    target.sendSystemMessage(oocMessage);
                }
            }
            return;
        }

        if (type == MessageType.LOOC) {
            Component loocMessage = Component.literal("LOOC: " + playerName + ": " + messageText)
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x46D7D2)));
            for (Player target : sender.level().players()) {
                if (sender.distanceTo(target) <= NORMAL_RADIUS) {
                    // ИСПРАВЛЕНО ПОД 1.21.4: sendSystemMessage
                    target.sendSystemMessage(loocMessage);
                }
            }
            return;
        }

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
                // ИСПРАВЛЕНО ПОД 1.21.4: sendSystemMessage
                target.sendSystemMessage(formattedMessage);
            }
        }
    }

    private static boolean hasHeadsetWithKey(Player player, Item requiredKey) {
        var headsetTag = ItemTags.create(Identifier.fromNamespaceAndPath("ssc14", "headsets"));
        foundHeadset.set(false);

        var inventory = player.getInventory();
        int containerSize = inventory.getContainerSize();
        for (int i = 0; containerSize > i; i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && stack.is(headsetTag) && hasSpecificKeyInside(stack, requiredKey)) {
                return true;
            }
        }

        try {
            CuriosApi.getCuriosInventory(player).ifPresent(invHandler -> {
                var earHandler = invHandler.getStacksHandler("ear");
                if (earHandler.isPresent()) {
                    IItemHandler handler = earHandler.get().getStacks();
                    int slotsCount = handler.getSlots();
                    for (int i = 0; slotsCount > i; i++) {
                        ItemStack stack = handler.getStackInSlot(i);
                        if (!stack.isEmpty() && stack.is(headsetTag) && hasSpecificKeyInside(stack, requiredKey)) {
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

    private static boolean hasSpecificKeyInside(ItemStack headset, Item requiredKey) {
        // ИСПРАВЛЕНО ПОД 1.21.4: Используем гарантированно существующий метод ItemUtil.getStack из вашей утилиты!
        net.neoforged.neoforge.transfer.ResourceHandler<net.neoforged.neoforge.transfer.item.ItemResource> itemHandler = 
            headset.getCapability(net.neoforged.neoforge.capabilities.Capabilities.Item.ITEM, net.neoforged.neoforge.transfer.access.ItemAccess.forStack(headset));
        
        if (itemHandler == null) {
            return false;
        }

        // Безопасный перебор фиксированного диапазона слотов гарнитуры с помощью проверенной утилиты
        for (int i = 0; 9 > i; i++) {
            ItemStack itemInSlot = net.neoforged.neoforge.transfer.item.ItemUtil.getStack(itemHandler, i);
            if (!itemInSlot.isEmpty() && itemInSlot.is(requiredKey)) {
                return true;
            }
        }
        return false;
    }

	private static final java.util.concurrent.atomic.AtomicBoolean foundHeadset = new java.util.concurrent.atomic.AtomicBoolean(false);
    private enum MessageType {
        NORMAL, WHISPER, EMOTE, RADIO, LOOC, OOC
    }
}
