
package net.mcreator.ssc;

import net.minecraft.world.item.ItemStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModBlocks;

public class CargoRegistry {
    public static class CargoItem {
        private final ItemStack stack;
        private final int count;
        private final int price;

        public CargoItem(ItemStack stack, int count, int price) {
            this.stack = stack;
            this.count = count;
            this.price = price;
        }

        public ItemStack getStack() { 
            ItemStack copy = this.stack.copy();
            // Устанавливаем размер одного стака по умолчанию
            copy.setCount(copy.getMaxStackSize());
            return copy; 
        }
        
        public int getCount() { return this.count; }
        public int getPrice() { return this.price; }
    }

    private static final Map<Integer, List<CargoItem>> CATEGORIES = new HashMap<>();

    static {
        for (int i = 1; i <= 15; i++) {
            CATEGORIES.put(i, new ArrayList<>());
        }

        // ВКЛАДКА 10: МАТЕРИАЛЫ
        // Третий аргумент — это количество СТАКОВ (пачек), которое спавнится в мире
        register(10, new ItemStack(Ssc14ModItems.SILVER.get()), 1, 2580);     // 1 пачка серебра
        register(10, new ItemStack(Ssc14ModItems.GLASS.get()), 3, 150);       // 3 пачки стекла
        register(10, new ItemStack(Ssc14ModItems.PLASTIC.get()), 3, 200);     // 3 пачки пластика
        register(10, new ItemStack(Ssc14ModItems.STEEL.get()), 3, 300);       // 3 пачки стали
        register(10, new ItemStack(Ssc14ModBlocks.FUEL_TANK.get()), 1, 1200);  // 1 канистра топлива
        register(10, new ItemStack(Ssc14ModItems.SSC_14_GOLD.get()), 1, 5000);  // 1 пачка золота

        // ВКЛАДКА 11: МЕДИЦИНА
        register(11, new ItemStack(Ssc14ModItems.BRUTEPACK.get()), 1, 400);     // 1 пачка брутпаков
    }

    private static void register(int tab, ItemStack stack, int count, int price) {
        if (CATEGORIES.containsKey(tab)) {
            CATEGORIES.get(tab).add(new CargoItem(stack, count, price));
        }
    }

    public static List<CargoItem> getItemsForTab(int tab) {
        return CATEGORIES.getOrDefault(tab, new ArrayList<>());
    }

    public static CargoItem getItemAt(int tab, int index) {
        List<CargoItem> list = getItemsForTab(tab);
        if (index >= 0 && index < list.size()) {
            return list.get(index);
        }
        return null;
    }
}
