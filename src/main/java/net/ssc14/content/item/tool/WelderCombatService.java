package net.ssc14.content.item.tool;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

/** Applies the state-specific secondary damage used by legacy welder attacks. */
public final class WelderCombatService {
    private WelderCombatService() { }

    public static void apply(LevelAccessor level, Entity target, WelderState state) {
        if (target == null || !(target.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        var damageType = ResourceKey.create(Registries.DAMAGE_TYPE, state.damageTypeId());
        target.hurtServer(serverLevel,
                new DamageSource(level.holderOrThrow(damageType)), state.attackDamage());
    }
}
