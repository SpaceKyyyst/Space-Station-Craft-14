
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
public class MetabolismTICProcedure {
    
    private static final double BASE_METABOLISM_RATE = 0.00084;
    private static final double NUTRIENTS_MIN = 0;
    private static final double NUTRIENTS_MAX = 200;

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Pre event) {
        execute(event, event.getEntity());
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) return;
        if (!isMetabolismActive(livingEntity)) return;
        
        Double current = getNutrientsValue(livingEntity);
        if (current == null) return;
        
        double newValue = current - BASE_METABOLISM_RATE;
        setNutrientsValue(livingEntity, newValue);
    }

    // === Локальные хелперы ===
    
    private static boolean isMetabolismActive(LivingEntity entity) {
        if (entity == null) return false;
        if (!entity.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS)) return false;
        if (entity instanceof Player player) {
            GameType mode = player.gameMode();
            if (mode == GameType.CREATIVE || mode == GameType.SPECTATOR) return false;
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
            double clamped = Math.max(NUTRIENTS_MIN, Math.min(NUTRIENTS_MAX, newValue));
            attr.setBaseValue(Math.round(clamped * 100000d) / 100000d);
        }
    }
}
