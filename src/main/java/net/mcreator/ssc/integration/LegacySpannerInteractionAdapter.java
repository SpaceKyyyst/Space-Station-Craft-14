package net.mcreator.ssc.integration;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.entity.IDConsoleENTITYEntity;
import net.ssc14.content.entity.PlassteelWallCarcaseEntitEntity;
import net.ssc14.content.entity.WallCarcaseEntitEntity;
import net.ssc14.content.item.tool.SpannerInteractionService;

/** Transitional boundary for generated spanner entity interaction. */
public final class LegacySpannerInteractionAdapter {
    private LegacySpannerInteractionAdapter() {
    }

    public static void execute(LevelAccessor level, double x, double y, double z,
            Entity target, Entity source) {
        if (!(source instanceof LivingEntity living)) return;
        ItemStack tool = living.getMainHandItem();
        if (target instanceof WallCarcaseEntitEntity) {
            if (tool.is(Ssc14ModItems.SPANNER.get())) {
                SpannerInteractionService.repair(level, target, source, tool,
                        Ssc14ModItems.SPANNER.get(), Ssc14Blocks.WALL_CARCASE.get(),
                        Ssc14ModAttributes.PROGRESS_BAR_ATRB);
            } else if (tool.is(Ssc14ModItems.SCREWDRIVER.get())) {
                SpannerInteractionService.dismantle(level, target, source, tool,
                        Ssc14ModItems.SCREWDRIVER.get(), Ssc14ModItems.STEEL.get(),
                        Ssc14ModAttributes.PROGRESS_BAR_ATRB);
            }
        } else if (target instanceof PlassteelWallCarcaseEntitEntity
                && tool.is(Ssc14ModItems.SPANNER.get())) {
            SpannerInteractionService.repair(level, target, source, tool,
                    Ssc14ModItems.SPANNER.get(), Ssc14Blocks.PLASTEEL_WALL_CARCASE.get(),
                    Ssc14ModAttributes.PROGRESS_BAR_ATRB);
        } else if (target instanceof IDConsoleENTITYEntity
                && tool.is(Ssc14ModItems.SPANNER.get())) {
            SpannerInteractionService.repair(level, target, source, tool,
                    Ssc14ModItems.SPANNER.get(), Ssc14ModBlocks.CONSOLE_OF_ID.get(),
                    Ssc14ModAttributes.PROGRESS_BAR_ATRB);
        }
    }
}
