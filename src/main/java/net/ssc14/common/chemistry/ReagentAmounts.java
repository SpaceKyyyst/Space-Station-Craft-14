package net.ssc14.common.chemistry;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/** Integer quantities; planning uses O(reagents + inputs + outputs) time and O(reagents + outputs) memory. */
public final class ReagentAmounts {
    private ReagentAmounts() {
    }

    public static long total(Map<String, Integer> amounts) {
        long total = 0;
        for (int amount : amounts.values()) {
            if (amount < 0) {
                throw new IllegalArgumentException("Reagent amount must not be negative");
            }
            total += amount;
        }
        return total;
    }

    public static int availableSpace(Map<String, Integer> amounts, int capacity) {
        return (int) Math.max(0L, (long) capacity - total(amounts));
    }

    public static boolean canAdd(Map<String, Integer> amounts, int capacity, String id, int amount) {
        return id != null && !id.isBlank() && amount > 0
            && amount <= availableSpace(amounts, capacity)
            && (long) amounts.getOrDefault(id, 0) + amount <= Integer.MAX_VALUE;
    }

    public static Optional<Map<String, Integer>> react(Map<String, Integer> amounts, int capacity,
            Map<String, Integer> inputs, Map<String, Integer> outputs) {
        if (inputs.isEmpty() || capacity < 0 || !validRecipeAmounts(inputs) || !validRecipeAmounts(outputs)) {
            return Optional.empty();
        }
        // Validate stored quantities before subtraction can hide corrupt negative values.
        total(amounts);
        Map<String, Integer> result = new LinkedHashMap<>(amounts);
        for (var input : inputs.entrySet()) {
            int required = input.getValue();
            int stored = result.getOrDefault(input.getKey(), 0);
            if (stored < required) {
                return Optional.empty();
            }
            if (stored == required) {
                result.remove(input.getKey());
            } else {
                result.put(input.getKey(), stored - required);
            }
        }
        // Check the complete output, including reactions that increase the volume.
        for (var output : outputs.entrySet()) {
            long next = (long) result.getOrDefault(output.getKey(), 0) + output.getValue();
            if (next > Integer.MAX_VALUE) {
                return Optional.empty();
            }
            result.put(output.getKey(), (int) next);
        }
        if (total(result) > capacity) {
            return Optional.empty();
        }
        return Optional.of(Collections.unmodifiableMap(result));
    }

    private static boolean validRecipeAmounts(Map<String, Integer> amounts) {
        for (var entry : amounts.entrySet()) {
            if (entry.getKey() == null || entry.getKey().isBlank()
                    || entry.getValue() == null || entry.getValue() <= 0) {
                return false;
            }
        }
        return true;
    }
}
