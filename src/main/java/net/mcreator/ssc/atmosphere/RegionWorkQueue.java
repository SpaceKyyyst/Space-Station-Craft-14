package net.mcreator.ssc.atmosphere;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Fair ready queue with dormant work indexed by chunk, independent of Minecraft. */
public final class RegionWorkQueue {
    private final Set<Long> ready = new LinkedHashSet<>();
    private final Map<Long, Set<Long>> byChunk = new HashMap<>();
    private final Map<Long, Long> chunkByRegion = new HashMap<>();

    public void schedule(long region, long chunk, boolean loaded) {
        Long previous = chunkByRegion.putIfAbsent(region, chunk);
        if (previous != null && previous.longValue() != chunk) {
            throw new IllegalArgumentException("A queued region cannot change chunks");
        }
        byChunk.computeIfAbsent(chunk, key -> new LinkedHashSet<>()).add(region);
        if (loaded) ready.add(region);
        else ready.remove(region);
    }

    /** O(number of queued regions in this chunk), without scanning other chunks. */
    public void resumeChunk(long chunk) {
        Set<Long> regions = byChunk.get(chunk);
        if (regions != null) ready.addAll(regions);
    }

    public void suspendChunk(long chunk) {
        Set<Long> regions = byChunk.get(chunk);
        if (regions != null) {
            for (long region : regions) ready.remove(region);
        }
    }

    /** Snapshot the step before callbacks can enqueue more work; O(limit) time/space. */
    public List<Long> takeReady(int limit) {
        if (limit <= 0) return List.of();
        List<Long> batch = new ArrayList<>(Math.min(limit, ready.size()));
        var iterator = ready.iterator();
        while (iterator.hasNext() && batch.size() < limit) {
            long region = iterator.next();
            iterator.remove();
            long chunk = chunkByRegion.remove(region);
            Set<Long> regions = byChunk.get(chunk);
            regions.remove(region);
            if (regions.isEmpty()) byChunk.remove(chunk);
            batch.add(region);
        }
        return batch;
    }
}
