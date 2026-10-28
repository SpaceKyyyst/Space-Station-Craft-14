package net.mcreator.ssc;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import net.ssc14.common.chemistry.MixturePortion;
import net.ssc14.core.math.ProportionalAllocation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AllocationTest {
    @Test void rejectsNegativeBudget() {
        assertThrows(IllegalArgumentException.class,
                () -> ProportionalAllocation.allocate(-1, new long[]{1}));
    }

    @Test void rejectsAnyNegativeLimit() {
        assertThrows(IllegalArgumentException.class,
                () -> ProportionalAllocation.allocate(1, new long[]{1, -1, 2}));
    }

    @Test void returnsEmptyOrZeroAllocationWhenThereIsNoDemand() {
        assertArrayEquals(new long[0], ProportionalAllocation.allocate(10, new long[0]));
        assertArrayEquals(new long[]{0, 0, 0},
                ProportionalAllocation.allocate(10, new long[]{0, 0, 0}));
    }

    @Test void capsAllocationAtEveryLimitWhenBudgetExceedsDemand() {
        assertArrayEquals(new long[]{1, 2, 3},
                ProportionalAllocation.allocate(100, new long[]{1, 2, 3}));
    }

    @Test void usesBigIntegerIntermediatesForLongMaxValues() {
        assertArrayEquals(
                new long[]{4_611_686_018_427_387_903L, 4_611_686_018_427_387_904L},
                ProportionalAllocation.allocate(
                        Long.MAX_VALUE, new long[]{Long.MAX_VALUE, Long.MAX_VALUE}));
    }

    @Test void preservesExactAvailableBudgetAcrossRepresentativeInputs() {
        assertExactBudget(2, new long[]{1, 1, 1});
        assertExactBudget(17, new long[]{2, 5, 7, 11});
        assertExactBudget(100, new long[]{2, 5, 7, 11});
        assertArrayEquals(new long[]{0, 1, 1},
                ProportionalAllocation.allocate(2, new long[]{1, 1, 1}));
    }

    @Test void leavesInputLimitsUnchanged() {
        long[] limits = {5, 7, 11};
        long[] original = limits.clone();

        ProportionalAllocation.allocate(9, limits);

        assertArrayEquals(original, limits);
    }

    @Test void legacyFacadePreservesResultsAndExceptionTypes() {
        assertArrayEquals(new long[]{0, 1, 1},
                ProportionalAllocation.allocate(
                        2, new long[]{1, 1, 1}));
        assertArrayEquals(new long[]{1, 2, 3},
                ProportionalAllocation.allocate(
                        100, new long[]{1, 2, 3}));
        assertThrows(IllegalArgumentException.class,
                () -> ProportionalAllocation.allocate(
                        -1, new long[]{1}));
        assertThrows(IllegalArgumentException.class,
                () -> ProportionalAllocation.allocate(
                        1, new long[]{-1}));
    }

    @Test void drinkPreservesVolume() {
        var contents = new LinkedHashMap<String, Integer>();
        contents.put("water", 10);
        contents.put("sugar", 11);
        var portion = MixturePortion.take(contents, 10);
        assertEquals(10, portion.values().stream().mapToInt(Integer::intValue).sum());
        assertEquals(Map.of("water", 10, "sugar", 11), contents);
        assertEquals(contents, MixturePortion.take(contents, 100));
        assertTrue(MixturePortion.take(contents, -1).isEmpty());
    }

    private static void assertExactBudget(long budget, long[] limits) {
        long totalLimit = Arrays.stream(limits).sum();
        long actual = Arrays.stream(ProportionalAllocation.allocate(budget, limits)).sum();
        assertEquals(Math.min(budget, totalLimit), actual);
    }
}
