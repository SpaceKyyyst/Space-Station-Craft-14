package net.mcreator.ssc.entity;

import javax.annotation.Nullable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.ssc14.content.entity.Ssc14EntityTypes;

/** @deprecated Use {@link net.ssc14.content.entity.Bullet35Entity}. */
@Deprecated(forRemoval = false)
public class Bullet35Entity extends net.ssc14.content.entity.Bullet35Entity {
    public static final ItemStack PROJECTILE_ITEM = new ItemStack(net.ssc14.content.item.Ssc14Items.BULLET_35ITEM.get());

    public Bullet35Entity(EntityType<? extends net.ssc14.content.entity.Bullet35Entity> type, Level level) {
        super(type, level);
    }

    public Bullet35Entity(
            EntityType<? extends net.ssc14.content.entity.Bullet35Entity> type,
            double x,
            double y,
            double z,
            Level level,
            @Nullable ItemStack firedFromWeapon) {
        super(type, x, y, z, level, firedFromWeapon);
    }

    public Bullet35Entity(
            EntityType<? extends net.ssc14.content.entity.Bullet35Entity> type,
            LivingEntity owner,
            Level level,
            @Nullable ItemStack firedFromWeapon) {
        super(type, owner, level, firedFromWeapon);
    }

    public static Bullet35Entity shoot(Level level, LivingEntity shooter, RandomSource random) {
        return shoot(level, shooter, random, 10.0f, 16.0, 1);
    }

    public static Bullet35Entity shoot(
            Level level, LivingEntity shooter, RandomSource random, float pullingPower) {
        return shoot(level, shooter, random, pullingPower * 10.0f, 16.0, 1);
    }

    public static Bullet35Entity shoot(
            Level level,
            LivingEntity shooter,
            RandomSource random,
            float power,
            double damage,
            int knockback) {
        Bullet35Entity projectile = new Bullet35Entity(
                Ssc14EntityTypes.BULLET_35.get(), shooter, level, null);
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

    public static Bullet35Entity shoot(LivingEntity shooter, LivingEntity target) {
        Bullet35Entity projectile = new Bullet35Entity(
                Ssc14EntityTypes.BULLET_35.get(), shooter, shooter.level(), null);
        double deltaX = target.getX() - shooter.getX();
        double deltaY = target.getY() + target.getEyeHeight() - 1.1;
        double deltaZ = target.getZ() - shooter.getZ();
        projectile.shoot(
                deltaX,
                deltaY - projectile.getY() + Math.hypot(deltaX, deltaZ) * 0.2f,
                deltaZ,
                20.0f,
                12.0f);
        projectile.setSilent(true);
        projectile.setBaseDamage(16.0);
        projectile.setKnockback(1);
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
