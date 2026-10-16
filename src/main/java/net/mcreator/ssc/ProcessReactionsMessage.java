
package net.mcreator.ssc;

import net.mcreator.ssc.item.ReagentContainerItem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Map;
import net.mcreator.ssc.network.MenuPacketGuard;
import net.mcreator.ssc.chemistry.ReagentTransferPlan;

@EventBusSubscriber
public record ProcessReactionsMessage(int slotIndex, ItemStack carriedStack, String targetMarker) implements CustomPacketPayload {

    // ИСПРАВЛЕНО: Регистрация ID типа сетевого пакета переведена на fromNamespaceAndPath
    public static final Type<ProcessReactionsMessage> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath("ssc_14", "process_reactions")
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
                Ssc14Mod.LOGGER.error("Failed to transfer reagents", e);
                return null;
            });
        }
    }

    private static void execute(ServerPlayer player, int slotIndex) {
        if (!MenuPacketGuard.canUseMenu(player)) return;
        if (slotIndex < 0 || slotIndex >= player.containerMenu.slots.size()) return;

        Slot targetSlot = player.containerMenu.slots.get(slotIndex);
        if (!targetSlot.isActive() || !targetSlot.mayPickup(player)) return;
        ItemStack targetStack = targetSlot.getItem(); 
        ItemStack mainHandStack = player.getMainHandItem(); 

        if (targetStack == mainHandStack || !targetSlot.mayPlace(targetStack)
                || !(targetStack.getItem() instanceof ReagentContainerItem targetItem)
                || !(mainHandStack.getItem() instanceof ReagentContainerItem sourceItem)) {
            return;
        }

        Map<String, Integer> sourceReagents = ModReagents.getReagents(mainHandStack);
        if (sourceReagents.isEmpty()) return;

        Map<String, Integer> transferMap = ReagentTransferPlan.calculate(sourceReagents,
                ModReagents.getReagents(targetStack), sourceItem.getMaxCapacity(), targetItem.getMaxCapacity());
        if (transferMap.isEmpty()) return;

        for (Map.Entry<String, Integer> entry : transferMap.entrySet()) {
            String reagentId = entry.getKey();
            int amount = entry.getValue();
            if (amount > 0) {
                ModReagents.removeReagent(mainHandStack, reagentId, amount);
                ModReagents.addReagent(targetStack, reagentId, amount);
            }
        }

        targetSlot.set(targetStack);
        targetSlot.setChanged();

        player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, mainHandStack);
        ModReagents.processReactions(targetSlot.getItem(), player);
        player.containerMenu.broadcastChanges();
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        Ssc14Mod.addServerboundNetworkMessage(TYPE, STREAM_CODEC, ProcessReactionsMessage::handleData);
    }
}
