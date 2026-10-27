package net.ssc14.content.item.safety;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.Ssc14Items;
import net.ssc14.content.particle.Ssc14ParticleTypes;

/** Applies the legacy extinguisher state transitions and spray effects. */
public final class ExtinguisherUseService {
    public static final int STATE_CHANGE_COOLDOWN_TICKS = 5;
    public static final int SPRAY_COOLDOWN_TICKS = 20;
    public static final int SPRAY_PARTICLE_COUNT = 10;

    private static final double SPRAY_SPREAD = 0.25D;
    private static final double BASE_SPRAY_SPEED = 0.7D;
    private static final double MINIMUM_SPEED_FACTOR = 0.5D;
    private static final double SPEED_FACTOR_RANGE = 0.4D;
    private static final double VELOCITY_ENCODING_SCALE = 0.01D;
    private static final Identifier STATE_CHANGE_SOUND = id("items_flask_close1");
    private static final Identifier SPRAY_SOUND = id("extinguish_effect");

    private ExtinguisherUseService() { }

    public static void execute(LevelAccessor level, Entity entity, ItemStack stack) {
        if (entity == null) {
            return;
        }
        if (stack.is(Ssc14Items.EXTINGUISHER.get())) {
            replaceMainHand(stack, entity, Ssc14Items.EXTINGUISHER_OPEN.get());
            playSound(level, entity, STATE_CHANGE_SOUND);
            addCooldown(entity, stack, STATE_CHANGE_COOLDOWN_TICKS);
            return;
        }
        if (!stack.is(Ssc14Items.EXTINGUISHER_OPEN.get())) {
            return;
        }
        if (entity.isShiftKeyDown()) {
            replaceMainHand(stack, entity, Ssc14Items.EXTINGUISHER.get());
            playSound(level, entity, STATE_CHANGE_SOUND);
            addCooldown(entity, stack, STATE_CHANGE_COOLDOWN_TICKS);
            return;
        }
        playSound(level, entity, SPRAY_SOUND);
        spawnSpray(level, entity);
        addCooldown(entity, stack, SPRAY_COOLDOWN_TICKS);
    }

    private static void replaceMainHand(ItemStack source, Entity entity, Item replacement) {
        source.shrink(1);
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.setItemInHand(InteractionHand.MAIN_HAND,
                    replacement.getDefaultInstance());
            if (livingEntity instanceof Player player) {
                player.getInventory().setChanged();
            }
        }
    }

    private static void playSound(LevelAccessor levelAccessor, Entity entity,
            Identifier soundId) {
        if (!(levelAccessor instanceof Level level)) {
            return;
        }
        float volume = (float) Mth.nextDouble(level.getRandom(), 0.9D, 1.1D);
        float pitch = (float) Mth.nextDouble(level.getRandom(), 0.9D, 1.1D);
        if (level.isClientSide()) {
            level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(),
                    BuiltInRegistries.SOUND_EVENT.getValue(soundId), SoundSource.MASTER,
                    volume, pitch, false);
        } else {
            level.playSound(null, BlockPos.containing(entity.position()),
                    BuiltInRegistries.SOUND_EVENT.getValue(soundId), SoundSource.MASTER,
                    volume, pitch);
        }
    }

    private static void spawnSpray(LevelAccessor levelAccessor, Entity entity) {
        if (!(levelAccessor instanceof ServerLevel serverLevel)
                || !(entity instanceof LivingEntity livingEntity)) {
            return;
        }
        Vec3 look = livingEntity.getLookAngle().normalize();
        double baseX = Math.round(livingEntity.getX() - 0.5D);
        double baseY = Math.round(livingEntity.getY() + livingEntity.getBbHeight() / 1.25D);
        double baseZ = Math.round(livingEntity.getZ() - 0.5D);
        for (int index = 0; index < SPRAY_PARTICLE_COUNT; index++) {
            Vec3 direction = randomizedDirection(serverLevel, look);
            double speedFactor = MINIMUM_SPEED_FACTOR
                    + serverLevel.getRandom().nextFloat() * SPEED_FACTOR_RANGE;
            Vec3 velocity = direction.scale(BASE_SPRAY_SPEED * speedFactor);
            serverLevel.sendParticles(Ssc14ParticleTypes.EXTINGUISHER_SPRAY.get(),
                    baseX + velocity.x * VELOCITY_ENCODING_SCALE,
                    baseY + velocity.y * VELOCITY_ENCODING_SCALE,
                    baseZ + velocity.z * VELOCITY_ENCODING_SCALE,
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private static Vec3 randomizedDirection(ServerLevel level, Vec3 look) {
        double x = look.x + (level.getRandom().nextFloat() - 0.5F) * SPRAY_SPREAD;
        double y = look.y + (level.getRandom().nextFloat() - 0.5F) * SPRAY_SPREAD;
        double z = look.z + (level.getRandom().nextFloat() - 0.5F) * SPRAY_SPREAD;
        return new Vec3(x, y, z).normalize();
    }

    private static void addCooldown(Entity entity, ItemStack stack, int ticks) {
        if (entity instanceof Player player) {
            player.getCooldowns().addCooldown(stack, ticks);
        }
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
