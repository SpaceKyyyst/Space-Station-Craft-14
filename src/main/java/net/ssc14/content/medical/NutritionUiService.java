package net.ssc14.content.medical;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
}
