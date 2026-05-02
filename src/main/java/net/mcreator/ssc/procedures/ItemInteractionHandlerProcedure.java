
package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class ItemInteractionHandlerProcedure {
    
    private static final HashMap<net.minecraft.world.item.Item, BiConsumer<Player, Slot>> HANDLERS = new HashMap<>();
    
    static {
        // Регистрация магнитных ботинок: НЕактивные → активные
        register(
            net.mcreator.ssc.init.Ssc14ModItems.MAGNETIC_BOOTS_ITEM.get(),
            net.mcreator.ssc.init.Ssc14ModItems.MAGNETIC_BOOTS_ACTIVE_ITEM.get(),
            (player, slot) -> sendMsg(player, "🧲 Магнитные ботинки §lВКЛЮЧЕНЫ§r!", "§a")
        );
        // Регистрация: активные → НЕактивные
        register(
            net.mcreator.ssc.init.Ssc14ModItems.MAGNETIC_BOOTS_ACTIVE_ITEM.get(),
            net.mcreator.ssc.init.Ssc14ModItems.MAGNETIC_BOOTS_ITEM.get(),
            (player, slot) -> sendMsg(player, "🧲 Магнитные ботинки §lВЫКЛЮЧЕНЫ§r!", "§e")
        );
    }
    
    public static void register(net.minecraft.world.item.Item from, net.minecraft.world.item.Item to, BiConsumer<Player, Slot> onToggle) {
        HANDLERS.put(from, (player, slot) -> {
            ItemStack newStack = new ItemStack(to, slot.getItem().getCount());
            newStack.applyComponents(slot.getItem().getComponents());
            
            // Заменяем предмет в слоте
            slot.set(newStack);
            
            // ✅ Синхронизируем изменения с клиентом (обновляет текстуру!)
            if (player.containerMenu != null) {
                player.containerMenu.broadcastChanges();
            }
            
            // Выполняем кастомную логику (сообщение в чат и т.д.)
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
    
    private static void sendMsg(Player player, String text, String color) {
        if (player instanceof ServerPlayer sp) {
            sp.sendSystemMessage(Component.literal(color + text));
        }
    }
}
