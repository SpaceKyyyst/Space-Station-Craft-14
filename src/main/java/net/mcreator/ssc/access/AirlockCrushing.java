package net.mcreator.ssc.access;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.phys.AABB;

/** Corvax applies 15 blunt damage once and reopens after two seconds. */
public final class AirlockCrushing {
    public static final int RELEASE_TICKS = 40;
    private static final float DAMAGE = 15;
    private static final double HALF_THICKNESS = 3.0 / 16;
    private AirlockCrushing() { }
    public static boolean apply(ServerLevel level, BlockPos pos) {
        var facing = level.getBlockState(pos).getValue(HorizontalDirectionalBlock.FACING);
        var box = new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1);
        box = facing.getAxis() == net.minecraft.core.Direction.Axis.X
                ? new AABB(pos.getX() + .5 - HALF_THICKNESS, box.minY, box.minZ, pos.getX() + .5 + HALF_THICKNESS, box.maxY, box.maxZ)
                : new AABB(box.minX, box.minY, pos.getZ() + .5 - HALF_THICKNESS, box.maxX, box.maxY, pos.getZ() + .5 + HALF_THICKNESS);
        var victims = level.getEntitiesOfClass(LivingEntity.class, box, entity -> entity.isAlive() && !entity.isSpectator());
        var damage = new DamageSource(level.holderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.parse("ssc_14:ssc_14dmgblunt"))));
        for (var victim : victims) {
            victim.hurtServer(level, damage, DAMAGE);
            victim.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, RELEASE_TICKS, 9, false, true));
        }
        return !victims.isEmpty();
    }
}
