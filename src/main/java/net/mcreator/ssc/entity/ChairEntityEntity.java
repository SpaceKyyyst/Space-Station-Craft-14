package net.mcreator.ssc.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/** @deprecated Use {@link net.ssc14.content.entity.ChairEntityEntity}. */
@Deprecated(forRemoval = false)
public class ChairEntityEntity extends net.ssc14.content.entity.ChairEntityEntity {
    public ChairEntityEntity(EntityType<? extends net.ssc14.content.entity.ChairEntityEntity> type, Level level) {
        super(type, level);
    }
}
