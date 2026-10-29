package net.mcreator.ssc.init;

import net.ssc14.content.firearm.BulletCollisionParticleService;
import net.ssc14.content.firearm.BulletFlightParticleService;
import net.ssc14.content.entity.C4CrutchLifetimeHandler;
import net.ssc14.content.block.furniture.ChairInteractionService;
import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.entity.IDConsoleENTITYEntity;
import net.ssc14.content.entity.PlassteelWallCarcaseEntitEntity;
import net.ssc14.content.entity.WallCarcaseEntitEntity;
import net.ssc14.content.item.tool.SpannerInteractionService;
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
        if (!(sourceEntity instanceof LivingEntity living)) return;
        ItemStack tool = living.getMainHandItem();
        if (entity instanceof WallCarcaseEntitEntity) {
            if (tool.is(Ssc14ModItems.SPANNER.get())) {
                SpannerInteractionService.repair(level, entity, sourceEntity, tool,
                        Ssc14ModItems.SPANNER.get(), Ssc14Blocks.WALL_CARCASE.get(),
                        Ssc14ModAttributes.PROGRESS_BAR_ATRB);
            } else if (tool.is(Ssc14ModItems.SCREWDRIVER.get())) {
                SpannerInteractionService.dismantle(level, entity, sourceEntity, tool,
                        Ssc14ModItems.SCREWDRIVER.get(), Ssc14ModItems.STEEL.get(),
                        Ssc14ModAttributes.PROGRESS_BAR_ATRB);
            }
        } else if (entity instanceof PlassteelWallCarcaseEntitEntity
                && tool.is(Ssc14ModItems.SPANNER.get())) {
            SpannerInteractionService.repair(level, entity, sourceEntity, tool,
                    Ssc14ModItems.SPANNER.get(), Ssc14Blocks.PLASTEEL_WALL_CARCASE.get(),
                    Ssc14ModAttributes.PROGRESS_BAR_ATRB);
        } else if (entity instanceof IDConsoleENTITYEntity
                && tool.is(Ssc14ModItems.SPANNER.get())) {
            SpannerInteractionService.repair(level, entity, sourceEntity, tool,
                    Ssc14ModItems.SPANNER.get(), Ssc14ModBlocks.CONSOLE_OF_ID.get(),
                    Ssc14ModAttributes.PROGRESS_BAR_ATRB);
        } else {
            SpannerInteractionService.pull(entity, sourceEntity, Ssc14ModAttributes.ENT_PULL);
        }
    }
}
