package net.ssc14.content.item.combat;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;

/** Applies the canonical slash damage dealt when a shard breaks in a target. */
public final class GlassShardInteractionService {
    private static final ResourceKey<DamageType> SLASH_DAMAGE = ResourceKey.create(
            Registries.DAMAGE_TYPE, Identifier.parse("ssc_14:ssc_14dmgslash"));
    private static final float STANDARD_DAMAGE = 2.5F;
    private static final float ARMORED_DAMAGE = 4.5F;

    private GlassShardInteractionService() { }

    public static void hurt(GlassShardKind kind, LivingEntity target) {
        if (target.level() instanceof ServerLevel level) {
            float damage = kind == GlassShardKind.ARMORED ? ARMORED_DAMAGE : STANDARD_DAMAGE;
            target.hurtServer(level, new DamageSource(level.holderOrThrow(SLASH_DAMAGE)), damage);
        }
    }
}
