package net.ssc14.content.player.action;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.attribute.Ssc14Attributes;

/** Owns synchronized player action flags previously toggled by generated procedures. */
public final class PlayerActionStateService {
    private PlayerActionStateService() { }

    public static void setPulling(Entity entity, boolean active) {
        setFlag(entity, Ssc14Attributes.ENT_PULL, active);
    }

    public static void setWorldCheckOpen(Entity entity, boolean active) {
        setFlag(entity, Ssc14Attributes.OPEN_WORLD_CHECK_MENU, active);
    }

    private static void setFlag(Entity entity, DeferredHolder<Attribute, Attribute> attribute, boolean active) {
        if (!(entity instanceof LivingEntity living) || !living.getAttributes().hasAttribute(attribute)) return;
        living.getAttribute(attribute).setBaseValue(active ? 1.0 : 0.0);
    }
}
