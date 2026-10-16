package net.mcreator.ssc;

import java.util.LinkedHashMap;
import java.util.Map;
import net.mcreator.ssc.chemistry.MixturePortion;
import net.mcreator.ssc.math.ProportionalAllocation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AllocationTest {
    @Test void preservesSmallBudgetAcrossManyRecipients() {
        assertArrayEquals(new long[]{0, 1, 1}, ProportionalAllocation.allocate(2, new long[]{1, 1, 1}));
    }
    @Test void supplyCannotExceedDemand() {
        assertArrayEquals(new long[]{1}, ProportionalAllocation.allocate(750, new long[]{1}));
        assertArrayEquals(new long[]{0}, ProportionalAllocation.allocate(750, new long[]{0}));
    }
    @Test void handlesOverflowAndRejectsNegativeInput() {
        assertArrayEquals(new long[]{1, 2}, ProportionalAllocation.allocate(3, new long[]{Long.MAX_VALUE, Long.MAX_VALUE}));
        assertThrows(IllegalArgumentException.class, () -> ProportionalAllocation.allocate(1, new long[]{-1}));
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
}
