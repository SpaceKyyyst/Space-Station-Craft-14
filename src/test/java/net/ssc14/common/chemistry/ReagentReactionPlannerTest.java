package net.ssc14.common.chemistry;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReagentReactionPlannerTest {
    @Test
    void consumesInputsAndAddsOutputsAtomically() {
        var result = ReagentReactionPlanner.plan(
                Map.of("water", 2, "salt", 1), 10,
                Map.of("water", 1), Map.of("brine", 2));

        assertEquals(Map.of("water", 1, "salt", 1, "brine", 2), result.orElseThrow());
    }

    @Test
    void rejectsMissingInputAndCapacityOverflowWithoutMutation() {
        assertTrue(ReagentReactionPlanner.plan(
                Map.of("water", 1), 10, Map.of("oxygen", 1), Map.of("steam", 1)).isEmpty());
        assertTrue(ReagentReactionPlanner.plan(
                Map.of("water", 1), 1, Map.of("water", 1), Map.of("steam", 2)).isEmpty());
    }
}
