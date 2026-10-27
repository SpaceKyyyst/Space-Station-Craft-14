package net.ssc14.content.item.weapon;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.entity.FoamDartProjectileEntity;
import net.ssc14.content.entity.Ssc14EntityTypes;
import net.ssc14.content.item.Ssc14Items;

/** Owns Astro-Ace foam-dart storage, reload and firing behavior. */
public final class FoamDartLauncherService {
    public static final int MAX_DARTS = 10;
    public static final int FIRE_COOLDOWN_TICKS = 10;
    private static final String DART_COUNT_KEY = "DartBullets";
    private static final Identifier RELOAD_SOUND = id("foam_rifle_discharge");
    private static final Identifier SHOT_SOUND = id("foam_rifle_shot");

    private FoamDartLauncherService() { }

    public static long dartCount(ItemStack stack) {
        return Math.round(storedDarts(stack));
    }

    public static String description(ItemStack stack) {
        return "Дротики:  " + dartCount(stack) + " / " + MAX_DARTS;
    }

    public static void toggleDart(LevelAccessor level, Entity entity, ItemStack stack) {
        if (!(entity instanceof LivingEntity living)
                || stack.getItem() != living.getMainHandItem().getItem()) {
            return;
        }

        ItemStack mainHand = living.getMainHandItem();
        ItemStack offhand = living.getOffhandItem();
        double darts = storedDarts(mainHand);
        if (offhand.is(Ssc14Items.FOAM_DART.get()) && darts < MAX_DARTS) {
            offhand.shrink(1);
            setDartCount(stack, darts + 1);
            play(level, entity, RELOAD_SOUND, SoundSource.MASTER);
            return;
        }
        if (darts <= 0) {
            return;
        }

        setDartCount(stack, darts - 1);
        ItemStack dart = Ssc14Items.FOAM_DART.get().getDefaultInstance();
        if (offhand.isEmpty()) {
            living.setItemInHand(InteractionHand.OFF_HAND, dart);
            if (living instanceof Player player) {
                player.getInventory().setChanged();
            }
        } else if (level instanceof ServerLevel serverLevel) {
            ItemEntity dropped = new ItemEntity(serverLevel, entity.getX(),
                    entity.getY() + entity.getBbHeight() / 3.0D,
                    entity.getZ(), dart);
            dropped.setPickUpDelay(0);
            dropped.setUnlimitedLifetime();
            serverLevel.addFreshEntity(dropped);
        }
        play(level, entity, RELOAD_SOUND, SoundSource.MASTER);
    }

    public static void fire(LevelAccessor level, Entity entity, ItemStack stack) {
        if (entity == null) {
            return;
        }
        if (entity instanceof Player player) {
            player.getCooldowns().addCooldown(stack, FIRE_COOLDOWN_TICKS);
        }

        double darts = storedDarts(stack);
        if (darts <= 0) {
            return;
        }
        setDartCount(stack, darts - 1);
        Level projectileLevel = entity.level();
        if (!projectileLevel.isClientSide()) {
            Projectile projectile = createProjectile(projectileLevel, entity);
            projectile.setPos(entity.getX(), entity.getEyeY() - 0.1D, entity.getZ());
            projectile.shoot(entity.getLookAngle().x, entity.getLookAngle().y,
                    entity.getLookAngle().z, 2.0F, 1.0F);
            projectileLevel.addFreshEntity(projectile);
        }
        play(level, entity, SHOT_SOUND, SoundSource.PLAYERS);
    }

    private static Projectile createProjectile(Level level, Entity shooter) {
        AbstractArrow projectile = new FoamDartProjectileEntity(
                Ssc14EntityTypes.FOAM_DART_PROJECTILE.get(), level);
        projectile.setOwner(shooter);
        projectile.setBaseDamage(0.0D);
        projectile.setSilent(true);
        projectile.pickup = AbstractArrow.Pickup.ALLOWED;
        return projectile;
    }

    private static double storedDarts(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getDoubleOr(DART_COUNT_KEY, 0.0D);
    }

    private static void setDartCount(ItemStack stack, double count) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack,
                tag -> tag.putDouble(DART_COUNT_KEY, count));
    }

    private static void play(LevelAccessor level, Entity entity,
            Identifier soundId, SoundSource source) {
        if (!(level instanceof Level concreteLevel)) {
            return;
        }
        var sound = BuiltInRegistries.SOUND_EVENT.getValue(soundId);
        if (!concreteLevel.isClientSide()) {
            concreteLevel.playSound(null, BlockPos.containing(entity.position()),
                    sound, source, 1.0F, 1.0F);
        } else {
            concreteLevel.playLocalSound(entity.getX(), entity.getY(), entity.getZ(),
                    sound, source, 1.0F, 1.0F, false);
        }
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
