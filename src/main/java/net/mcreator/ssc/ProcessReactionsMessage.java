
package net.mcreator.ssc;

import net.mcreator.ssc.item.ReagentContainerItem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.*;

@EventBusSubscriber
public record ProcessReactionsMessage(int slotIndex, ItemStack carriedStack, String targetMarker) implements CustomPacketPayload {

    public static final Type<ProcessReactionsMessage> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(Ssc14Mod.MODID, "process_reactions")
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ProcessReactionsMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, message -> message.slotIndex,
            ItemStack.OPTIONAL_STREAM_CODEC, message -> message.carriedStack,
            ByteBufCodecs.stringUtf8(256), message -> message.targetMarker,
            ProcessReactionsMessage::new
    );

    @Override
    public Type<ProcessReactionsMessage> type() {
        return TYPE;
    }

    public static void handleData(final ProcessReactionsMessage message, final IPayloadContext context) {
        if (context.flow() == PacketFlow.SERVERBOUND) {
            context.enqueueWork(() -> {
                if (context.player() instanceof ServerPlayer serverPlayer) {
                    execute(serverPlayer, message.slotIndex());
                }
            }).exceptionally(e -> {
                System.err.println("[SSC-SERVER] Ошибка сетевого пакета: " + e.getMessage());
                return null;
            });
        }
    }

    private static void execute(ServerPlayer player, int slotIndex) {
        if (player.containerMenu == null) return;

        // Защитная проверка индекса слота
        if (slotIndex < 0 || slotIndex >= player.containerMenu.slots.size()) return;

        Slot targetSlot = player.containerMenu.slots.get(slotIndex);
        ItemStack targetStack = targetSlot.getItem(); // Куда льем (в инвентаре)
        ItemStack mainHandStack = player.getMainHandItem(); // Из чего льем (в хотбаре/руке)

        // Проверяем классы предметов
        if (!(targetStack.getItem() instanceof ReagentContainerItem) || !(mainHandStack.getItem() instanceof ReagentContainerItem)) {
            return;
        }

        Map<String, Integer> sourceReagents = ModReagents.getReagents(mainHandStack);
        if (sourceReagents.isEmpty()) return;

        int totalSourceVolume = sourceReagents.values().stream().mapToInt(Integer::intValue).sum();
        if (totalSourceVolume <= 0) return;

        ReagentContainerItem targetItem = (ReagentContainerItem) targetStack.getItem();
        int targetCurrentVolume = ModReagents.getReagents(targetStack).values().stream().mapToInt(Integer::intValue).sum();
        int freeSpace = targetItem.getMaxCapacity() - targetCurrentVolume;

        if (freeSpace <= 0) return;

        // Переливаем порциями по 5 единиц
        int transferAmount = Math.min(5, Math.min(totalSourceVolume, freeSpace));
        Map<String, Integer> transferMap = calculateProportionalTransfer(sourceReagents, totalSourceVolume, transferAmount);

        // Проводим изменения в жидкостях
        for (Map.Entry<String, Integer> entry : transferMap.entrySet()) {
            String reagentId = entry.getKey();
            int amount = entry.getValue();
            if (amount > 0) {
                ModReagents.removeReagent(mainHandStack, reagentId, amount);
                ModReagents.addReagent(targetStack, reagentId, amount);
            }
        }

        // Фиксируем измененный предмет в слоте инвентаря
        targetSlot.set(targetStack);
        targetSlot.setChanged();

        // ФИКС: Явно обновляем предмет в руке (хотбаре) игрока на сервере
        player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, mainHandStack);

        // ЗАПУСКАЕМ ХИМИЧЕСКУЮ РЕАКЦИЮ НА СЕРВЕРЕ намертво
        ModReagents.processReactions(targetSlot.getItem(), player);

        // Синхронизируем абсолютно все изменения слотов и хотбара с клиентом
        player.containerMenu.broadcastChanges();
    }

    private static Map<String, Integer> calculateProportionalTransfer(Map<String, Integer> sourceReagents, int totalSourceVolume, int transferAmount) {
        Map<String, Double> exactAmounts = new LinkedHashMap<>();
        Map<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : sourceReagents.entrySet()) {
            String reagentId = entry.getKey();
            int sourceAmount = entry.getValue();
            double proportion = (double) sourceAmount / totalSourceVolume;
            exactAmounts.put(reagentId, proportion * transferAmount);
        }
        int remaining = transferAmount;
        Map<String, Double> fractionalParts = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : exactAmounts.entrySet()) {
            String reagentId = entry.getKey();
            double exact = entry.getValue();
            int rounded = (int) Math.floor(exact);
            result.put(reagentId, rounded);
            remaining -= rounded;
            fractionalParts.put(reagentId, exact - rounded);
        }
        List<Map.Entry<String, Double>> sortedFractions = new ArrayList<>(fractionalParts.entrySet());
        sortedFractions.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
        for (int i = 0; i < remaining && i < sortedFractions.size(); i++) {
            String reagentId = sortedFractions.get(i).getKey();
            result.put(reagentId, result.get(reagentId) + 1);
        }
        return result;
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        Ssc14Mod.addNetworkMessage(TYPE, STREAM_CODEC, ProcessReactionsMessage::handleData);
    }
}
