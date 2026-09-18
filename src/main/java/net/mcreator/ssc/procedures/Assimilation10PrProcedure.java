
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.Ssc14Mod;

public class Assimilation10PrProcedure {
    
    private static final double LIQUID_PER_CYCLE = 5.0;
    private static final int BASE_ABSORPTION_TICKS = 200;
    
    private static final double MOD_OVERHYDRATED = 0.833;
    private static final double MOD_HYDRATED = 1.0;
    private static final double MOD_THIRSTY = 1.25;
    private static final double MOD_DEHYDRATED = 1.667;

    public static void execute(LevelAccessor world, Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) return;
        if (!isHydrationActive(livingEntity)) return;
        
        Double currentLiquid = getLiquidValue(livingEntity);
        if (currentLiquid == null) return;
        
        double hydrationModifier = getAbsorptionModifier(currentLiquid);
        
        AttributeInstance absorptionAttr = livingEntity.getAttribute(Ssc14ModAttributes.FLUID_ABSORPTION);
        if (absorptionAttr != null) {
            double absorptionBonus = absorptionAttr.getValue() * 0.10;
            hydrationModifier /= (1.0 + absorptionBonus);
        }
        
        int delayTicks = (int) Math.round(BASE_ABSORPTION_TICKS * hydrationModifier);
        
        Ssc14Mod.queueServerWork(delayTicks, () -> {
            // ИСПРАВЛЕНО: Добавлены скобочки к методу .isClientSide() под NeoForge 26.1.2
            if (livingEntity == null || livingEntity.isRemoved() || livingEntity.level().isClientSide()) {
                return;
            }
            processAbsorption(livingEntity);
        });
    }
    
    private static void processAbsorption(LivingEntity entity) {
        Double liquidBefore = getLiquidValue(entity);
        if (liquidBefore != null) {
            setLiquidValue(entity, liquidBefore + LIQUID_PER_CYCLE);
        }
        
        AttributeInstance absorptionAttr = entity.getAttribute(Ssc14ModAttributes.FLUID_ABSORPTION);
        if (absorptionAttr != null) {
            double newVal = Math.max(0, absorptionAttr.getValue() - 1);
            absorptionAttr.setBaseValue(Math.round(newVal));
        }
    }
    
    private static boolean isHydrationActive(LivingEntity entity) {
        if (entity == null) return false;
        if (!entity.getAttributes().hasAttribute(Ssc14ModAttributes.LIQUID)) return false;
        if (entity instanceof Player player) {
            var mode = player.gameMode();
            if (mode == net.minecraft.world.level.GameType.CREATIVE || 
                mode == net.minecraft.world.level.GameType.SPECTATOR) return false;
        }
        return true;
    }

    private static Double getLiquidValue(LivingEntity entity) {
        AttributeInstance attr = entity.getAttribute(Ssc14ModAttributes.LIQUID);
        return attr != null ? attr.getValue() : null;
    }

    private static void setLiquidValue(LivingEntity entity, double newValue) {
        AttributeInstance attr = entity.getAttribute(Ssc14ModAttributes.LIQUID);
        if (attr != null) {
            double clamped = Math.max(0, Math.min(200, newValue));
            attr.setBaseValue(Math.round(clamped * 100000d) / 100000d);
        }
    }
    
    private static double getAbsorptionModifier(double liquid) {
        if (liquid >= 151) return MOD_OVERHYDRATED;
        if (liquid >= 101) return MOD_HYDRATED;
        if (liquid >= 51) return MOD_THIRSTY;
        return MOD_DEHYDRATED;
    }
}
