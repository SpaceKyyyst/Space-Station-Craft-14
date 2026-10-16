package net.mcreator.ssc;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import net.mcreator.ssc.atmosphere.StandardGasReactions;
import net.mcreator.ssc.init.Ssc14ModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;

/** Server-thread-only world adapter, persistence owner and fair section scheduler. */
public final class AtmosphereManager {
    private static final Map<ServerLevel, AtmosphereManager> CACHE = new IdentityHashMap<>();
    private static final int TICK_INTERVAL = 4;
    private static final float STEP_SECONDS = TICK_INTERVAL / 20f;
    private static final int MAX_REGIONS_PER_TICK = 128;
    private static final int MAX_BLOCK_CHANGES_PER_TICK = 1024;
    private static final int VISUAL_INTERVAL = 40;
    private static final double MAX_PARTICLE_DISTANCE_SQUARED = 64 * 64;
    private static final float BASE_SPAWN_CHANCE = 0.25f;
    private static final float PARTICLE_OFFSET_RANGE = 0.5f;
    private static GasReaction[] reactions;
    private final ServerLevel level;
    private final AtmosphereSavedData savedData;
    private final Map<Long, AtmosRegion> regions = new HashMap<>();
    private final Set<Long> activeRegions = new LinkedHashSet<>();
    private final Set<BlockPos> pendingBlockChanges = new LinkedHashSet<>();
    private int tickCounter;

    private AtmosphereManager(ServerLevel level) {
        this.level = level;
        savedData = AtmosphereSavedData.get(level);
        for (long key : savedData.savedRegionKeys()) activeRegions.add(key);
    }

    public static void initReactions() {
        if (reactions == null) reactions = StandardGasReactions.create();
    }

    public static AtmosphereManager get(ServerLevel level) {
        initReactions();
        return CACHE.computeIfAbsent(level, AtmosphereManager::new);
    }

    static long packRegionKey(int x, int y, int z) {
        return (((long) x & 0x3FFFFFF) << 38) | (((long) y & 0xFFF) << 26) | (z & 0x3FFFFFF);
    }

    private long packRegionKey(BlockPos pos) {
        return packRegionKey(pos.getX() >> 4, pos.getY() >> 4, pos.getZ() >> 4);
    }

    private static BlockPos regionOrigin(long key) {
        int x = (int) (key >> 38);
        int y = (int) (key << 26 >> 52);
        int z = (int) (key << 38 >> 38);
        return new BlockPos(x << 4, y << 4, z << 4);
    }

    private AtmosRegion getOrCreateRegion(BlockPos pos) {
        long key = packRegionKey(pos);
        AtmosRegion region = regions.get(key);
        if (region == null) {
            region = new AtmosRegion(pos.getX() >> 4, pos.getY() >> 4, pos.getZ() >> 4,
                    () -> activate(key));
            region.initFromWorld(level);
            regions.put(key, region);
            var saved = savedData.restore(key);
            if (saved != null) region.readFromNBT(saved);
            savedData.track(key, region);
        }
        return region;
    }

    private void activate(long key) {
        activeRegions.add(key);
        savedData.setDirty();
    }

    /** Deferred until the next server tick, after cancellable block events have resolved. */
    public void onBlockChanged(BlockPos pos) {
        if (regions.containsKey(packRegionKey(pos))) pendingBlockChanges.add(pos.immutable());
        for (Direction direction : Direction.values()) {
            BlockPos neighbor = pos.relative(direction);
            if (regions.containsKey(packRegionKey(neighbor))) pendingBlockChanges.add(neighbor);
        }
    }

    public AtmosCell getCellAt(BlockPos pos) {
        if (level.isOutsideBuildHeight(pos) || !level.hasChunkAt(pos)) return null;
        return getOrCreateRegion(pos).getCellAt(pos);
    }

    public void onChunkLoaded(ChunkPos chunk) {
        for (AtmosRegion region : regions.values()) {
            BlockPos origin = region.getRegionOrigin();
            int distance = Math.abs((origin.getX() >> 4) - chunk.x) + Math.abs((origin.getZ() >> 4) - chunk.z);
            if (distance <= 1) region.wakeGasCells();
        }
    }

    private void processBlockChanges() {
        int remaining = Math.min(MAX_BLOCK_CHANGES_PER_TICK, pendingBlockChanges.size());
        while (remaining-- > 0) {
            var iterator = pendingBlockChanges.iterator();
            BlockPos pos = iterator.next();
            iterator.remove();
            AtmosRegion region = regions.get(packRegionKey(pos));
            if (region != null) region.updatePermeability(pos);
        }
    }

    public void tick() {
        processBlockChanges();
        tickCounter = (tickCounter + 1) % VISUAL_INTERVAL;
        if (tickCounter % TICK_INTERVAL != 0) return;
        int remaining = Math.min(MAX_REGIONS_PER_TICK, activeRegions.size());
        // Remove work before executing it; changed cells enqueue the section for a later step.
        while (remaining-- > 0) {
            var iterator = activeRegions.iterator();
            long key = iterator.next();
            iterator.remove();
            BlockPos origin = regionOrigin(key);
            if (!level.hasChunkAt(origin)) {
                activeRegions.add(key);
                continue;
            }
            AtmosRegion region = getOrCreateRegion(origin);
            region.tick(STEP_SECONDS, reactions);
            if (region.isActive()) activeRegions.add(key);
        }
        if (tickCounter == 0) sendGasVisuals();
    }

    private void sendGasVisuals() {
        for (ServerPlayer player : level.players()) {
            int remaining = 32;
            for (AtmosRegion region : regions.values()) {
                if (remaining <= 0) break;
                if (!level.hasChunkAt(region.getRegionOrigin())) continue;
                var visuals = region.collectGasVisuals(player.blockPosition(), remaining);
                remaining -= visuals.size();
                for (AtmosRegion.GasVisualData visual : visuals) {
                    double distance = player.position().distanceToSqr(visual.x, visual.y, visual.z);
                    if (level.getRandom().nextFloat() >= calculateFinalSpawnChance(distance, visual.density, visual.gasIndex)) continue;
                    ParticleOptions particle = getParticleForGas(visual.gasIndex,
                            getParticleType(visual.gasIndex, false, level.getRandom()));
                    if (particle != null) {
                        // Targeted overload avoids broadcasting the same particle once per nearby player.
                        level.sendParticles(player, particle, false, false,
                                visual.x, visual.y, visual.z, 1,
                                PARTICLE_OFFSET_RANGE, PARTICLE_OFFSET_RANGE, PARTICLE_OFFSET_RANGE, 0);
                    }
                }
            }
        }
    }

    private float calculateFinalSpawnChance(double distanceSquared, float density, int gasIndex) {
        if (!Double.isFinite(distanceSquared) || !Float.isFinite(density) || distanceSquared < 0
                || distanceSquared >= MAX_PARTICLE_DISTANCE_SQUARED || density <= 0) return 0;
        double normalizedDistance = distanceSquared / MAX_PARTICLE_DISTANCE_SQUARED;
        float multiplier = gasIndex == GasType.PLASMA.ordinal() || gasIndex == GasType.TRITIUM.ordinal() ? 1.3f : 1f;
        return (float) (BASE_SPAWN_CHANCE * (1 - normalizedDistance * normalizedDistance)
                * Math.min(1f, density) * multiplier);
    }
    private int getParticleType(int gasIndex, boolean isSecondType, RandomSource random) {
        if (gasIndex == GasType.PLASMA.ordinal() || gasIndex == GasType.TRITIUM.ordinal()) {
            return isSecondType ? 2 : 1;
        }
        return 1 + random.nextInt(4);
    }

    private ParticleOptions getParticleForGas(int gasIndex, int type) {

            switch (gasIndex) {
                case 4:
                    return type == 1 ? Ssc14ModParticleTypes.PLASMA_PARTICLES_1.get() : Ssc14ModParticleTypes.PLASMA_PARTICLES_2.get();
                case 6:
                    return type == 1 ? Ssc14ModParticleTypes.TRITIUM_PARTICLES_1.get() : Ssc14ModParticleTypes.TRITIUM_PARTICLES_2.get();
                case 9:
                    return switch (type) {
                        case 1 -> Ssc14ModParticleTypes.WATER_VAPOR_PARTICLES_1.get();
                        case 2 -> Ssc14ModParticleTypes.WATER_VAPOR_PARTICLES_2.get();
                        case 3 -> Ssc14ModParticleTypes.WATER_VAPOR_PARTICLES_3.get();
                        default -> Ssc14ModParticleTypes.WATER_VAPOR_PARTICLES_4.get();
                    };
                case 5:
                    return switch (type) {
                        case 1 -> Ssc14ModParticleTypes.AMMONIA_PARTICLES_1.get();
                        case 2 -> Ssc14ModParticleTypes.AMMONIA_PARTICLES_2.get();
                        case 3 -> Ssc14ModParticleTypes.AMMONIA_PARTICLES_3.get();
                        default -> Ssc14ModParticleTypes.AMMONIA_PARTICLES_4.get();
                    };
                case 7:
                    return switch (type) {
                        case 1 -> Ssc14ModParticleTypes.FREON_PARTICLES_1.get();
                        case 2 -> Ssc14ModParticleTypes.FREON_PARTICLES_2.get();
                        case 3 -> Ssc14ModParticleTypes.FREON_PARTICLES_3.get();
                        default -> Ssc14ModParticleTypes.FREON_PARTICLES_4.get();
                    };
                case 8:
                    return switch (type) {
                        case 1 -> Ssc14ModParticleTypes.HELIUM_PARTICLES_1.get();
                        case 2 -> Ssc14ModParticleTypes.HELIUM_PARTICLES_2.get();
                        case 3 -> Ssc14ModParticleTypes.HELIUM_PARTICLES_3.get();
                        default -> Ssc14ModParticleTypes.HELIUM_PARTICLES_4.get();
                    };
                default:
                    return null;
            }

    }

    public static void onWorldUnload(ServerLevel level) { CACHE.remove(level); }
}
