
package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;

import java.util.HashMap;
import java.util.function.BiConsumer;

// Импорты Curios API для NeoForge 1.21
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

public class ItemInteractionHandlerProcedure {
    private static final HashMap<net.minecraft.world.item.Item, BiConsumer<Player, Slot>> HANDLERS = new HashMap<>();

    static {
        // --- Магнитные ботинки ---
        register(
            net.mcreator.ssc.init.Ssc14ModItems.MAGNETIC_BOOTS_ITEM.get(),
            net.mcreator.ssc.init.Ssc14ModItems.MAGNETIC_BOOTS_ACTIVE_ITEM.get(),
            (player, slot) -> {}
        );
        register(
            net.mcreator.ssc.init.Ssc14ModItems.MAGNETIC_BOOTS_ACTIVE_ITEM.get(),
            net.mcreator.ssc.init.Ssc14ModItems.MAGNETIC_BOOTS_ITEM.get(),
            (player, slot) -> {}
        );

        // --- Скафандр утилизатора (Переключение шлема) ---
        register(
            net.mcreator.ssc.init.Ssc14ModItems.HARDSUIT_SALVAGE.get(),
            net.mcreator.ssc.init.Ssc14ModItems.HARDSUIT_SALVAGE.get(),
            (player, slot) -> handleHardsuitToggle(player, slot, true)
        );
    }

    public static void register(net.minecraft.world.item.Item from, net.minecraft.world.item.Item to, BiConsumer<Player, Slot> onToggle) {
        HANDLERS.put(from, (player, slot) -> {
            if (from != to) {
                ItemStack newStack = new ItemStack(to, slot.getItem().getCount());
                newStack.applyComponents(slot.getItem().getComponents());
                slot.set(newStack);

                if (player instanceof ServerPlayer sp) {
                    sp.connection.send(new ClientboundContainerSetSlotPacket(
                        sp.containerMenu.containerId,
                        sp.containerMenu.incrementStateId(),
                        slot.index,
                        newStack
                    ));
                }
            }
            onToggle.accept(player, slot);
        });
    }


    public static boolean tryInteract(Player player, Slot slot) {
        ItemStack stack = slot.getItem();
        if (stack.isEmpty()) return false;
        var handler = HANDLERS.get(stack.getItem());
        if (handler != null) {
            handler.accept(player, slot);
            return true;
        }
        return false;
    }

    /**
     * Логика переключения встроенного шлема через Curios API с проверкой outerwear
     */
    private static void handleHardsuitToggle(Player player, Slot slot, boolean openHelmet) {
        if (!(player instanceof ServerPlayer sp)) return;
        var curiosInvOpt = CuriosApi.getCuriosInventory(sp);
        if (curiosInvOpt.isEmpty()) return;
        var curiosInventory = curiosInvOpt.get();
        var outerwearHandlerOpt = curiosInventory.getStacksHandler("outerwear");
        if (outerwearHandlerOpt.isEmpty()) return;
        var outerwearStacks = outerwearHandlerOpt.get().getStacks();
        boolean isEquippedInOuterwear = false;
        for (int i = 0; i < outerwearStacks.getSlots(); i++) {
            ItemStack stackInOuterwear = outerwearStacks.getStackInSlot(i);
            if (!stackInOuterwear.isEmpty() && stackInOuterwear == slot.getItem()) {
                isEquippedInOuterwear = true;
                break;
            }
        }
        if (!isEquippedInOuterwear) return;
        curiosInventory.getStacksHandler("headdress").ifPresent(stacksHandler -> {
            var resultStacks = stacksHandler.getStacks();
            ItemStack headItem = resultStacks.getStackInSlot(0);
            net.minecraft.world.item.Item helmetItem = net.mcreator.ssc.init.Ssc14ModItems.HARDSUIT_SALVAGE_HELMET.get();
            if (headItem.isEmpty()) {
                ItemStack newHelmet = new ItemStack(helmetItem);
                resultStacks.setStackInSlot(0, newHelmet);
                sp.containerMenu.broadcastChanges();
            } else if (headItem.is(helmetItem)) {
                resultStacks.setStackInSlot(0, ItemStack.EMPTY);
                sp.containerMenu.broadcastChanges();
            }
        });
    }
}
