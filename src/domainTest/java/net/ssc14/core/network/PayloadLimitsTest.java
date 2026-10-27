package net.ssc14.core.network;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PayloadLimitsTest {
    @Test
    void snapshotSurvivesMutationAndPreservesNullSelection() {
        Map<String, Object> source = new HashMap<>();
        source.put("selected_trigger_id", null);
        source.put("L1_text", "Switch");
        Map<String, Object> snapshot = PayloadLimits.snapshot(source);
        source.clear();
        assertEquals("Switch", snapshot.get("L1_text"));
        assertNull(snapshot.get("selected_trigger_id"));
        assertThrows(UnsupportedOperationException.class, () -> snapshot.put("changed", true));
    }

    @Test
    void rejectsNegativeAndOversizedCountsBeforeAllocation() {
        assertThrows(IllegalArgumentException.class, () -> PayloadLimits.requireCount(-1, 256));
        assertThrows(IllegalArgumentException.class, () -> PayloadLimits.requireCount(Integer.MAX_VALUE, 256));
        assertEquals(256, PayloadLimits.requireCount(256, 256));
    }

    @Test
    void rejectsUnsupportedValuesAndTotalTextBudgetOverflow() {
        assertThrows(IllegalArgumentException.class, () -> PayloadLimits.snapshot(Map.of("value", new Object())));
        Map<String, Object> state = new HashMap<>();
        for (int index = 0; index < 5; index++) {
            state.put("L" + index, "x".repeat(PayloadLimits.MAX_TEXT_LENGTH));
        }
        assertThrows(IllegalArgumentException.class, () -> PayloadLimits.snapshot(state));
    }

    @Test
    void rejectsOversizedKeysAndIndividualText() {
        assertThrows(IllegalArgumentException.class,
                () -> PayloadLimits.snapshot(Map.of("x".repeat(PayloadLimits.MAX_KEY_LENGTH + 1), true)));
        assertThrows(IllegalArgumentException.class,
                () -> PayloadLimits.snapshot(Map.of("text", "x".repeat(PayloadLimits.MAX_TEXT_LENGTH + 1))));
    }
}
