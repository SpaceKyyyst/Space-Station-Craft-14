
package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class UniversalInteractCommandProcedure {
    
    // ✅ Принимаем Item вместо int slotIndex
    public static void execute(Player player, Item targetItem) {
        
        if (!(player instanceof ServerPlayer sp)) return;
        
        AbstractContainerMenu menu = sp.containerMenu;
        
        // 🔍 Ищем предмет во ВСЕХ слотах контейнера
        for (Slot slot : menu.slots) {
            ItemStack stack = slot.getItem();
            if (stack.isEmpty() || stack.getItem() != targetItem) continue;
            
            // ✅ Нашли! Обрабатываем переключение
            
            boolean swapped = false;
            ItemStack newStack = ItemStack.EMPTY;
            
            var bootsInactive = net.mcreator.ssc.init.Ssc14ModItems.MAGNETIC_BOOTS_ITEM.get();
            var bootsActive = net.mcreator.ssc.init.Ssc14ModItems.MAGNETIC_BOOTS_ACTIVE_ITEM.get();
            
            if (stack.getItem() == bootsInactive) {
                newStack = new ItemStack(bootsActive, stack.getCount());
                newStack.applyComponents(stack.getComponents());
                swapped = true;
            } else if (stack.getItem() == bootsActive) {
                newStack = new ItemStack(bootsInactive, stack.getCount());
                newStack.applyComponents(stack.getComponents());
                swapped = true;
            }
            
            if (swapped) {
			// Заменяем предмет в слоте
            slot.set(newStack);
            
            // ✅ Синхронизируем изменения с клиентом (обновляет текстуру!)
            if (player.containerMenu != null) {
                player.containerMenu.broadcastChanges();
            	}
        	}
    	}
    }
}
