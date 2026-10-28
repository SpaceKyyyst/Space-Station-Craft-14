package net.ssc14.common.station;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StationBudgetServiceTest {
    @Test
    void validatesAmountsAndDelegatesAtomicSpending() {
        double[] budget = {100};
        StationBudgetService.installSpender((level, amount) -> {
            if (budget[0] < amount) return false;
            budget[0] -= amount;
            return true;
        });

        assertFalse(StationBudgetService.trySpend(null, 0));
        assertFalse(StationBudgetService.trySpend(null, Double.NaN));
        assertTrue(StationBudgetService.trySpend(null, 40));
        assertFalse(StationBudgetService.trySpend(null, 70));
    }
}
