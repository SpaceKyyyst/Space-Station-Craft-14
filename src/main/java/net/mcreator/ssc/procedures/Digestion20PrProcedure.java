
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.Ssc14Mod;

public class Digestion20PrProcedure {
    
    private static final double NUTRIENTS_PER_CYCLE = 5.0;
    private static final int BASE_DIGESTION_TICKS = 400;
    
    private static final double MOD_OVEREATING = 0.833;
    private static final double MOD_SATIATED = 1.0;
    private static final double MOD_HUNGER = 1.25;
    private static final double MOD_STARVATION = 1.667;

    public static void execute(LevelAccessor world, Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) return;
        if (!isMetabolismActive(livingEntity)) return;
        
        Double currentNutrients = getNutrientsValue(livingEntity);
        if (currentNutrients == null) return;
        
        double modifier = getDigestionModifier(currentNutrients);
        int delayTicks = (int) Math.round(BASE_DIGESTION_TICKS * modifier);
        
        // ⚠️ ОТЛОЖЕННОЕ ВЫПОЛНЕНИЕ — ПРАВИЛЬНАЯ ЛОГИКА!
        Ssc14Mod.queueServerWork(delayTicks, () -> {
            // ✅ Пропускаем, если сущность удалена ИЛИ мы на клиенте
            if (livingEntity == null || livingEntity.isRemoved() || livingEntity.level().isClientSide) {
                return;
            }
            processDigestion(livingEntity);
        });
    }
    
    private static void processDigestion(LivingEntity entity) {
        // 1. Добавляем питательные вещества
        Double nutrientsBefore = getNutrientsValue(entity);
        if (nutrientsBefore != null) {
            setNutrientsValue(entity, nutrientsBefore + NUTRIENTS_PER_CYCLE);
        }
        
        // 2. Уменьшаем счётчик пищеварения
        AttributeInstance digestiveAttr = entity.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES);
        if (digestiveAttr != null) {
            double newVal = Math.max(0, digestiveAttr.getValue() - 1);
            digestiveAttr.setBaseValue(Math.round(newVal));
        }
    }

    // === Локальные хелперы ===
    
    private static boolean isMetabolismActive(LivingEntity entity) {
        if (entity == null) return false;
        if (!entity.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS)) return false;
        if (entity instanceof Player player) {
            var mode = player.gameMode();
            if (mode == net.minecraft.world.level.GameType.CREATIVE || 
                mode == net.minecraft.world.level.GameType.SPECTATOR) return false;
        }
        return true;
    }

    private static Double getNutrientsValue(LivingEntity entity) {
        AttributeInstance attr = entity.getAttribute(Ssc14ModAttributes.NUTRIENTS);
        return attr != null ? attr.getValue() : null;
    }

    private static void setNutrientsValue(LivingEntity entity, double newValue) {
        AttributeInstance attr = entity.getAttribute(Ssc14ModAttributes.NUTRIENTS);
        if (attr != null) {
            double clamped = Math.max(0, Math.min(200, newValue));
            attr.setBaseValue(Math.round(clamped * 100000d) / 100000d);
        }
    }
    
    private static double getDigestionModifier(double nutrients) {
        if (nutrients >= 151) return MOD_OVEREATING;
        if (nutrients >= 101) return MOD_SATIATED;
        if (nutrients >= 51) return MOD_HUNGER;
        return MOD_STARVATION;
    }
}
