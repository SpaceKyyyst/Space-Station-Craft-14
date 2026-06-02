
// net/mcreator/ssc/procedures/LiquidMetabolismTICProcedure.java
package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

import net.mcreator.ssc.init.Ssc14ModAttributes;

import javax.annotation.Nullable;

@EventBusSubscriber
public class LiquidMetabolismTICProcedure {
    
    // Расход жидкости в 2 раза быстрее, чем питательных веществ
    private static final double BASE_LIQUID_DRAIN_RATE = 0.00168; // 0.00084 × 2
    private static final double LIQUID_MIN = 0;
    private static final double LIQUID_MAX = 200;

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Pre event) {
        execute(event, event.getEntity());
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) return;
        if (!isHydrationActive(livingEntity)) return;
        
        Double current = getLiquidValue(livingEntity);
        if (current == null) return;
        
        double newValue = current - BASE_LIQUID_DRAIN_RATE;
        setLiquidValue(livingEntity, newValue);
    }

    // === Локальные хелперы ===
    
    private static boolean isHydrationActive(LivingEntity entity) {
        if (entity == null) return false;
        if (!entity.getAttributes().hasAttribute(Ssc14ModAttributes.LIQUID)) return false;
        if (entity instanceof Player player) {
            GameType mode = player.gameMode();
            if (mode == GameType.CREATIVE || mode == GameType.SPECTATOR) return false;
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
            double clamped = Math.max(LIQUID_MIN, Math.min(LIQUID_MAX, newValue));
            attr.setBaseValue(Math.round(clamped * 100000d) / 100000d);
        }
    }
}
