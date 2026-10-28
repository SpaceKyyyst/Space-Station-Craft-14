package net.ssc14.content.medical;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.ssc14.content.attribute.Ssc14Attributes;

/** Applies the server-side liquid metabolism drain. */
@EventBusSubscriber
public final class LiquidMetabolismTickHandler {
    private static final double BASE_LIQUID_DRAIN_RATE = 0.00168;
    private static final double LIQUID_MIN = 0;
    private static final double LIQUID_MAX = 200;

    private LiquidMetabolismTickHandler() { }

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Pre event) {
        drain(event.getEntity());
    }

    private static void drain(net.minecraft.world.entity.Entity entity) {
        if (!(entity instanceof LivingEntity living) || !isActive(living)) return;
        AttributeInstance attribute = living.getAttribute(Ssc14Attributes.LIQUID);
        if (attribute == null) return;
        double clamped = Math.max(LIQUID_MIN,
                Math.min(LIQUID_MAX, attribute.getValue() - BASE_LIQUID_DRAIN_RATE));
        attribute.setBaseValue(Math.round(clamped * 100000d) / 100000d);
    }

    private static boolean isActive(LivingEntity entity) {
        if (!entity.getAttributes().hasAttribute(Ssc14Attributes.LIQUID)) return false;
        if (entity instanceof Player player) {
            GameType mode = player.gameMode();
            return mode != GameType.CREATIVE && mode != GameType.SPECTATOR;
        }
        return true;
    }
}
