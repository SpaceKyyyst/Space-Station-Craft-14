package net.ssc14.common.station;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void validatesCreditsAndDelegatesReset() {
        double[] budget = {10};
        StationBudgetService.installEditor(new StationBudgetService.Editor() {
            @Override public void credit(net.minecraft.world.level.LevelAccessor level,
                    double amount) { budget[0] += amount; }
            @Override public void reset(net.minecraft.world.level.LevelAccessor level) {
                budget[0] = 0;
            }
        });

        StationBudgetService.credit(null, -1);
        StationBudgetService.credit(null, Double.POSITIVE_INFINITY);
        assertEquals(10, budget[0]);
        StationBudgetService.credit(null, 25);
        assertEquals(35, budget[0]);
        StationBudgetService.reset(null);
        assertEquals(0, budget[0]);
    }
}
