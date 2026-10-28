package net.ssc14.common.chemistry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/** Pure reaction planning shared by canonical chemistry and compatibility bridges. */
public final class ReagentReactionPlanner {
    private ReagentReactionPlanner() {
    }

    /**
     * Applies one reaction atomically in memory. The input map is never modified.
     * Missing inputs, invalid amounts, and capacity overflow produce an empty result.
     */
    public static Optional<Map<String, Integer>> plan(
            Map<String, Integer> current,
            int capacity,
            Map<String, Integer> inputs,
            Map<String, Integer> outputs) {
        if (current == null || inputs == null || outputs == null || capacity < 0) {
            return Optional.empty();
        }
        Map<String, Integer> next = new LinkedHashMap<>(current);
        for (Map.Entry<String, Integer> input : inputs.entrySet()) {
            if (input.getKey() == null || input.getKey().isBlank()
                    || input.getValue() == null || input.getValue() <= 0
                    || next.getOrDefault(input.getKey(), 0) < input.getValue()) {
                return Optional.empty();
            }
            int remaining = next.get(input.getKey()) - input.getValue();
            if (remaining == 0) {
                next.remove(input.getKey());
            } else {
                next.put(input.getKey(), remaining);
            }
        }
        for (Map.Entry<String, Integer> output : outputs.entrySet()) {
            if (output.getKey() == null || output.getKey().isBlank()
                    || output.getValue() == null || output.getValue() <= 0) {
                return Optional.empty();
            }
            long total = (long) next.getOrDefault(output.getKey(), 0) + output.getValue();
            if (total > Integer.MAX_VALUE) {
                return Optional.empty();
            }
            next.put(output.getKey(), (int) total);
        }
        long total = ReagentAmounts.total(next);
        return total > capacity ? Optional.empty() : Optional.of(next);
    }
}
