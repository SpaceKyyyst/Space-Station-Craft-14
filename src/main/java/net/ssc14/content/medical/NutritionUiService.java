package net.ssc14.content.medical;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.ssc14.content.attribute.Ssc14Attributes;

public final class NutritionUiService {
    private NutritionUiService() { }

    public static double blinkingFrame(Entity entity) {
        if (entity == null || entity.level().getGameTime() % 20 < 10) {
            return 0;
        }
        return entity instanceof LivingEntity living
                && living.getAttributes().hasAttribute(Ssc14Attributes.NUTRIENTS) ? 1 : 0;
    }

    public static boolean above(Entity entity, double threshold) {
        if (!(entity instanceof LivingEntity living) || isCreativeOrSpectator(entity)) return false;
        return living.getAttributes().hasAttribute(Ssc14Attributes.NUTRIENTS)
                && living.getAttribute(Ssc14Attributes.NUTRIENTS).getValue() > threshold;
    }

    public static boolean inRange(Entity entity, double minimumInclusive, double maximumExclusive) {
        if (!(entity instanceof LivingEntity living) || isCreativeOrSpectator(entity)) return false;
        if (!living.getAttributes().hasAttribute(Ssc14Attributes.NUTRIENTS)) return false;
        double value = living.getAttribute(Ssc14Attributes.NUTRIENTS).getValue();
        return value >= minimumInclusive && value < maximumExclusive;
    }

    public static boolean below(Entity entity, double threshold) {
        if (!(entity instanceof LivingEntity living) || isCreativeOrSpectator(entity)) return false;
        return living.getAttributes().hasAttribute(Ssc14Attributes.NUTRIENTS)
                && living.getAttribute(Ssc14Attributes.NUTRIENTS).getValue() < threshold;
    }

    private static boolean isCreativeOrSpectator(Entity entity) {
        return entity instanceof Player player
                && (player.gameMode() == GameType.CREATIVE || player.gameMode() == GameType.SPECTATOR);
    }
}
