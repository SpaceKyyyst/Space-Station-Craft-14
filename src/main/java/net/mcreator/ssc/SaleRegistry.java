
package net.mcreator.ssc;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import java.util.HashMap;
import java.util.Map;

import net.mcreator.ssc.init.Ssc14ModItems;

public class SaleRegistry {
    private static final Map<Item, Integer> SALE_PRICES = new HashMap<>();

    static {
        // РЕЕСТР ЦЕН ПРОДАЖИ (Цена указывается строго за 1 штуку предмета!)
        SALE_PRICES.put(Ssc14ModItems.SILVER.get(), 40);     	// 1 серебро = 40 кредитов
        SALE_PRICES.put(Ssc14ModItems.GLASS.get(), 2);        	// 1 стекло = 2 кредита
        SALE_PRICES.put(Ssc14ModItems.PLASTIC.get(), 3);     	// 1 пластик = 3 кредита
        SALE_PRICES.put(Ssc14ModItems.STEEL.get(), 2);        	// 1 сталь = 2 кредита
        SALE_PRICES.put(Ssc14ModItems.SSC_14_GOLD.get(), 80); 	// 1 золото = 80 кредитов
        SALE_PRICES.put(Ssc14ModItems.CROWBAR.get(), 22); 		// монтировка = 22 кр
        SALE_PRICES.put(Ssc14ModItems.WELDING.get(), 40); 
        SALE_PRICES.put(Ssc14ModItems.ACTIVE_WELDER.get(), 40); 
        SALE_PRICES.put(Ssc14ModItems.NIPPERS.get(), 30);
        SALE_PRICES.put(Ssc14ModItems.FIRE_AXE.get(), 500);
        SALE_PRICES.put(Ssc14ModItems.SCREWDRIVER.get(), 30);
        SALE_PRICES.put(Ssc14ModItems.SPANNER.get(), 22);
        SALE_PRICES.put(Ssc14ModItems.GAS_ANALYSER.get(), 80);
        //SALE_PRICES.put(Ssc14ModItems.ИМЯ_ПРЕДМЕТА.get(), цена_за_штуку);
        
        // Сюда ты сможешь дописать вообще любую вещь из мода:
        // SALE_PRICES.put(Ssc14ModItems.ИМЯ_ПРЕДМЕТА.get(), цена_за_штуку);
    }

    // Метод получения цены за 1 единицу предмета
    public static int getPrice(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        Item item = stack.getItem();
        
        // Если предмет есть в реестре — берем его цену, если нет — скупаем как "мусор" по 1 кредиту
        return SALE_PRICES.getOrDefault(item, 1);
    }
}
