
package net.mcreator.ssc;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

public class ArmorResistanceHelper {
    
    public static final String KEY_BLUNT = "blunt";
    public static final String KEY_SLASH = "slash";
    public static final String KEY_PIERCING = "piercing";
    public static final String KEY_HEAT = "heat";
    public static final String KEY_EXPLOSIVE = "explosive";

    private static final Map<String, Double> RESISTANCE_REGISTRY = new HashMap<>();

    public static void registerResistance(String itemId, String damageType, double value) {
        String key = itemId + ":" + damageType;
        RESISTANCE_REGISTRY.put(key, Math.min(Math.max(value, 0.0), 1.0));
    }

    // 🔧 ИЗМЕНЕНО: Последовательное применение защиты (как в вики SS14)
    public static float applyArmorResistance(Player player, String damageType, float incomingDamage) {
        float currentDamage = incomingDamage;

        Optional<ICuriosItemHandler> curiosOpt = CuriosApi.getCuriosInventory(player);
        if (curiosOpt.isEmpty()) return incomingDamage;

        ICuriosItemHandler curios = curiosOpt.get();

        for (String identifier : curios.getCurios().keySet()) {
            var stacksHandlerOpt = curios.getStacksHandler(identifier);
            if (stacksHandlerOpt.isEmpty()) continue;

            IDynamicStackHandler handler = stacksHandlerOpt.get().getStacks();
            if (handler == null) continue;

            for (int slot = 0; slot < handler.getSlots(); slot++) {
                ItemStack stack = handler.getStackInSlot(slot);
                if (stack.isEmpty()) continue;

                ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
                String key = itemId.toString() + ":" + damageType;

                if (RESISTANCE_REGISTRY.containsKey(key)) {
                    double resistance = RESISTANCE_REGISTRY.get(key);
                    // 🔧 Формула SS14: damage = damage * (1 - resistance)
                    currentDamage *= (1.0f - (float)resistance);
                    System.out.println("[SSC14-ARMOR] " + itemId + " | " + damageType + " | res=" + resistance + " | new_dmg=" + String.format("%.2f", currentDamage));
                }
            }
        }

        return Math.max(currentDamage, 0.0f);
    }

    public static void setResistance(ItemStack stack, String damageType, double value) {
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        registerResistance(itemId.toString(), damageType, value);
    }
}
