package net.mcreator.ssc.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/** @deprecated Use {@link net.ssc14.content.entity.MouseWhiteEntity}. */
@Deprecated(forRemoval = false)
public class MouseWhiteEntity extends net.ssc14.content.entity.MouseWhiteEntity {
    public MouseWhiteEntity(EntityType<? extends net.ssc14.content.entity.MouseWhiteEntity> type, Level level) {
        super(type, level);
    }
}
