
package net.ssc14.integration.neoforge.combat;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.ssc14.core.combat.ResistanceRegistry;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Optional;

public final class ArmorResistanceService {
    private static final org.slf4j.Logger LOGGER = com.mojang.logging.LogUtils.getLogger();

    private ArmorResistanceService() { }
    
    public static final String KEY_BLUNT = "blunt";
    public static final String KEY_SLASH = "slash";
    public static final String KEY_PIERCING = "piercing";
    public static final String KEY_HEAT = "heat";
    public static final String KEY_EXPLOSIVE = "explosive";

    private static final ResistanceRegistry RESISTANCE_REGISTRY = new ResistanceRegistry();

    public static void registerResistance(String itemId, String damageType, double value) {
        RESISTANCE_REGISTRY.register(itemId, damageType, value);
    }

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

                Identifier itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
                if (itemId == null) continue;

                float previousDamage = currentDamage;
                currentDamage = RESISTANCE_REGISTRY.apply(itemId.toString(), damageType, currentDamage);
                LOGGER.debug("Armor {} damage type {}: {} -> {}",
                        itemId, damageType, previousDamage, currentDamage);
            }
        }

        return Math.max(currentDamage, 0.0f);
    }

    public static void setResistance(ItemStack stack, String damageType, double value) {
        Identifier itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (itemId != null) {
            registerResistance(itemId.toString(), damageType, value);
        }
    }
}
