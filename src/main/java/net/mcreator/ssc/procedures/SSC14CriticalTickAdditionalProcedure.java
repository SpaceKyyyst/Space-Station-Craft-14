
package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class SSC14CriticalTickAdditionalProcedure {
    
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        execute(event, event.getEntity());
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity == null) return;
        
        // 🔧 Исправлено: используем entity, а не несуществующий player
        if (!(entity instanceof Player player)) return;
        
        var nbt = player.getPersistentData();
        double totalDamage = nbt.getDouble("sscCustomHealth").orElse(0.0);
        
        // 🔧 Проверка: крит = 100-200 (как в основной логике)
        if (totalDamage >= 100.0) {
            // 🚫 В крите: блокируем строительство
            player.getAbilities().mayBuild = false;
            player.onUpdateAbilities();
        } else {
            // ✅ Вне крита: разрешаем строительство
            player.getAbilities().mayBuild = true;
            player.onUpdateAbilities();
        }
    }
}
