package net.mcreator.ssc.init;

import net.ssc14.content.firearm.BulletCollisionParticleService;
import net.ssc14.content.firearm.BulletFlightParticleService;
import net.ssc14.content.entity.C4CrutchLifetimeHandler;
import net.ssc14.content.block.furniture.ChairInteractionService;
import net.mcreator.ssc.procedures.SpannerEntUseProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.entity.Ssc14EntityBehavior;

/** Bridges target-owned entities to procedures that remain in the legacy namespace. */
public final class Ssc14LegacyEntityBehavior implements Ssc14EntityBehavior.Hooks {
    public static final Ssc14LegacyEntityBehavior INSTANCE = new Ssc14LegacyEntityBehavior();

    private Ssc14LegacyEntityBehavior() {
    }

    @Override
    public void bulletFlight(LevelAccessor level, double x, double y, double z) {
        BulletFlightParticleService.execute(level, x, y, z);
    }

    @Override
    public void bulletBlockCollision(LevelAccessor level, Entity projectile) {
        BulletCollisionParticleService.execute(level, projectile);
    }

    @Override
    public void c4Tick(LevelAccessor level, Entity entity) {
        C4CrutchLifetimeHandler.execute(level, entity);
    }

    @Override
    public void chairTick(LevelAccessor level, Entity entity) {
        ChairInteractionService.tickChairEntity(level, entity);
    }

    @Override
    public void spannerInteraction(
            LevelAccessor level, double x, double y, double z, Entity entity, Entity sourceEntity) {
        SpannerEntUseProcedure.execute(level, x, y, z, entity, sourceEntity);
    }
}
