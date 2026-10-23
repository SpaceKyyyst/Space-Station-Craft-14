package net.mcreator.ssc.entity;

import javax.annotation.Nullable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.ssc14.content.entity.Ssc14EntityTypes;

/** @deprecated Use {@link net.ssc14.content.entity.FoamDartProjectileEntity}. */
@Deprecated(forRemoval = false)
public class FoamDartProjectileEntity extends net.ssc14.content.entity.FoamDartProjectileEntity {
    public static final ItemStack PROJECTILE_ITEM = new ItemStack(Ssc14ModItems.FOAM_DART.get());

    public FoamDartProjectileEntity(EntityType<? extends net.ssc14.content.entity.FoamDartProjectileEntity> type, Level level) {
        super(type, level);
    }

    public FoamDartProjectileEntity(
            EntityType<? extends net.ssc14.content.entity.FoamDartProjectileEntity> type,
            double x,
            double y,
            double z,
            Level level,
            @Nullable ItemStack firedFromWeapon) {
        super(type, x, y, z, level, firedFromWeapon);
    }

    public FoamDartProjectileEntity(
            EntityType<? extends net.ssc14.content.entity.FoamDartProjectileEntity> type,
            LivingEntity owner,
            Level level,
            @Nullable ItemStack firedFromWeapon) {
        super(type, owner, level, firedFromWeapon);
    }

    public static FoamDartProjectileEntity shoot(
            Level level, LivingEntity shooter, RandomSource random) {
        return shoot(level, shooter, random, 2.0f, 0.0, 0);
    }

    public static FoamDartProjectileEntity shoot(
            Level level, LivingEntity shooter, RandomSource random, float pullingPower) {
        return shoot(level, shooter, random, pullingPower * 2.0f, 0.0, 0);
    }

    public static FoamDartProjectileEntity shoot(
            Level level,
            LivingEntity shooter,
            RandomSource random,
            float power,
            double damage,
            int knockback) {
        FoamDartProjectileEntity projectile = new FoamDartProjectileEntity(
                Ssc14EntityTypes.FOAM_DART_PROJECTILE.get(), shooter, level, null);
        projectile.shoot(
                shooter.getViewVector(1).x,
                shooter.getViewVector(1).y,
                shooter.getViewVector(1).z,
                power * 2.0f,
                0.0f);
        projectile.setSilent(true);
        projectile.setCritArrow(false);
        projectile.setBaseDamage(damage);
        projectile.setKnockback(knockback);
        level.addFreshEntity(projectile);
        level.playSound(
                null,
                shooter.getX(),
                shooter.getY(),
                shooter.getZ(),
                BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.arrow.shoot")),
                SoundSource.PLAYERS,
                1.0f,
                1.0f / (random.nextFloat() * 0.5f + 1.0f) + power / 2.0f);
        return projectile;
    }

    public static FoamDartProjectileEntity shoot(LivingEntity shooter, LivingEntity target) {
        FoamDartProjectileEntity projectile = new FoamDartProjectileEntity(
                Ssc14EntityTypes.FOAM_DART_PROJECTILE.get(), shooter, shooter.level(), null);
        double deltaX = target.getX() - shooter.getX();
        double deltaY = target.getY() + target.getEyeHeight() - 1.1;
        double deltaZ = target.getZ() - shooter.getZ();
        projectile.shoot(
                deltaX,
                deltaY - projectile.getY() + Math.hypot(deltaX, deltaZ) * 0.2f,
                deltaZ,
                4.0f,
                12.0f);
        projectile.setSilent(true);
        projectile.setBaseDamage(0.0);
        projectile.setKnockback(0);
        projectile.setCritArrow(false);
        shooter.level().addFreshEntity(projectile);
        shooter.level().playSound(
                null,
                shooter.getX(),
                shooter.getY(),
                shooter.getZ(),
                BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.arrow.shoot")),
                SoundSource.PLAYERS,
                1.0f,
                1.0f / (RandomSource.create().nextFloat() * 0.5f + 1.0f));
        return projectile;
    }
}
