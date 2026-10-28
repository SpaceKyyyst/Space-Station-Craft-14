package net.ssc14.common.chemistry;

import java.util.LinkedHashMap;
import java.util.Map;
import net.ssc14.core.math.ProportionalAllocation;

/** Computes a portion without mutating the container. */
public final class MixturePortion {
    private MixturePortion() { }

    public static Map<String, Integer> take(Map<String, Integer> contents, int requested) {
        Map<String, Integer> portion = new LinkedHashMap<>();
        if (requested <= 0) return portion;
        long[] limits = contents.values().stream().mapToLong(value -> Math.max(0, value)).toArray();
        long[] amounts = ProportionalAllocation.allocate(requested, limits);
        int index = 0;
        for (String reagent : contents.keySet()) {
            if (amounts[index] > 0) portion.put(reagent, Math.toIntExact(amounts[index]));
            index++;
        }
        return portion;
    }
}
