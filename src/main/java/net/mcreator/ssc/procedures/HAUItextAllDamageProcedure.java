/*
package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;

public class HAUItextAllDamageProcedure {
    
    // 🔧 КЛЮЧ С ПРОБЕЛОМ — как в процедуре получения урона!
    // Проверь в OnPlayerTakeDamageSSC14Procedure.java, что там тоже "sscCustomHealth " (с пробелом)
    private static final String KEY_TOTAL_DAMAGE = "sscCustomHealth ";
    
    public static String execute(Player player) {
        // 🔧 Если игрока нет (меню, загрузка) — возвращаем заглушку
        if (player == null) return "Общие повреждения: N/D";
        
        CompoundTag nbt = player.getPersistentData();
        
        // 🔧 Проверяем наличие ключа перед чтением
        if (!nbt.contains(KEY_TOTAL_DAMAGE)) {
            // 🔧 Отладка: если ключа нет — пишем в консоль
            System.out.println("[SSC14-SCANNER] Key not found: '" + KEY_TOTAL_DAMAGE + "'");
            return "Общие повреждения: 0.0";
        }
        
        // 🔧 Читаем урон (в 1.21.8 getDouble возвращает double, не Optional!)
        double totalDamage = nbt.getDouble(KEY_TOTAL_DAMAGE);
        
        // 🔧 Отладка: выводим значение в консоль
        System.out.println("[SSC14-SCANNER] Found damage: " + totalDamage);
        
        // 🔧 Форматируем с одним знаком после запятой
        return "Общие повреждения: " + String.format("%.1f", totalDamage);  
    }
}
*/