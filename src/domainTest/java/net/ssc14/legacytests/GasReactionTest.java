package net.ssc14.legacytests;

import net.ssc14.common.atmosphere.model.GasType;
import net.ssc14.common.atmosphere.reaction.GasReaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GasReactionTest {
    private boolean[] required() {
        boolean[] flags = new boolean[GasType.COUNT];
        flags[0] = true;
        return flags;
    }

    private float[] amounts(float oxygen) {
        float[] values = new float[GasType.COUNT];
        values[0] = oxygen;
        return values;
    }

    private GasReaction reaction(float heat) {
        return GasReaction.create("test", required(), 300, 500,
                amounts(0.25f), amounts(0), heat, 0.5f);
    }

    @Test
    void constructorAndAccessorsDoNotExposeDefinitionArrays() {
        boolean[] flags = required();
        float[] consume = amounts(0.25f);
        float[] produce = amounts(0);
        GasReaction reaction = new GasReaction("test", flags, 300, 500, consume, produce, 0, 0.5f);
        flags[0] = false;
        consume[0] = 100;
        produce[0] = 100;
        reaction.requiredGases()[0] = false;
        reaction.consumptionPerTick()[0] = 100;
        reaction.productionPerTick()[0] = 100;
        assertTrue(reaction.requiredGases()[0]);
        float[] moles = amounts(1);
        assertTrue(reaction.apply(moles, new float[]{400}));
        assertEquals(0.75f, moles[0]);
    }

    @Test
    void directConstructorValidatesJustLikeFactory() {
        assertThrows(IllegalArgumentException.class, () -> new GasReaction("test",
                new boolean[1], 300, 500, amounts(0), amounts(0), 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new GasReaction("test",
                required(), 500, 300, amounts(0), amounts(0), 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new GasReaction("test",
                required(), 300, 500, amounts(-1), amounts(0), 0, 0));
    }

    @Test
    void rejectsNonFiniteDefinitions() {
        for (float invalid : new float[]{Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY}) {
            assertThrows(IllegalArgumentException.class, () -> GasReaction.create("test",
                    required(), invalid, 500, amounts(0), amounts(0), 0, 0));
            assertThrows(IllegalArgumentException.class, () -> GasReaction.create("test",
                    required(), 300, 500, amounts(invalid), amounts(0), 0, 0));
            assertThrows(IllegalArgumentException.class, () -> reaction(invalid));
        }
    }

    @Test
    void respectsInclusiveTemperatureAndActivationBoundaries() {
        GasReaction reaction = reaction(0);
        assertTrue(reaction.canOccur(amounts(0.5f), 300));
        assertTrue(reaction.canOccur(amounts(0.5f), 500));
        assertFalse(reaction.canOccur(amounts(0.49f), 400));
        assertFalse(reaction.canOccur(amounts(1), 299));
        assertFalse(reaction.canOccur(amounts(1), 501));
        assertFalse(reaction.canOccur(amounts(0), 400));
    }

    @Test
    void invalidCellNumbersRejectWithoutMutation() {
        for (float invalid : new float[]{Float.NaN, Float.POSITIVE_INFINITY, -1}) {
            float[] moles = amounts(1);
            moles[1] = invalid;
            float[] before = moles.clone();
            float[] temperature = {400};
            assertFalse(reaction(100).apply(moles, temperature));
            assertArrayEquals(before, moles);
            assertEquals(400, temperature[0]);
        }
        assertFalse(reaction(0).canOccur(amounts(1), Float.NaN));
    }

    @Test
    void insufficientReagentsLeaveBothStateArraysUntouched() {
        GasReaction reaction = GasReaction.create("test", required(), 300, 500,
                amounts(2), amounts(0), 100, 0.5f);
        float[] moles = amounts(1);
        float[] temperature = {400};
        assertFalse(reaction.apply(moles, temperature));
        assertArrayEquals(amounts(1), moles);
        assertEquals(400, temperature[0]);
    }

    @Test
    void appliesStoichiometryAndHeatAndClampsCooling() {
        float[] moles = amounts(1);
        float[] temperature = {400};
        assertTrue(reaction(100).apply(moles, temperature));
        assertEquals(0.75f, moles[0]);
        assertEquals(400 + 100 / (0.75f * GasType.OXYGEN.getHeatCapacity()), temperature[0], 0.0001f);
        assertTrue(reaction(-100000).apply(amounts(1), temperature));
        assertEquals(1f, temperature[0]);
    }

    @Test
    void overflowDoesNotPartiallyApplyReaction() {
        GasReaction reaction = GasReaction.create("overflow", required(), 300, 500,
                amounts(0), amounts(Float.MAX_VALUE), 0, 0);
        float[] moles = amounts(Float.MAX_VALUE);
        float[] temperature = {400};
        assertFalse(reaction.apply(moles, temperature));
        assertEquals(Float.MAX_VALUE, moles[0]);
        assertEquals(400, temperature[0]);
    }

    @Test
    void invalidArrayShapesAndAliasingFailExplicitly() {
        assertThrows(IllegalArgumentException.class, () -> reaction(0).canOccur(new float[1], 400));
        assertThrows(IllegalArgumentException.class, () -> reaction(0).apply(amounts(1), new float[0]));
        float[] aliased = amounts(400);
        assertThrows(IllegalArgumentException.class, () -> reaction(0).apply(aliased, aliased));
    }
}
