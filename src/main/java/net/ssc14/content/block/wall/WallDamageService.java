package net.ssc14.content.block.wall;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.entity.C4CrutchEntEntity;
import net.ssc14.content.sound.Ssc14SoundEvents;
import net.ssc14.integration.neoforge.server.ServerWorkScheduler;

/** Canonical destruction and C4 degradation behavior shared by station wall blocks. */
public final class WallDamageService {
    static final int EXPLOSION_DELAY_TICKS = 1;
    static final double STEEL_DIRECT_RADIUS = 1.25;
    static final double PLASTEEL_DIRECT_RADIUS = 1.0;
    static final double DEGRADE_RADIUS = 5.0;

    private WallDamageService() { }

    public static void breakByPlayer(LevelAccessor level, double x, double y, double z,
            Entity entity) {
        if (entity == null || entity instanceof Player player
                && player.gameMode() == GameType.CREATIVE) {
            return;
        }
        replacePreservingProperties(level, BlockPos.containing(x, y, z),
                Ssc14Blocks.WALL_CARCASE.get().defaultBlockState());
        if (level instanceof ServerLevel serverLevel) {
            ItemEntity drop = new ItemEntity(serverLevel, x, y, z,
                    new ItemStack(BuiltInRegistries.ITEM.getValue(id("steel"))));
            drop.setPickUpDelay(1);
            drop.setUnlimitedLifetime();
            serverLevel.addFreshEntity(drop);
        }
        playBreakSound(level, x, y, z);
    }

    public static void scheduleSteelExplosion(ServerLevel level, double x, double y, double z) {
        scheduleExplosion(level, x, y, z, STEEL_DIRECT_RADIUS,
                Ssc14Blocks.WALL_CARCASE.get().defaultBlockState());
    }

    public static void schedulePlasteelExplosion(ServerLevel level, double x, double y, double z) {
        scheduleExplosion(level, x, y, z, PLASTEEL_DIRECT_RADIUS,
                Ssc14Blocks.PLASTEEL_WALL_CARCASE.get().defaultBlockState());
    }

    private static void scheduleExplosion(ServerLevel level, double x, double y, double z,
            double directRadius, BlockState degradedState) {
        ServerWorkScheduler.schedule(level, EXPLOSION_DELAY_TICKS,
                () -> applyExplosionDamage(level, x, y, z, directRadius, degradedState));
    }

    private static void applyExplosionDamage(ServerLevel level, double x, double y, double z,
            double directRadius, BlockState degradedState) {
        BlockPos pos = BlockPos.containing(x, y, z);
        if (hasC4(level, x, y, z, directRadius)) {
            destroyWithDrops(level, pos);
        } else if (hasC4(level, x, y, z, DEGRADE_RADIUS)) {
            replacePreservingProperties(level, pos, degradedState);
        } else {
            destroyWithDrops(level, pos);
        }
    }

    private static boolean hasC4(ServerLevel level, double x, double y, double z,
            double radius) {
        AABB bounds = new AABB(Vec3.ZERO, Vec3.ZERO)
                .move(new Vec3(x + 0.5, y + 0.5, z + 0.5)).inflate(radius);
        return !level.getEntitiesOfClass(C4CrutchEntEntity.class, bounds,
                entity -> true).isEmpty();
    }

    private static void destroyWithDrops(ServerLevel level, BlockPos pos) {
        Block.dropResources(level.getBlockState(pos), level, pos, null);
        level.destroyBlock(pos, false);
    }

    private static void replacePreservingProperties(LevelAccessor level, BlockPos pos,
            BlockState replacement) {
        BlockState original = level.getBlockState(pos);
        for (Property<?> property : original.getProperties()) {
            Property<?> target = replacement.getBlock().getStateDefinition()
                    .getProperty(property.getName());
            if (target != null) {
                replacement = copyProperty(original, replacement, property, target);
            }
        }
        level.setBlock(pos, replacement, Block.UPDATE_ALL);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static BlockState copyProperty(BlockState original, BlockState replacement,
            Property source, Property target) {
        try {
            return replacement.setValue(target, original.getValue(source));
        } catch (IllegalArgumentException ignored) {
            return replacement;
        }
    }

    private static void playBreakSound(LevelAccessor accessor, double x, double y, double z) {
        if (!(accessor instanceof Level level)) {
            return;
        }
        SoundEvent sound = RandomSource.create().nextBoolean()
                ? Ssc14SoundEvents.WALL_DESTROY.get()
                : Ssc14SoundEvents.WALLDEVICE_DESTROY.get();
        BlockPos pos = BlockPos.containing(x, y, z);
        if (level.isClientSide()) {
            level.playLocalSound(x, y, z, sound, SoundSource.NEUTRAL, 1.0f, 1.0f, false);
        } else {
            level.playSound(null, pos, sound, SoundSource.NEUTRAL, 1.0f, 1.0f);
        }
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
