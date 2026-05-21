
package net.mcreator.ssc;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public final class AtmosphereManager {

    // 🔧 НАСТРОЙКИ ЧАСТОТЫ СПАВНА ЧАСТИЦ (в начале класса!)
    private static final float PLASMA_SPAWN_CHANCE = 0.3f;    // Шанс спавна частицы плазмы (~33%)
    private static final float VAPOR_SPAWN_CHANCE = 0.3f;     // Шанс спавна частицы пара (~25%)
    private static final float SECOND_TYPE_CHANCE = 0.5f;      // Шанс спавна "второго типа" частиц (50/50)

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

    public void onBlockChanged(BlockPos pos) {
        AtmosRegion region = getOrCreateRegion(pos);
        region.updatePermeability(pos);
        activeRegions.add(packRegionKey(pos));
    }

    public AtmosCell getCellAt(BlockPos pos) { 
        return getOrCreateRegion(pos).getCellAt(pos); 
    }

    public void tick() {
        if (level == null || level.isClientSide) return;
        
        // 🔍 ОПТИМИЗАЦИЯ: убираем регионы, которые далеко от игроков и неактивны
        if (!activeRegions.isEmpty()) {
            Set<Long> toRemove = new HashSet<>();
            for (long key : activeRegions) {
                AtmosRegion region = regions.get(key);
                if (region == null) { toRemove.add(key); continue; }
                
                BlockPos origin = region.getRegionOrigin();
                boolean nearPlayer = false;
                
                for (ServerPlayer player : level.players()) {
                    if (player.blockPosition().distSqr(origin) < 256*256) {
                        nearPlayer = true;
                        break;
                    }
                }
                
                if (!nearPlayer && !region.isActive) {
                    toRemove.add(key);
                }
            }
            activeRegions.removeAll(toRemove);
        }
        
        tickCounter++;
        boolean fastTick = tickCounter % FAST_TICK_INTERVAL == 0;
        boolean slowTick = tickCounter % SLOW_TICK_INTERVAL == 0;
        if (!fastTick && !slowTick) return;
        
        float deltaTime = fastTick ? 0.2f : 1.0f;
        
        // 🔒 Лимит активных регионов
        if (activeRegions.size() > MAX_ACTIVE_REGIONS) {
            List<Long> toRemove = new ArrayList<>(activeRegions);
            toRemove.sort((a, b) -> {
                AtmosRegion ra = regions.get(a);
                AtmosRegion rb = regions.get(b);
                long ta = ra != null ? ra.lastActivityTime : 0;
                long tb = rb != null ? rb.lastActivityTime : 0;
                return Long.compare(ta, tb);
            });
            for (int i = 0; i < toRemove.size() - MAX_ACTIVE_REGIONS; i++) {
                activeRegions.remove(toRemove.get(i));
            }
        }
        
        Set<Long> toTick = new HashSet<>(activeRegions);
        for (long key : toTick) {
            AtmosRegion region = regions.get(key);
            if (region == null) { 
                activeRegions.remove(key); 
                continue; 
            }
            
            region.tick(deltaTime, REACTIONS);
            
            // 👇 Визуализация плазмы
            if (!region.getPendingVisuals().isEmpty() && !level.isClientSide) {
                for (AtmosRegion.PlasmaVisualData data : region.getPendingVisuals()) {
                    BlockPos particlePos = new BlockPos((int)data.x, (int)data.y, (int)data.z);
                    
                    for (ServerPlayer player : level.players()) {
                        if (player.blockPosition().distSqr(particlePos) < 4096) {
                            if (level.random.nextFloat() < PLASMA_SPAWN_CHANCE) {
                                level.sendParticles(
                                    net.mcreator.ssc.init.Ssc14ModParticleTypes.PLASMA_PARTICLES_1.get(),
                                    data.x + (level.random.nextFloat() - 0.5f),
                                    data.y + (level.random.nextFloat() - 0.5f),
                                    data.z + (level.random.nextFloat() - 0.5f),
                                    1, 0, 0, 0, 0
                                );
                                if (level.random.nextFloat() < SECOND_TYPE_CHANCE) {
                                    level.sendParticles(
                                        net.mcreator.ssc.init.Ssc14ModParticleTypes.PLASMA_PARTICLES_2.get(),
                                        data.x + (level.random.nextFloat() - 0.5f),
                                        data.y + (level.random.nextFloat() - 0.5f),
                                        data.z + (level.random.nextFloat() - 0.5f),
                                        1, 0, 0, 0, 0
                                    );
                                }
                            }
                        }
                    }
                }
            }
            
            // 👇 Визуализация водяного пара (аналогично плазме)
            if (!region.getPendingVaporVisuals().isEmpty() && !level.isClientSide) {
                for (AtmosRegion.VaporVisualData data : region.getPendingVaporVisuals()) {
                    BlockPos particlePos = new BlockPos((int)data.x, (int)data.y, (int)data.z);
                    
                    for (ServerPlayer player : level.players()) {
                        if (player.blockPosition().distSqr(particlePos) < 4096) {
                            if (level.random.nextFloat() < VAPOR_SPAWN_CHANCE) {
                                level.sendParticles(
                                    net.mcreator.ssc.init.Ssc14ModParticleTypes.WATER_VAPOR_PARTICLES_1.get(),
                                    data.x + (level.random.nextFloat() - 0.5f),
                                    data.y + (level.random.nextFloat() - 0.5f),
                                    data.z + (level.random.nextFloat() - 0.5f),
                                    1, 0, 0, 0, 0
                                );
                                if (level.random.nextFloat() < SECOND_TYPE_CHANCE) {
                                    level.sendParticles(
                                        net.mcreator.ssc.init.Ssc14ModParticleTypes.WATER_VAPOR_PARTICLES_2.get(),
                                        data.x + (level.random.nextFloat() - 0.5f),
                                        data.y + (level.random.nextFloat() - 0.5f),
                                        data.z + (level.random.nextFloat() - 0.5f),
                                        1, 0, 0, 0, 0
                                    );
                                }
                            }
                        }
                    }
                }
            }
            
            if (!region.isActive && System.currentTimeMillis() - region.lastActivityTime > 15000) {
                activeRegions.remove(key);
            }
        }
    }

    public static void onWorldUnload(ServerLevel level) { 
        CACHE.remove(level.dimension().location().toString()); 
    }
}
