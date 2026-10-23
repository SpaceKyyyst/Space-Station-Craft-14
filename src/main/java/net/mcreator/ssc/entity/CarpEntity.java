package net.mcreator.ssc.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/** @deprecated Use {@link net.ssc14.content.entity.CarpEntity}. */
@Deprecated(forRemoval = false)
public class CarpEntity extends net.ssc14.content.entity.CarpEntity {
    public CarpEntity(EntityType<? extends net.ssc14.content.entity.CarpEntity> type, Level level) {
        super(type, level);
    }
}
