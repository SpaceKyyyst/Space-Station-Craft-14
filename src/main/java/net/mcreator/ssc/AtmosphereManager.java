
package net.mcreator.ssc;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.mcreator.ssc.init.Ssc14ModParticleTypes;
import java.util.*;

public final class AtmosphereManager {
    // ═══════════════════════════════════════════════════════════════
    // 🎨 НАСТРОЙКИ ВИЗУАЛИЗАЦИИ
    // ═══════════════════════════════════════════════════════════════
    private static final float MIN_SPAWN_DISTANCE = 0f;
    private static final float MAX_SPAWN_DISTANCE = 64f; // Уменьшено для производительности
    private static final float MIN_DENSITY_THRESHOLD = 0.0f;
    private static final float MAX_DENSITY_EFFECT = 1.0f;
    private static final float BASE_SPAWN_CHANCE = 0.25f;
    private static final float PLASMA_CHANCE_MULTIPLIER = 1.3f;
    private static final float TRITIUM_CHANCE_MULTIPLIER = 1.3f;
    private static final float DEFAULT_GAS_CHANCE_MULTIPLIER = 1.0f;
    private static final float SECOND_PARTICLE_CHANCE = 0.5f;
    private static final float PARTICLE_OFFSET_RANGE = 0.5f;

    // ═══════════════════════════════════════════════════════════════
    // ⚙️ СИСТЕМНЫЕ НАСТРОЙКИ
    // ═══════════════════════════════════════════════════════════════
    private static GasReaction[] REACTIONS;
    private static final Map<String, AtmosphereManager> CACHE = new HashMap<>();
    private static final int MAX_ACTIVE_REGIONS = 128;

    private final Map<Long, AtmosRegion> regions = new HashMap<>();
    private final Set<Long> activeRegions = new HashSet<>();

    private int tickCounter = 0;
    private static final int FAST_TICK_INTERVAL = 4;
    private static final int SLOW_TICK_INTERVAL = 20;
    private ServerLevel level;

    private AtmosphereManager(ServerLevel level) { 
        this.level = level; 
    }

    public static void initReactions() {
        if (REACTIONS != null) return;
        
        java.util.function.Function<int[], boolean[]> flags = indices -> {
            boolean[] arr = new boolean[GasType.COUNT];
            for (int i : indices) {
                if (i >= 0 && i < GasType.COUNT) arr[i] = true;
            }
            return arr;
        };
        
        java.util.function.Function<float[], float[]> values = v -> {
            float[] arr = new float[GasType.COUNT];
            System.arraycopy(v, 0, arr, 0, Math.min(v.length, GasType.COUNT));
            return arr;
        };
        
        List<GasReaction> list = new ArrayList<>();
        list.add(GasReaction.create("plasma_burn", 
            flags.apply(new int[]{GasType.PLASMA.ordinal(), GasType.OXYGEN.ordinal()}), 
            373f, 1643f, 
            values.apply(new float[]{0,0,0,0,0.05f,0,0,0,0,0}), 
            values.apply(new float[]{0,0,0.04f,0,0,0,0.0075f,0,0,0}), 
            5000f, 0.1f));
        list.add(GasReaction.create("tritium_burn", 
            flags.apply(new int[]{GasType.TRITIUM.ordinal(), GasType.OXYGEN.ordinal()}), 
            373f, 1643f, 
            values.apply(new float[]{0,0,0,0,0,0,0.05f,0,0,0}), 
            values.apply(new float[]{0,0,0.03f,0,0,0,0,0,0,0.03f}), 
            8000f, 0.1f));
        list.add(GasReaction.create("freon_synthesis", 
            flags.apply(new int[]{GasType.NITROGEN.ordinal(), GasType.OXYGEN.ordinal(), GasType.TRITIUM.ordinal()}), 
            73f, 253f, 
            values.apply(new float[]{0,0.01f,0,0,0,0,0.02f,0,0,0}), 
            values.apply(new float[]{0,0.01f,0,0,0,0,0,0.015f,0,0}), 
            -2000f, 0.05f));
        list.add(GasReaction.create("freon_cool", 
            flags.apply(new int[]{GasType.FREON.ordinal(), GasType.NITROGEN.ordinal()}), 
            23f, 250f, 
            values.apply(new float[]{0,0.02f,0,0,0,0,0,0.01f,0,0}), 
            values.apply(new float[]{0,0.02f,0,0.01f,0,0,0,0,0,0}), 
            -3000f, 0.05f));
        list.add(GasReaction.create("n2o_decay", 
            flags.apply(new int[]{GasType.NITROUS_OXIDE.ordinal()}), 
            850f, 5000f, 
            values.apply(new float[]{0,0,0,0.05f,0,0,0,0,0,0}), 
            values.apply(new float[]{0.025f,0.025f,0,0,0,0,0,0,0,0}), 
            1000f, 0.1f));
        list.add(GasReaction.create("ammonia_scrub", 
            flags.apply(new int[]{GasType.AMMONIA.ordinal(), GasType.OXYGEN.ordinal()}), 
            323f, 350f, 
            values.apply(new float[]{0,0.01f,0,0,0,0.02f,0,0,0,0}), 
            values.apply(new float[]{0,0,0,0.01f,0,0,0,0,0,0.01f}), 
            -500f, 0.1f));
        
        REACTIONS = list.toArray(new GasReaction[0]);
    }

    public static AtmosphereManager get(ServerLevel level) {
        initReactions();
        String key = level.dimension().location().toString();
        return CACHE.computeIfAbsent(key, k -> new AtmosphereManager(level));
    }

    private static long packRegionKey(int rx, int ry, int rz) {
        return (((long)rx & 0x3FFFFFF) << 38) | (((long)ry & 0xFFF) << 26) | (rz & 0x3FFFFFF);
    }

    private long packRegionKey(BlockPos pos) { 
        return packRegionKey(pos.getX() >> 4, pos.getY() >> 4, pos.getZ() >> 4); 
    }

    private AtmosRegion getOrCreateRegion(BlockPos pos) {
        long key = packRegionKey(pos);
        AtmosRegion region = regions.get(key);
        if (region == null) {
            int rx = pos.getX() >> 4, ry = pos.getY() >> 4, rz = pos.getZ() >> 4;
            region = new AtmosRegion(rx, ry, rz);
            if (level != null) region.initFromWorld(level);
            regions.put(key, region);
        }
        return region;
    }

    // ✅ КЛЮЧЕВОЕ: регион ДОБАВЛЯЕТСЯ в activeRegions при изменении
    public void onBlockChanged(BlockPos pos) {
        AtmosRegion region = getOrCreateRegion(pos);
        region.updatePermeability(pos);
        activeRegions.add(packRegionKey(pos)); // 🔑 Это критически важно!
    }

    public AtmosCell getCellAt(BlockPos pos) { 
        return getOrCreateRegion(pos).getCellAt(pos); 
    }

    private int getParticleType(int gasIndex, boolean isSecondType, RandomSource random) {
        if (gasIndex == GasType.PLASMA.ordinal() || gasIndex == GasType.TRITIUM.ordinal()) {
            return isSecondType ? 2 : 1;
        }
        return 1 + random.nextInt(4);
    }

    private ParticleOptions getParticleForGas(int gasIndex, int type) {
        try {
            switch (gasIndex) {
                case 4: // PLASMA
                    return type == 1 ? 
                        Ssc14ModParticleTypes.PLASMA_PARTICLES_1.get() : 
                        Ssc14ModParticleTypes.PLASMA_PARTICLES_2.get();
                case 6: // TRITIUM
                    return type == 1 ? 
                        Ssc14ModParticleTypes.TRITIUM_PARTICLES_1.get() : 
                        Ssc14ModParticleTypes.TRITIUM_PARTICLES_2.get();
                case 9: // WATER_VAPOR
                    return switch (type) {
                        case 1 -> Ssc14ModParticleTypes.WATER_VAPOR_PARTICLES_1.get();
                        case 2 -> Ssc14ModParticleTypes.WATER_VAPOR_PARTICLES_2.get();
                        case 3 -> Ssc14ModParticleTypes.WATER_VAPOR_PARTICLES_3.get();
                        default -> Ssc14ModParticleTypes.WATER_VAPOR_PARTICLES_4.get();
                    };
                case 5: // AMMONIA
                    return switch (type) {
                        case 1 -> Ssc14ModParticleTypes.AMMONIA_PARTICLES_1.get();
                        case 2 -> Ssc14ModParticleTypes.AMMONIA_PARTICLES_2.get();
                        case 3 -> Ssc14ModParticleTypes.AMMONIA_PARTICLES_3.get();
                        default -> Ssc14ModParticleTypes.AMMONIA_PARTICLES_4.get();
                    };
                case 7: // FREON
                    return switch (type) {
                        case 1 -> Ssc14ModParticleTypes.FREON_PARTICLES_1.get();
                        case 2 -> Ssc14ModParticleTypes.FREON_PARTICLES_2.get();
                        case 3 -> Ssc14ModParticleTypes.FREON_PARTICLES_3.get();
                        default -> Ssc14ModParticleTypes.FREON_PARTICLES_4.get();
                    };
                case 8: // HELIUM
                    return switch (type) {
                        case 1 -> Ssc14ModParticleTypes.HELIUM_PARTICLES_1.get();
                        case 2 -> Ssc14ModParticleTypes.HELIUM_PARTICLES_2.get();
                        case 3 -> Ssc14ModParticleTypes.HELIUM_PARTICLES_3.get();
                        default -> Ssc14ModParticleTypes.HELIUM_PARTICLES_4.get();
                    };
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private float calculateFinalSpawnChance(double distSqr, float density, int gasIndex) {
        float distanceFactor;
        double maxDistSqr = MAX_SPAWN_DISTANCE * MAX_SPAWN_DISTANCE;
        double minDistSqr = MIN_SPAWN_DISTANCE * MIN_SPAWN_DISTANCE;
        
        if (distSqr <= minDistSqr) {
            distanceFactor = 1.0f;
        } else if (distSqr >= maxDistSqr) {
            distanceFactor = 0.0f;
        } else {
            float t = (float)((distSqr - minDistSqr) / (maxDistSqr - minDistSqr));
            distanceFactor = 1.0f - t * t;
        }
        
        float densityFactor;
        if (density <= MIN_DENSITY_THRESHOLD) {
            densityFactor = 0.0f;
        } else if (density >= MAX_DENSITY_EFFECT) {
            densityFactor = 1.0f;
        } else {
            densityFactor = (density - MIN_DENSITY_THRESHOLD) / (MAX_DENSITY_EFFECT - MIN_DENSITY_THRESHOLD);
        }
         
        float gasMultiplier;
        if (gasIndex == GasType.PLASMA.ordinal()) {
            gasMultiplier = PLASMA_CHANCE_MULTIPLIER;
        } else if (gasIndex == GasType.TRITIUM.ordinal()) {
            gasMultiplier = TRITIUM_CHANCE_MULTIPLIER;
        } else {
            gasMultiplier = DEFAULT_GAS_CHANCE_MULTIPLIER;
        }
        
        return Math.min(1.0f, BASE_SPAWN_CHANCE * distanceFactor * densityFactor * gasMultiplier);
    }

    public void tick() {
        if (level == null || level.isClientSide) return;
        
        // 🔍 ОПТИМИЗАЦИЯ: чистка далёких неактивных регионов
        if (!activeRegions.isEmpty()) {
            Set<Long> toRemove = new HashSet<>();
            for (long key : activeRegions) {
                AtmosRegion region = regions.get(key);
                if (region == null) { toRemove.add(key); continue; }
                BlockPos origin = region.getRegionOrigin();
                boolean nearPlayer = false;
                for (ServerPlayer player : level.players()) {
                    if (player.blockPosition().distSqr(origin) < 256*256) {
                        nearPlayer = true; break;
                    }
                }
                if (!nearPlayer && !region.isActive) toRemove.add(key);
            }
            activeRegions.removeAll(toRemove);
        }
        
        tickCounter++;
        boolean fastTick = tickCounter % FAST_TICK_INTERVAL == 0;
        boolean slowTick = tickCounter % SLOW_TICK_INTERVAL == 0;
        if (!fastTick && !slowTick) return;
        
        float deltaTime = fastTick ? 0.2f : 1.0f;
        
        if (activeRegions.size() > MAX_ACTIVE_REGIONS) {
            List<Long> toRemove = new ArrayList<>(activeRegions);
            toRemove.sort((a, b) -> {
                AtmosRegion ra = regions.get(a), rb = regions.get(b);
                return Long.compare(
                    ra != null ? ra.lastActivityTime : 0,
                    rb != null ? rb.lastActivityTime : 0
                );
            });
            for (int i = 0; i < toRemove.size() - MAX_ACTIVE_REGIONS; i++)
                activeRegions.remove(toRemove.get(i));
        }
        
        Set<Long> toTick = new HashSet<>(activeRegions);
        for (long key : toTick) {
            AtmosRegion region = regions.get(key);
            if (region == null) { activeRegions.remove(key); continue; }
            
            region.tick(deltaTime, REACTIONS);
            
            // 👇 ВИЗУАЛИЗАЦИЯ ВСЕХ ГАЗОВ 👇
            if (!region.getPendingVisuals().isEmpty() && !level.isClientSide) {
                for (AtmosRegion.GasVisualData data : region.getPendingVisuals()) {
                    BlockPos particlePos = new BlockPos((int)data.x, (int)data.y, (int)data.z);
                    
                    for (ServerPlayer player : level.players()) {
                        double distSqr = player.blockPosition().distSqr(particlePos);
                        if (distSqr >= MAX_SPAWN_DISTANCE * MAX_SPAWN_DISTANCE) continue;
                        
                        float spawnChance = calculateFinalSpawnChance(distSqr, data.density, data.gasIndex);
                        if (spawnChance <= 0f || level.random.nextFloat() >= spawnChance) continue;
                        
                        int pType = getParticleType(data.gasIndex, false, level.random);
                        ParticleOptions particle = getParticleForGas(data.gasIndex, pType);
                        if (particle != null) {
                            // ✅ sendParticles: последние 3 параметра — deltaX, deltaY, deltaZ (разброс скорости), потом speed
                            level.sendParticles(
                                particle,
                                data.x + (level.random.nextFloat() - 0.5f) * PARTICLE_OFFSET_RANGE,
                                data.y + (level.random.nextFloat() - 0.5f) * PARTICLE_OFFSET_RANGE,
                                data.z + (level.random.nextFloat() - 0.5f) * PARTICLE_OFFSET_RANGE,
                                1,          // count
                                0.0, 0.0, 0.0,  // deltaX, deltaY, deltaZ (НУЛИ = нет разброса скорости!)
                                0.0         // speed = 0 = частицы не падают!
                            );
                        }
                        
                        if (level.random.nextFloat() < SECOND_PARTICLE_CHANCE) {
                            int sType = getParticleType(data.gasIndex, true, level.random);
                            ParticleOptions second = getParticleForGas(data.gasIndex, sType);
                            if (second != null && second != particle) {
                                level.sendParticles(
                                    second,
                                    data.x + (level.random.nextFloat() - 0.5f) * PARTICLE_OFFSET_RANGE,
                                    data.y + (level.random.nextFloat() - 0.5f) * PARTICLE_OFFSET_RANGE,
                                    data.z + (level.random.nextFloat() - 0.5f) * PARTICLE_OFFSET_RANGE,
                                    1, 0.0, 0.0, 0.0, 0.0
                                );
                            }
                        }
                    }
                }
            }
            
            if (!region.isActive && System.currentTimeMillis() - region.lastActivityTime > 15000)
                activeRegions.remove(key);
        }
    }

    public static void onWorldUnload(ServerLevel level) { 
        CACHE.remove(level.dimension().location().toString()); 
    }
}
