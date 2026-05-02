
package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.item.ItemEntity;

@EventBusSubscriber
public class NoItemDespawnProcedure {
    
    @SubscribeEvent
    public static void onItemSpawn(EntityJoinLevelEvent event) {
        // Работаем только на сервере и только с предметами
        if (event.getEntity().level().isClientSide() || !(event.getEntity() instanceof ItemEntity item)) {
            return;
        }
        
        // Устанавливаем время жизни на максимум — предмет никогда не исчезнет
        item.lifespan = Integer.MAX_VALUE;
    }
}
