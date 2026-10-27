package net.mcreator.ssc;

import net.mcreator.ssc.procedures.ArmGlassShardDAMAGEProcedure;
import net.mcreator.ssc.procedures.GlassShardDAMAGEProcedure;
import net.minecraft.world.entity.LivingEntity;
import net.ssc14.content.item.combat.GlassShardInteractionService;
import net.ssc14.content.item.combat.GlassShardKind;

/** Adapter retained until shard damage moves into the canonical combat runtime. */
final class LegacyGlassShardInteractionAdapter
        implements GlassShardInteractionService.Adapter {
    static final LegacyGlassShardInteractionAdapter INSTANCE =
            new LegacyGlassShardInteractionAdapter();

    private LegacyGlassShardInteractionAdapter() { }

    @Override
    public void hurt(GlassShardKind kind, LivingEntity target) {
        switch (kind) {
            case STANDARD -> GlassShardDAMAGEProcedure.execute(target.level(), target);
            case ARMORED -> ArmGlassShardDAMAGEProcedure.execute(target.level(), target);
        }
    }
}
