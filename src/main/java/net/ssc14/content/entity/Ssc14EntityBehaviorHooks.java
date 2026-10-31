package net.ssc14.content.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.attribute.Ssc14Attributes;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.block.furniture.ChairInteractionService;
import net.ssc14.content.firearm.BulletCollisionParticleService;
import net.ssc14.content.firearm.BulletFlightParticleService;
import net.ssc14.content.item.Ssc14Items;
import net.ssc14.content.item.tool.SpannerInteractionService;

/** Canonical hook bridge for entity behavior formerly wired by generated code. */
public final class Ssc14EntityBehaviorHooks implements Ssc14EntityBehavior.Hooks {
    public static final Ssc14EntityBehaviorHooks INSTANCE = new Ssc14EntityBehaviorHooks();

    private Ssc14EntityBehaviorHooks() {
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
        if (!(sourceEntity instanceof LivingEntity living)) return;
        ItemStack tool = living.getMainHandItem();
        if (entity instanceof WallCarcaseEntitEntity) {
            if (tool.is(Ssc14Items.SPANNER.get())) {
                SpannerInteractionService.repair(level, entity, sourceEntity, tool,
                        Ssc14Items.SPANNER.get(), Ssc14Blocks.WALL_CARCASE.get(),
                        Ssc14Attributes.PROGRESS_BAR_ATRB);
            } else if (tool.is(Ssc14Items.SCREWDRIVER.get())) {
                SpannerInteractionService.dismantle(level, entity, sourceEntity, tool,
                        Ssc14Items.SCREWDRIVER.get(), Ssc14Items.STEEL.get(),
                        Ssc14Attributes.PROGRESS_BAR_ATRB);
            }
        } else if (entity instanceof PlassteelWallCarcaseEntitEntity
                && tool.is(Ssc14Items.SPANNER.get())) {
            SpannerInteractionService.repair(level, entity, sourceEntity, tool,
                    Ssc14Items.SPANNER.get(), Ssc14Blocks.PLASTEEL_WALL_CARCASE.get(),
                    Ssc14Attributes.PROGRESS_BAR_ATRB);
        } else if (entity instanceof IDConsoleENTITYEntity
                && tool.is(Ssc14Items.SPANNER.get())) {
            SpannerInteractionService.repair(level, entity, sourceEntity, tool,
                    Ssc14Items.SPANNER.get(), Ssc14Blocks.CONSOLE_OF_ID.get(),
                    Ssc14Attributes.PROGRESS_BAR_ATRB);
        } else {
            SpannerInteractionService.pull(entity, sourceEntity, Ssc14Attributes.ENT_PULL);
        }
    }
}
