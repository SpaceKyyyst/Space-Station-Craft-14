package net.ssc14.content.item.reagent.network;

import java.util.Map;
import net.ssc14.content.item.reagent.ReagentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.common.chemistry.ReagentTransferPlan;
import net.ssc14.content.item.reagent.ReagentContainerItem;
import net.ssc14.content.item.reagent.ReagentContainerAccess;
import net.ssc14.integration.neoforge.chemistry.network.ProcessReactionsMessage;
import net.ssc14.integration.neoforge.menu.network.MenuAccessGuard;
import net.ssc14.integration.neoforge.network.Ssc14NetworkPayloads;

/** Transitional handler until the reagent container runtime moves to the target namespace. */
@EventBusSubscriber
public final class ProcessReactionsRegistration {
    private static final Logger LOGGER = LogManager.getLogger(ProcessReactionsRegistration.class);
    private ProcessReactionsRegistration() { }

    public static void handle(ProcessReactionsMessage message, IPayloadContext context) {
        if (context.flow() != PacketFlow.SERVERBOUND) return;
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) execute(player, message.slotIndex());
        }).exceptionally(error -> {
            LOGGER.error("Failed to transfer reagents", error);
            return null;
        });
    }

    private static void execute(ServerPlayer player, int slotIndex) {
        if (!MenuAccessGuard.canUseMenu(player)
                || slotIndex < 0 || slotIndex >= player.containerMenu.slots.size()) return;
        Slot targetSlot = player.containerMenu.slots.get(slotIndex);
        if (!targetSlot.isActive() || !targetSlot.mayPickup(player)) return;
        ItemStack targetStack = targetSlot.getItem();
        ItemStack sourceStack = player.getMainHandItem();
        if (targetStack == sourceStack || !targetSlot.mayPlace(targetStack)
                || !(targetStack.getItem() instanceof ReagentContainerItem targetItem)
                || !(sourceStack.getItem() instanceof ReagentContainerItem sourceItem)) return;

        Map<String, Integer> sourceReagents = ReagentContainerAccess.contents(sourceStack);
        if (sourceReagents.isEmpty()) return;
        Map<String, Integer> transfer = ReagentTransferPlan.calculate(sourceReagents,
                ReagentContainerAccess.contents(targetStack), sourceItem.getMaxCapacity(), targetItem.getMaxCapacity());
        for (var entry : transfer.entrySet()) {
            if (entry.getValue() > 0) {
                ReagentContainerAccess.remove(sourceStack, entry.getKey(), entry.getValue());
                ReagentContainerAccess.add(targetStack, entry.getKey(), entry.getValue());
            }
        }
        if (transfer.isEmpty()) return;
        targetSlot.set(targetStack);
        targetSlot.setChanged();
        player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, sourceStack);
        ReagentService.processReactions(targetSlot.getItem(), player);
        player.containerMenu.broadcastChanges();
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        Ssc14NetworkPayloads.addServerbound(ProcessReactionsMessage.TYPE,
                ProcessReactionsMessage.STREAM_CODEC, ProcessReactionsRegistration::handle);
    }
}
