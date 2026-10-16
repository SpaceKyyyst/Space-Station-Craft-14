package net.mcreator.ssc;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class AtmosphereManager {
    private static GasReaction[] REACTIONS;
    private static final Map<ServerLevel, AtmosphereManager> CACHE = new HashMap<>();
    
    private final Map<Long, AtmosRegion> regions;
    private final AtmosphereSavedData savedData;
    private static final int MAX_REGIONS_PER_STEP = 8;
    private final Set<Long> activeRegions = new LinkedHashSet<>();
    private int tickCounter = 0;
    private static final int FAST_TICK_INTERVAL = 4;
    private final ServerLevel level;

    private AtmosphereManager(ServerLevel level) {
        this.level = level;
        this.savedData = AtmosphereSavedData.get(level);
        this.regions = savedData.regions();
        this.activeRegions.addAll(regions.keySet());
    }

    public static void initReactions() {
        if (REACTIONS == null) REACTIONS = net.mcreator.ssc.atmosphere.AtmosphereReactions.create();
    }

    public static AtmosphereManager get(ServerLevel level) {
        initReactions();
        return CACHE.computeIfAbsent(level, AtmosphereManager::new);
    }
    
    static long packRegionKey(int rx, int ry, int rz) {
        return (((long)rx & 0x3FFFFFF) << 38) | (((long)ry & 0xFFF) << 26) | (rz & 0x3FFFFFF);
    }

    private long packRegionKey(BlockPos pos) {
        return packRegionKey(pos.getX() >> 4, pos.getY() >> 4, pos.getZ() >> 4);
    }
    private AtmosRegion getOrCreateRegion(BlockPos pos) {
        long key = packRegionKey(pos);
        AtmosRegion region = regions.get(key);
        if (region == null) {
            int rx = pos.getX() >> 4;
            int ry = pos.getY() >> 4;
            int rz = pos.getZ() >> 4;
            region = new AtmosRegion(rx, ry, rz);
            regions.put(key, region);
        }
        region.initializeIfLoaded(level);
        return region;
    }

    public void onBlockChanged(BlockPos pos) {
        AtmosRegion region = regions.get(packRegionKey(pos));
        if (region != null) {
            region.initializeIfLoaded(level);
            region.updatePermeability(pos);
            markCellDirty(pos);
        }
        for (var direction : net.minecraft.core.Direction.values()) {
            BlockPos neighbor = pos.relative(direction);
            if ((region == null || !region.contains(neighbor)) && regions.containsKey(packRegionKey(neighbor))) {
                getOrCreateRegion(neighbor).updatePermeability(neighbor);
                markCellDirty(neighbor);
            }
        }
    }

    public void markCellDirty(BlockPos pos) {
        getOrCreateRegion(pos).markCellDirty(pos);
        activeRegions.add(packRegionKey(pos));
        savedData.setDirty();
    }

    public AtmosCell readCellAt(BlockPos pos) {
        var region = regions.get(packRegionKey(pos));
        var cell = region == null ? null : region.peekCellAt(pos);
        return cell == null ? new AtmosCell() : cell;
    }

    public AtmosCell getCellAt(BlockPos pos) {
        // This legacy API returns a mutable cell; callers may change it directly.
        savedData.setDirty();
        return getOrCreateRegion(pos).getCellAt(pos);
    }

    public void tick() {
        if (level == null || level.isClientSide()) return;
        
        tickCounter++;
        boolean fastTick = tickCounter % FAST_TICK_INTERVAL == 0;
        if (!fastTick) return;
        
        if (activeRegions.isEmpty()) return;
        try (var timing = net.mcreator.ssc.diagnostics.TickPhase.start("atmosphere", activeRegions.size())) {
            simulateStep();
        }
    }

    private void simulateStep() {
        float deltaTime = FAST_TICK_INTERVAL / 20f;

        List<Long> toTick = new ArrayList<>(MAX_REGIONS_PER_STEP);
        var iterator = activeRegions.iterator();
        while (iterator.hasNext() && toTick.size() < MAX_REGIONS_PER_STEP) {
            toTick.add(iterator.next());
            iterator.remove();
        }

        for (long key : toTick) {
            AtmosRegion region = regions.get(key);
            if (region == null) { activeRegions.remove(key); continue; }
            if (!level.hasChunkAt(region.getRegionOrigin())) continue;
            region.initializeIfLoaded(level);
            region.tick(deltaTime, REACTIONS);
            if (region.hasPendingWork()) activeRegions.add(key);
            savedData.setDirty();

            net.mcreator.ssc.visual.GasVisualDispatcher.send(level, region.getPendingVisuals());
        }
    }

    public static void onWorldUnload(ServerLevel level) {
        CACHE.remove(level);
    }

    public static void onChunkAvailabilityChanged(ServerLevel level, net.minecraft.world.level.ChunkPos chunk) {
        var manager = CACHE.get(level);
        if (manager == null) return;
        for (var region : manager.regions.values()) {
            var origin = region.getRegionOrigin();
            if (Math.abs((origin.getX() >> 4) - chunk.x()) <= 1 && Math.abs((origin.getZ() >> 4) - chunk.z()) <= 1) {
                region.invalidatePermeability();
                if (region.hasPendingWork()) manager.activeRegions.add(manager.packRegionKey(origin));
            }
        }
    }
}
