package net.mcreator.ssc.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/** @deprecated Use {@link net.ssc14.content.entity.MouseGrayEntity}. */
@Deprecated(forRemoval = false)
public class MouseGrayEntity extends net.ssc14.content.entity.MouseGrayEntity {
    public MouseGrayEntity(EntityType<? extends net.ssc14.content.entity.MouseGrayEntity> type, Level level) {
        super(type, level);
    }
}
