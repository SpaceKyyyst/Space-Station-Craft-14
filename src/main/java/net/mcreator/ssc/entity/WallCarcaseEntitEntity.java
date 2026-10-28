package net.mcreator.ssc.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/** @deprecated Use {@link net.ssc14.content.entity.WallCarcaseEntitEntity}. */
@Deprecated(forRemoval = false)
public class WallCarcaseEntitEntity extends net.ssc14.content.entity.WallCarcaseEntitEntity {
    public WallCarcaseEntitEntity(EntityType<? extends net.ssc14.content.entity.WallCarcaseEntitEntity> type, Level level) {
        super(type, level);
    }
}
