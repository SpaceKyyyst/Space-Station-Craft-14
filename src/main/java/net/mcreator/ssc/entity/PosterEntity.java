package net.mcreator.ssc.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/** @deprecated Use {@link net.ssc14.content.entity.PosterEntity}. */
@Deprecated(forRemoval = false)
public class PosterEntity extends net.ssc14.content.entity.PosterEntity {
    public PosterEntity(EntityType<? extends net.ssc14.content.entity.PosterEntity> type, Level level) {
        super(type, level);
    }
}
