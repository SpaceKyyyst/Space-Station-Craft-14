package net.ssc14.content.item.tool;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

/** Applies the legacy SSC14 damage packets associated with mechanical tools. */
public final class MechanicalToolDamageService {
    private MechanicalToolDamageService() { }

    public static void apply(
            LevelAccessor level, Entity target, MechanicalToolProfile profile) {
        if (target == null || !(target.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        for (MechanicalToolProfile.DamageEffect effect : profile.damageEffects()) {
            var damageType = ResourceKey.create(Registries.DAMAGE_TYPE, effect.damageType());
            target.hurtServer(
                    serverLevel,
                    new DamageSource(level.holderOrThrow(damageType)),
                    effect.amount());
        }
    }
}
