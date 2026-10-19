package net.mcreator.ssc.energy;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnergyTransferTest {
    @Test
    void integerRemaindersAreDelivered() {
        assertArrayEquals(new long[] {0, 1}, EnergyAllocation.distribute(1, new long[] {1, 1}));
        assertArrayEquals(new long[] {1, 2}, EnergyAllocation.distribute(3, new long[] {2, 2}));
    }

    @Test
    void noDemandConsumesNoEnergy() {
        assertArrayEquals(new long[] {0, 0}, EnergyAllocation.distribute(750, new long[] {0, 0}));
    }

    @Test
    void largeTotalsCannotOverflow() {
        long[] allocations = EnergyAllocation.distribute(Long.MAX_VALUE,
                new long[] {Long.MAX_VALUE, Long.MAX_VALUE});
        assertEquals(Long.MAX_VALUE, Math.addExact(allocations[0], allocations[1]));
    }

    @Test
    void conservesEnergyForRandomLoads() {
        Random random = new Random(14);
        for (int sample = 0; sample < 1000; sample++) {
            long[] demand = random.longs(20, 0, 2501).toArray();
            long supply = random.nextInt(100000);
            long[] allocations = EnergyAllocation.distribute(supply, demand);
            assertEquals(Math.min(supply, Arrays.stream(demand).sum()), Arrays.stream(allocations).sum());
            for (int i = 0; i < demand.length; i++) {
                assertTrue(allocations[i] >= 0 && allocations[i] <= demand[i]);
            }
        }
    }

    @Test
    void sharedSourceCannotSpendSnapshotTwice() {
        EnergyTickBudget budget = new EnergyTickBudget(500, 1000, 750, 100);
        assertEquals(400, budget.takeOutput(400));
        assertEquals(100, budget.takeOutput(400));
        assertEquals(0, budget.takeOutput(400));
    }

    @Test
    void receivingEnergyDoesNotMakeItAvailableInTheSameTick() {
        EnergyTickBudget budget = new EnergyTickBudget(0, 1000, 750, 250);
        assertEquals(250, budget.acceptInput(500));
        assertEquals(0, budget.availableOutput());
        assertEquals(0, budget.remainingInput());
    }

    @Test
    void budgetsForSeparateLevelsDoNotShareState() {
        EnergyTickBudget firstLevel = new EnergyTickBudget(500, 1000, 750, 250);
        EnergyTickBudget secondLevel = new EnergyTickBudget(25, 1000, 750, 250);
        firstLevel.takeOutput(500);
        assertEquals(25, secondLevel.takeOutput(500));
    }

    @Test
    void invalidSavedEnergyCannotCreateNegativeDemandOrExcessOutput() {
        EnergyTickBudget negative = new EnergyTickBudget(-1, 1000, 750, 250);
        EnergyTickBudget overfull = new EnergyTickBudget(2000, 1000, 1500, 250);
        assertEquals(0, negative.availableOutput());
        assertEquals(0, overfull.remainingInput());
        assertEquals(1000, overfull.availableOutput());
    }
}
