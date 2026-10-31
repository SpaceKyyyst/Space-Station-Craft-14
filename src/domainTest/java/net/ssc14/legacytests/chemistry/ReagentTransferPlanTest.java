package net.ssc14.legacytests.chemistry;

import org.junit.jupiter.api.Test;
import java.util.Map;
import net.ssc14.common.chemistry.ReagentTransferPlan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReagentTransferPlanTest {
    @Test
    void allocatesDrinkingPortionsWithoutRoundingEachReagentUp() {
        assertEquals(Map.of("a", 1), ReagentTransferPlan.allocate(Map.of("a", 1, "b", 1, "c", 1), 1));
        assertEquals(Map.of("a", 1, "b", 1), ReagentTransferPlan.allocate(Map.of("a", 1, "b", 1), 10));
    }

    @Test
    void conservesEveryTransferredUnitAndRespectsRemainingCapacity() {
        Map<String, Integer> source = Map.of("a", 4, "b", 3, "c", 3);
        Map<String, Integer> plan = ReagentTransferPlan.calculate(source, Map.of("a", 7), 10, 10);
        assertEquals(3, plan.values().stream().mapToInt(Integer::intValue).sum());
        plan.forEach((id, amount) -> assertTrue(amount > 0 && amount <= source.get(id)));
    }

    @Test
    void rejectsNegativeOverflowedAndOvercapacityContents() {
        assertTrue(ReagentTransferPlan.calculate(Map.of("a", -1), Map.of(), 10, 10).isEmpty());
        assertTrue(ReagentTransferPlan.calculate(Map.of("a", Integer.MAX_VALUE, "b", 1), Map.of(), Integer.MAX_VALUE, 10).isEmpty());
        assertTrue(ReagentTransferPlan.calculate(Map.of("a", 10), Map.of("b", -1), 10, 10).isEmpty());
        assertTrue(ReagentTransferPlan.calculate(Map.of("a", 10), Map.of("b", 10), 10, 10).isEmpty());
    }

    @Test
    void usesExactArithmeticAndStableTiebreaking() {
        assertEquals(Map.of("a", 3, "b", 2), ReagentTransferPlan.calculate(
                Map.of("b", 1_000_000_000, "a", 1_000_000_000), Map.of(), Integer.MAX_VALUE, 10));
        assertEquals(Map.of("a", 1), ReagentTransferPlan.calculate(Map.of("a", 1), Map.of(), 10, 10));
    }

    @Test
    void neverOverdrawsAnyReagentAcrossSmallMixtures() {
        for (int first = 0; first <= 10; first++) {
            for (int second = 0; second <= 10; second++) {
                Map<String, Integer> source = Map.of("a", first, "b", second);
                Map<String, Integer> plan = ReagentTransferPlan.calculate(source, Map.of(), 20, 20);
                assertEquals(Math.min(5, first + second), plan.values().stream().mapToInt(Integer::intValue).sum());
                plan.forEach((id, amount) -> assertTrue(amount <= source.get(id)));
            }
        }
    }
}
