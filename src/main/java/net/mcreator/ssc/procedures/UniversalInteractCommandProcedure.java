
package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.server.level.ServerPlayer;

public class UniversalInteractCommandProcedure {
    
    // ✅ Принимаем индекс слота вместо предмета
    public static void execute(Player player, int slotIndex) {
        if (!(player instanceof ServerPlayer sp)) return;
        
        AbstractContainerMenu menu = sp.containerMenu;
        
        // 🔒 Защита от выхода за границы массива слотов
        if (slotIndex < 0 || slotIndex >= menu.slots.size()) return;
        
        // Берём именно тот слот, на который навелись на клиенте
        Slot targetSlot = menu.slots.get(slotIndex);
        
        // ✅ Передаём слот в твой хендлер. 
        // Если там не магнитные ботинки (или другой зарегистрированный предмет), 
        // tryInteract просто вернёт false и ничего не произойдёт.
        ItemInteractionHandlerProcedure.tryInteract(player, targetSlot);
    }
}
