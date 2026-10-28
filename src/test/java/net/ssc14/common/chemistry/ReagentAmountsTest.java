package net.ssc14.common.chemistry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

class ReagentAmountsTest {
    @Test
    void computesCapacityAndValidAdditions() {
        Map<String, Integer> contents = Map.of("water", 20, "ethanol", 5);

        assertEquals(25, ReagentAmounts.total(contents));
        assertEquals(15, ReagentAmounts.availableSpace(contents, 40));
        assertTrue(ReagentAmounts.canAdd(contents, 40, "salt", 10));
    }

    @Test
    void rejectsInvalidAdditionsAndOverCapacityReactions() {
        Map<String, Integer> contents = Map.of("water", 20);

        assertFalse(ReagentAmounts.canAdd(contents, 20, "salt", 1));
        assertTrue(ReagentAmounts.react(contents, 20, Map.of("water", 1), Map.of("salt", 2)).isEmpty());
    }
}
