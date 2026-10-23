package net.mcreator.ssc.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/** @deprecated Use {@link net.ssc14.content.entity.IDConsoleENTITYEntity}. */
@Deprecated(forRemoval = false)
public class IDConsoleENTITYEntity extends net.ssc14.content.entity.IDConsoleENTITYEntity {
    public IDConsoleENTITYEntity(EntityType<? extends net.ssc14.content.entity.IDConsoleENTITYEntity> type, Level level) {
        super(type, level);
    }
}
