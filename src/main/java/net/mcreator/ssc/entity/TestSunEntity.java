package net.mcreator.ssc.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/** @deprecated Use {@link net.ssc14.content.entity.TestSunEntity}. */
@Deprecated(forRemoval = true)
public class TestSunEntity extends net.ssc14.content.entity.TestSunEntity {
    public TestSunEntity(EntityType<TestSunEntity> type, Level level) {
        super((EntityType<net.ssc14.content.entity.TestSunEntity>) (EntityType<?>) type, level);
    }
}
