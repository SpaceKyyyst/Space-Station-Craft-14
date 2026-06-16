
package net.mcreator.ssc;

import net.minecraft.resources.ResourceLocation;
import java.util.HashMap;
import java.util.Map;

public class ReagentContainerConfig {
    // Карта вместимости: ID предмета -> вместимость в миллибакетах (mb)
    public static final Map<ResourceLocation, Integer> CAPACITIES = new HashMap<>();

    static {
        // Добавь сюда ВСЕ предметы-контейнеры из твоего скриншота
        // Формат: CAPACITIES.put(ResourceLocation.parse("ssc_14:имя_предмета"), вместимость);
        
        CAPACITIES.put(ResourceLocation.parse("ssc_14:small_glass"), 50);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:shot_glass"), 25);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:pint_glass"), 100);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:mug"), 100);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:big_mug"), 200);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:beer_glass"), 100);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:beerstein_glass"), 200);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:wine_glass"), 50);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:cocktail_glass"), 50);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:tall_cocktail_glass"), 75);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:hurricane_glass"), 75);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:cognac_glass"), 50);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:rocks_glass"), 50);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:double_shot_glass"), 50);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:half_pint_glass"), 50);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:glass_mug"), 100);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:metal_coffee_cup"), 100);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:jug"), 500);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:rumka"), 25);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:shaker"), 200);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:thin_glass"), 500);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:vacuum_flask"), 500);
        CAPACITIES.put(ResourceLocation.parse("ssc_14:bar_flask"), 100);
        
        // Если каких-то предметов нет в списке, добавь их по аналогии!
    }
}
