package net.mcreator.ssc.init;

import net.mcreator.ssc.procedures.Bullet35_BlockCollision_Procedure;
import net.ssc14.content.firearm.BulletFlightParticleService;
import net.mcreator.ssc.procedures.C4_Crutch_Ent_TICProcedure;
import net.mcreator.ssc.procedures.ChairEntity_TICProcedure;
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
        Bullet35_BlockCollision_Procedure.execute(level, projectile);
    }

    @Override
    public void c4Tick(LevelAccessor level, Entity entity) {
        C4_Crutch_Ent_TICProcedure.execute(level, entity);
    }

    @Override
    public void chairTick(LevelAccessor level, Entity entity) {
        ChairEntity_TICProcedure.execute(level, entity);
    }

    @Override
    public void spannerInteraction(
            LevelAccessor level, double x, double y, double z, Entity entity, Entity sourceEntity) {
        SpannerEntUseProcedure.execute(level, x, y, z, entity, sourceEntity);
    }
}
