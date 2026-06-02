
package net.mcreator.ssc.procedures;

import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.mcreator.ssc.ArmorResistanceHelper;

@EventBusSubscriber(modid = "ssc_14")
public class ModArmorSetupProcedure {
    
    @SubscribeEvent
    public static void onModSetup(FMLCommonSetupEvent event) {
        
        // ═══════════════════════════════════════════════════════════
        // 🔧 ШАБЛОН ДОБАВЛЕНИЯ НОВОГО ПРЕДМЕТА БРОНИ:
        // 1. Возьми этот блок и скопируй его
        // 2. Замени "ITEM_ID" на ID твоего предмета (в нижнем регистре!)
        // 3. Укажи нужные типы урона и проценты защиты
        // ═══════════════════════════════════════════════════════════
        
        // ───────────────────────────────────────────────────────────
        //  Бронежилет 
        // ───────────────────────────────────────────────────────────
        {
            String itemId = "ssc_14:armor_security"; // 🔹 ID в нижнем регистре!
            
            ArmorResistanceHelper.registerResistance(itemId, "blunt", 0.3);      // 30% от ударного
            ArmorResistanceHelper.registerResistance(itemId, "slash", 0.3);      // 30% от режущего
            ArmorResistanceHelper.registerResistance(itemId, "piercing", 0.3);   // 30% от колющего
            ArmorResistanceHelper.registerResistance(itemId, "heat", 0.2);       // 20% от температурного
            ArmorResistanceHelper.registerResistance(itemId, "explosive", 0.1);  // 10% от взрывного
        }
        
        // ───────────────────────────────────────────────────────────
        //  Шлем безопасности 
        // ───────────────────────────────────────────────────────────
        {
            String itemId = "ssc_14:helmet_security"; // 🔹 ID в нижнем регистре!
            
            // 🔹 Формат: registerResistance("ID_предмета", "тип_урона", множитель_0.0-1.0);
            // 🔹 0.1 = 10% защиты, 0.5 = 50%, 1.0 = 100% (полное поглощение)
            
            ArmorResistanceHelper.registerResistance(itemId, "blunt", 0.1);      // 10% от ударного
            ArmorResistanceHelper.registerResistance(itemId, "slash", 0.1);      // 10% от режущего
            ArmorResistanceHelper.registerResistance(itemId, "piercing", 0.1);   // 10% от колющего
            ArmorResistanceHelper.registerResistance(itemId, "heat", 0.1);       // 10% от температурного
            
            // 🔹 Если нужно добавить защиту от других типов — просто скопируй строку:
            // ArmorResistanceHelper.registerResistance(itemId, "shock", 0.2);   // 20% от шока
            // ArmorResistanceHelper.registerResistance(itemId, "cold", 0.15);   // 15% от холода
        }
        
        // ───────────────────────────────────────────────────────────
        // 🧥 Пример: как добавить ещё один предмет (например, перчатки)
        // ───────────────────────────────────────────────────────────
        /*
        {
            String itemId = "ssc_14:gloves_security";
            
            ArmorResistanceHelper.registerResistance(itemId, "blunt", 0.05);     // 5%
            ArmorResistanceHelper.registerResistance(itemId, "heat", 0.1);       // 10%
        }
        */
        
        System.out.println("[SSC14] 🛡️ Глобальная регистрация защиты брони завершена");
    }
}
