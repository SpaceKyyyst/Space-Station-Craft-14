
package net.mcreator.ssc.procedures;

import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.ssc14.integration.neoforge.combat.ArmorResistanceService;

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
            
            ArmorResistanceService.registerResistance(itemId, "blunt", 0.3);      // 30% от ударного
            ArmorResistanceService.registerResistance(itemId, "slash", 0.3);      // 30% от режущего
            ArmorResistanceService.registerResistance(itemId, "piercing", 0.3);   // 30% от колющего
            ArmorResistanceService.registerResistance(itemId, "heat", 0.2);       // 20% от температурного
            ArmorResistanceService.registerResistance(itemId, "explosive", 0.1);  // 10% от взрывного
        }
        
        // ───────────────────────────────────────────────────────────
        //  Шлем безопасности 
        // ───────────────────────────────────────────────────────────
        {
            String itemId = "ssc_14:helmet_security"; // 🔹 ID в нижнем регистре!
            
            ArmorResistanceService.registerResistance(itemId, "blunt", 0.1);      // 10% от ударного
            ArmorResistanceService.registerResistance(itemId, "slash", 0.1);      // 10% от режущего
            ArmorResistanceService.registerResistance(itemId, "piercing", 0.1);   // 10% от колющего
            ArmorResistanceService.registerResistance(itemId, "heat", 0.1);       // 10% от температурного
            
            // 🔹 Если нужно добавить защиту от других типов — просто скопируй строку:
            // ArmorResistanceService.registerResistance(itemId, "shock", 0.2);   // 20% от шока
            // ArmorResistanceService.registerResistance(itemId, "cold", 0.15);   // 15% от холода
        }
        
        // ───────────────────────────────────────────────────────────
        //  Скафандр утилизатора 
        // ───────────────────────────────────────────────────────────
        {
            String itemId = "ssc_14:hardsuit_salvage"; // 🔹 ID в нижнем регистре!
            
            ArmorResistanceService.registerResistance(itemId, "blunt", 0.3);			// 30% от ударного
            ArmorResistanceService.registerResistance(itemId, "slash", 0.4);			// 40% от режущего
            ArmorResistanceService.registerResistance(itemId, "piercing", 0.3);		// 30% от колющего
            ArmorResistanceService.registerResistance(itemId, "heat", 0.3);			// 30% от температурного
            ArmorResistanceService.registerResistance(itemId, "caustic", 0.3);		// 30% от кислотного
            ArmorResistanceService.registerResistance(itemId, "radiation", 0.7);		// 70% от радиации
        }
        
        // ───────────────────────────────────────────────────────────
        //  Шлем скафандра утилизатора 
        // ───────────────────────────────────────────────────────────
        {
            String itemId = "ssc_14:hardsuit_salvage_helmet"; // 🔹 ID в нижнем регистре!
            
            ArmorResistanceService.registerResistance(itemId, "blunt", 0.1);			// 10% от ударного
            ArmorResistanceService.registerResistance(itemId, "slash", 0.1);			// 10% от режущего
            ArmorResistanceService.registerResistance(itemId, "piercing", 0.05);		// 5% от колющего
            ArmorResistanceService.registerResistance(itemId, "heat", 0.1);			// 10% от температурного
            ArmorResistanceService.registerResistance(itemId, "radiation", 0.75);	// 75% от радиации
        }

        
        
        // ───────────────────────────────────────────────────────────
        // 🧥 Пример: как добавить ещё один предмет (например, перчатки)
        // ───────────────────────────────────────────────────────────
        
            // 🔹 Формат: registerResistance("ID_предмета", "тип_урона", множитель_0.0-1.0);
            // 🔹 0.1 = 10% защиты, 0.5 = 50%, 1.0 = 100% (полное поглощение)

        /*
        {
            String itemId = "ssc_14:gloves_security";
            
            ArmorResistanceService.registerResistance(itemId, "blunt", 0.3);			// 30% от ударного
            ArmorResistanceService.registerResistance(itemId, "slash", 0.4);			// 40% от режущего
            ArmorResistanceService.registerResistance(itemId, "piercing", 0.3);		// 30% от колющего
            ArmorResistanceService.registerResistance(itemId, "heat", 0.3);			// 30% от температурного
            ArmorResistanceService.registerResistance(itemId, "caustic", 0.3);		// 30% от кислотного
            ArmorResistanceService.registerResistance(itemId, "radiation", 0.7);		// 70% от радиации
        }
        */
        
        System.out.println("[SSC14] 🛡️ Глобальная регистрация защиты брони завершена");
    }
}
