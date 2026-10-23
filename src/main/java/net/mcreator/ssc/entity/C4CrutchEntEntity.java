package net.mcreator.ssc.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/** @deprecated Use {@link net.ssc14.content.entity.C4CrutchEntEntity}. */
@Deprecated(forRemoval = false)
public class C4CrutchEntEntity extends net.ssc14.content.entity.C4CrutchEntEntity {
    public C4CrutchEntEntity(EntityType<? extends net.ssc14.content.entity.C4CrutchEntEntity> type, Level level) {
        super(type, level);
    }
}
