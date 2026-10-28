package net.ssc14.content.medical;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.ssc14.content.attribute.Ssc14Attributes;

public final class DigestiveProcessesService {
    private DigestiveProcessesService() {
    }

    public static boolean clear(Entity entity) {
        if (!(entity instanceof LivingEntity living)) {
            return false;
        }
        boolean changed = false;
        if (living.getAttributes().hasAttribute(Ssc14Attributes.DIGESTIVE_PROCESSES)) {
            living.getAttribute(Ssc14Attributes.DIGESTIVE_PROCESSES).setBaseValue(0);
            changed = true;
        }
        if (living.getAttributes().hasAttribute(Ssc14Attributes.FLUID_ABSORPTION)) {
            living.getAttribute(Ssc14Attributes.FLUID_ABSORPTION).setBaseValue(0);
            changed = true;
        }
        return changed;
    }
}
