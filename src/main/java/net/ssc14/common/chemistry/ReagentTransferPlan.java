package net.ssc14.common.chemistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Exact largest-remainder allocation; O(n log n) time and O(n) memory. */
public final class ReagentTransferPlan {
    public static final int TRANSFER_VOLUME = 5;

    private ReagentTransferPlan() {
    }

    public static Map<String, Integer> calculate(Map<String, Integer> source, Map<String, Integer> target,
            int sourceCapacity, int targetCapacity) {
        long sourceVolume = volume(source);
        long targetVolume = volume(target);
        if (sourceVolume <= 0 || sourceVolume > sourceCapacity || targetVolume < 0 || targetVolume >= targetCapacity) {
            return Map.of();
        }
        int amount = (int) Math.min(TRANSFER_VOLUME, Math.min(sourceVolume, targetCapacity - targetVolume));
        return allocate(source, amount);
    }

    public static Map<String, Integer> allocate(Map<String, Integer> source, int requestedAmount) {
        long sourceVolume = volume(source);
        if (sourceVolume <= 0 || requestedAmount <= 0) {
            return Map.of();
        }
        int amount = (int) Math.min(sourceVolume, requestedAmount);
        Map<String, Integer> result = new LinkedHashMap<>();
        List<Remainder> remainders = new ArrayList<>();
        int remaining = amount;
        for (Map.Entry<String, Integer> entry : source.entrySet()) {
            long scaled = (long) entry.getValue() * amount;
            int share = (int) (scaled / sourceVolume);
            result.put(entry.getKey(), share);
            remaining -= share;
            remainders.add(new Remainder(entry.getKey(), scaled % sourceVolume));
        }
        remainders.sort(Comparator.comparingLong(Remainder::amount).reversed().thenComparing(Remainder::reagent));
        for (int index = 0; index < remaining; index++) {
            result.compute(remainders.get(index).reagent(), (key, value) -> value + 1);
        }
        result.values().removeIf(value -> value == 0);
        return Collections.unmodifiableMap(result);
    }

    /** Returns the remaining contents after an allocated portion is consumed. */
    public static Optional<Map<String, Integer>> consume(Map<String, Integer> source,
            Map<String, Integer> portion) {
        if (source == null || portion == null || portion.isEmpty()) {
            return Optional.empty();
        }
        Map<String, Integer> result = new LinkedHashMap<>(source);
        for (Map.Entry<String, Integer> entry : portion.entrySet()) {
            int amount = entry.getValue() == null ? 0 : entry.getValue();
            int stored = result.getOrDefault(entry.getKey(), 0);
            if (entry.getKey() == null || entry.getKey().isBlank() || amount <= 0 || stored < amount) {
                return Optional.empty();
            }
            if (stored == amount) {
                result.remove(entry.getKey());
            } else {
                result.put(entry.getKey(), stored - amount);
            }
        }
        return Optional.of(Collections.unmodifiableMap(result));
    }

    private static long volume(Map<String, Integer> reagents) {
        long total = 0;
        for (Map.Entry<String, Integer> entry : reagents.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null || entry.getValue() < 0) {
                return -1;
            }
            total += entry.getValue();
        }
        return total;
    }

    private record Remainder(String reagent, long amount) {
    }
}
