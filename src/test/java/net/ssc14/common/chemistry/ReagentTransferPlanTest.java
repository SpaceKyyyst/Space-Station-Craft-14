package net.ssc14.common.chemistry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

class ReagentTransferPlanTest {
    @Test
    void allocatesRequestedVolumeByLargestRemainder() {
        Map<String, Integer> result = ReagentTransferPlan.allocate(
                Map.of("water", 2, "ethanol", 1), 5);

        assertEquals(Map.of("water", 2, "ethanol", 1), result);
        assertEquals(3, result.values().stream().mapToInt(Integer::intValue).sum());
    }

    @Test
    void capsTransferByTargetCapacityAndRejectsInvalidVolumes() {
        Map<String, Integer> result = ReagentTransferPlan.calculate(
                Map.of("water", 10), Map.of("ethanol", 8), 20, 8);

        assertTrue(result.isEmpty());
        assertTrue(ReagentTransferPlan.calculate(Map.of("water", 10), Map.of(), 9, 20).isEmpty());
    }
}
