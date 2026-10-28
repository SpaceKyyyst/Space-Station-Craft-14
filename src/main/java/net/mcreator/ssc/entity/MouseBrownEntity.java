package net.mcreator.ssc.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/** @deprecated Use {@link net.ssc14.content.entity.MouseBrownEntity}. */
@Deprecated(forRemoval = false)
public class MouseBrownEntity extends net.ssc14.content.entity.MouseBrownEntity {
    public MouseBrownEntity(EntityType<? extends net.ssc14.content.entity.MouseBrownEntity> type, Level level) {
        super(type, level);
    }
}
