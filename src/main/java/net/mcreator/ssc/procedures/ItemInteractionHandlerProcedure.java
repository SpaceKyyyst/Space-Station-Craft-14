
package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
// ✅ Добавляем импорт для пакета принудительного обновления слота
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class ItemInteractionHandlerProcedure {
    
    private static final HashMap<net.minecraft.world.item.Item, BiConsumer<Player, Slot>> HANDLERS = new HashMap<>();
    
    static {
        // ✅ Передаём пустую лямбду, так как мусорный лог мы убрали
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
    }
    
    public static void register(net.minecraft.world.item.Item from, net.minecraft.world.item.Item to, BiConsumer<Player, Slot> onToggle) {
        HANDLERS.put(from, (player, slot) -> {
            ItemStack newStack = new ItemStack(to, slot.getItem().getCount());
            newStack.applyComponents(slot.getItem().getComponents());
            
            // Заменяем предмет в слоте на сервере
            slot.set(newStack);
            
            // ✅ ПРИНУДИТЕЛЬНО отправляем пакет клиенту на обновление этого слота
            if (player instanceof ServerPlayer sp) {
                sp.connection.send(new ClientboundContainerSetSlotPacket(
                    sp.containerMenu.containerId,       // ID текущего контейнера
                    sp.containerMenu.incrementStateId(), // Уникальный ID состояния
                    slot.index,                          // Индекс слота, который мы меняем
                    newStack                             // Новый предмет
                ));
            }
            
            // Выполняем кастомную логику (сейчас она пустая, но место для будущих фишек)
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
}

