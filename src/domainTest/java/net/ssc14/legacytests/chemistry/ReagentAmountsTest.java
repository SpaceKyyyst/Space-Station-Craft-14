package net.ssc14.legacytests.chemistry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;
import net.ssc14.common.chemistry.ReagentAmounts;
import org.junit.jupiter.api.Test;

class ReagentAmountsTest {
    @Test
    void sumsInLongAndDoesNotExposeNegativeCapacityForOverfilledContainers() {
        var amounts = Map.of("water", Integer.MAX_VALUE, "ethanol", Integer.MAX_VALUE);
        assertEquals(4_294_967_294L, ReagentAmounts.total(amounts));
        assertEquals(0, ReagentAmounts.availableSpace(amounts, 500));
        assertFalse(ReagentAmounts.canAdd(amounts, Integer.MAX_VALUE, "water", 1));
    }

    @Test
    void rejectsInvalidOrOverflowingAdditions() {
        assertFalse(ReagentAmounts.canAdd(Map.of(), 50, "water", -1));
        assertFalse(ReagentAmounts.canAdd(Map.of(), 50, "water", 0));
        assertFalse(ReagentAmounts.canAdd(Map.of(), 50, "water", 51));
        assertFalse(ReagentAmounts.canAdd(Map.of(), 50, "", 1));
        assertTrue(ReagentAmounts.canAdd(Map.of("water", 49), 50, "water", 1));
    }

    @Test
    void rejectsNegativeStoredAmounts() {
        assertThrows(IllegalArgumentException.class, () -> ReagentAmounts.total(Map.of("water", -1)));
    }

    @Test
    void plansAllProductsAtomicallyAndKeepsOriginalWhenTheyDoNotFit() {
        var original = new LinkedHashMap<>(Map.of("water", 4, "ethanol", 4));
        var inputs = Map.of("water", 2, "ethanol", 2);
        assertTrue(ReagentAmounts.react(original, 10, inputs, Map.of("a", 4, "b", 4)).isEmpty());
        assertEquals(Map.of("water", 4, "ethanol", 4), original);
        assertEquals(Map.of("water", 2, "ethanol", 2, "a", 3, "b", 3),
            ReagentAmounts.react(original, 10, inputs, Map.of("a", 3, "b", 3)).orElseThrow());
    }

    @Test
    void consumesBeforeCountingOutputAndSupportsNonReagentEffects() {
        assertEquals(Map.of("ice", 10), ReagentAmounts.react(Map.of("water", 10), 10,
            Map.of("water", 10), Map.of("ice", 10)).orElseThrow());
        assertEquals(Map.of(), ReagentAmounts.react(Map.of("water", 10), 10,
            Map.of("water", 10), Map.of()).orElseThrow());
    }

    @Test
    void rejectsMissingInputsEmptyRecipesAndProductOverflow() {
        assertTrue(ReagentAmounts.react(Map.of("water", 1), 50, Map.of("water", 2), Map.of()).isEmpty());
        assertTrue(ReagentAmounts.react(Map.of(), 50, Map.of(), Map.of("water", 1)).isEmpty());
        assertTrue(ReagentAmounts.react(Map.of("a", Integer.MAX_VALUE, "b", 1), Integer.MAX_VALUE,
            Map.of("b", 1), Map.of("a", 1)).isEmpty());
    }

    @Test
    void rejectsInvalidRecipeQuantitiesAndIdentifiersWithoutConsumingInputs() {
        var original = new LinkedHashMap<>(Map.of("water", 10));
        for (int amount : new int[]{0, -1, Integer.MIN_VALUE}) {
            assertTrue(ReagentAmounts.react(original, 10, Map.of("water", amount), Map.of()).isEmpty());
            assertTrue(ReagentAmounts.react(original, 10, Map.of("water", 1), Map.of("ice", amount)).isEmpty());
        }
        assertTrue(ReagentAmounts.react(original, 10, Map.of("water", 1), Map.of(" ", 1)).isEmpty());
        var invalid = new LinkedHashMap<String, Integer>();
        invalid.put(null, 1);
        assertTrue(ReagentAmounts.react(original, 10, invalid, Map.of()).isEmpty());
        invalid.clear();
        invalid.put("water", null);
        assertTrue(ReagentAmounts.react(original, 10, invalid, Map.of()).isEmpty());
        assertEquals(Map.of("water", 10), original);
    }

    @Test
    void allowsVolumeReducingReactionToRecoverAnOverfilledContainer() {
        assertEquals(Map.of("water", 10), ReagentAmounts.react(Map.of("water", 12), 10,
            Map.of("water", 2), Map.of()).orElseThrow());
        assertTrue(ReagentAmounts.react(Map.of("water", 12), 10,
            Map.of("water", 1), Map.of()).isEmpty());
        assertTrue(ReagentAmounts.react(Map.of("water", 1), -1,
            Map.of("water", 1), Map.of()).isEmpty());
    }

    @Test
    void mergesExistingProductsAfterSubtractingSharedInputsWithoutOverflow() {
        assertEquals(Map.of("water", Integer.MAX_VALUE), ReagentAmounts.react(
            Map.of("water", Integer.MAX_VALUE), Integer.MAX_VALUE,
            Map.of("water", 1), Map.of("water", 1)).orElseThrow());
        assertTrue(ReagentAmounts.react(Map.of("water", Integer.MAX_VALUE), Integer.MAX_VALUE,
            Map.of("water", 1), Map.of("water", 2)).isEmpty());
    }

    @Test
    void failedLateIngredientValidationNeverPartiallyConsumesEarlierIngredients() {
        var original = new LinkedHashMap<>(Map.of("water", 10, "sugar", 10));
        var inputs = new LinkedHashMap<String, Integer>();
        inputs.put("water", 5);
        inputs.put("sugar", 11);
        assertTrue(ReagentAmounts.react(original, 20, inputs, Map.of("syrup", 16)).isEmpty());
        assertEquals(Map.of("water", 10, "sugar", 10), original);
    }

    @Test
    void resultCannotBeChangedAfterValidation() {
        var result = ReagentAmounts.react(Map.of("water", 2), 10, Map.of("water", 1), Map.of()).orElseThrow();
        assertThrows(UnsupportedOperationException.class, () -> result.put("water", 100));
    }
}
