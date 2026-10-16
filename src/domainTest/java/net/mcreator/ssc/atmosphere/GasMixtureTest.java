package net.mcreator.ssc.atmosphere;

import java.util.concurrent.atomic.AtomicInteger;
import net.mcreator.ssc.GasReaction;
import net.mcreator.ssc.GasType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GasMixtureTest {
    @Test
    void invalidNumbersAndIndicesCannotPoisonState() {
        GasMixture gas = new GasMixture();
        gas.setMoles(0, 2);
        for (float bad : new float[]{Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY}) {
            gas.setMoles(0, bad);
            gas.addMoles(0, bad);
            gas.setTemperature(bad);
        }
        gas.setMoles(-1, 4);
        gas.setMoles(GasType.COUNT, 4);
        assertEquals(2, gas.getMoles(0));
        assertEquals(GasMixture.DEFAULT_TEMPERATURE_K, gas.getTemperature());
        gas.setMoles(0, -3);
        gas.setTemperature(-3);
        assertEquals(0, gas.getTotalMoles());
        assertEquals(1, gas.getTemperature());
    }

    @Test
    void pressureAndTotalStayFiniteAtFloatExtremes() {
        GasMixture gas = new GasMixture();
        for (int index = 0; index < GasType.COUNT; index++) gas.setMoles(index, Float.MAX_VALUE);
        gas.setTemperature(Float.MAX_VALUE);
        assertTrue(Float.isFinite(gas.getPressure()));
        assertTrue(Float.isFinite(gas.getTotalMoles()));
        gas.addMoles(0, Float.MAX_VALUE);
        assertEquals(Float.MAX_VALUE, gas.getMoles(0));
    }

    @Test
    void equalPressureDifferentSpeciesMixWithoutLosingEitherSpecies() {
        GasMixture oxygen = new GasMixture();
        GasMixture nitrogen = new GasMixture();
        oxygen.setMoles(0, 10);
        nitrogen.setMoles(1, 10);
        assertTrue(oxygen.diffuseTo(nitrogen, 1, 1) > 0);
        assertTrue(oxygen.getMoles(1) > 0);
        assertTrue(nitrogen.getMoles(0) > 0);
        assertEquals(10, oxygen.getMoles(0) + nitrogen.getMoles(0), 1e-5);
        assertEquals(10, oxygen.getMoles(1) + nitrogen.getMoles(1), 1e-5);
    }

    @Test
    void transferPreservesEachSpeciesAndHeatAcrossTemperatures() {
        GasMixture first = new GasMixture();
        GasMixture second = new GasMixture();
        first.setMoles(0, 20);
        first.setMoles(4, 5);
        first.setTemperature(800);
        second.setMoles(1, 10);
        second.setTemperature(100);
        double heat = heat(first) + heat(second);
        for (int step = 0; step < 100; step++) first.diffuseTo(second, 0.8f, 0.2f);
        assertEquals(20, first.getMoles(0) + second.getMoles(0), 0.0001);
        assertEquals(5, first.getMoles(4) + second.getMoles(4), 0.0001);
        assertEquals(10, first.getMoles(1) + second.getMoles(1), 0.0001);
        assertEquals(heat, heat(first) + heat(second), heat * 0.00001);
    }

    @Test
    void longTimeStepsDoNotOvershootEquilibriumOrGoNegative() {
        GasMixture first = new GasMixture();
        GasMixture second = new GasMixture();
        first.setMoles(0, 10);
        first.diffuseTo(second, Float.MAX_VALUE, Float.MAX_VALUE);
        assertEquals(5, first.getMoles(0));
        assertEquals(5, second.getMoles(0));
    }

    @Test
    void invalidTimeAndPermeabilityAndSelfTransferDoNothing() {
        GasMixture first = new GasMixture();
        GasMixture second = new GasMixture();
        first.setMoles(0, 10);
        for (float invalid : new float[]{0, -1, Float.NaN, Float.POSITIVE_INFINITY}) {
            assertEquals(0, first.diffuseTo(second, invalid, 1));
            assertEquals(0, first.diffuseTo(second, 1, invalid));
        }
        assertEquals(0, first.diffuseTo(first, 1, 1));
        assertEquals(0, first.diffuseTo(null, 1, 1));
        assertEquals(10, first.getMoles(0));
        assertEquals(0, second.getMoles(0));
    }

    @Test
    void mutationsAndTransfersWakeBothOwnersButNoOpsDoNot() {
        AtomicInteger firstChanges = new AtomicInteger();
        AtomicInteger secondChanges = new AtomicInteger();
        GasMixture first = new GasMixture(firstChanges::incrementAndGet);
        GasMixture second = new GasMixture(secondChanges::incrementAndGet);
        first.setMoles(0, 10);
        first.setMoles(0, 10);
        assertEquals(1, firstChanges.get());
        first.diffuseTo(second, 1, 1);
        assertEquals(2, firstChanges.get());
        assertEquals(1, secondChanges.get());
    }

    @Test
    void pureNitrousOxideDecaysWithoutNitrogenFuelOrOxygen() {
        GasMixture gas = new GasMixture();
        gas.setMoles(GasType.NITROUS_OXIDE.ordinal(), 1);
        gas.setTemperature(1000);
        assertTrue(gas.tryReactions(StandardGasReactions.create()));
        assertEquals(0.95f, gas.getMoles(GasType.NITROUS_OXIDE.ordinal()));
        assertEquals(0.05f, gas.getMoles(GasType.NITROGEN.ordinal()));
        assertEquals(0.025f, gas.getMoles(GasType.OXYGEN.ordinal()));
    }

    @Test
    void everyRegisteredReactionIsReachableThroughMixtureApi() {
        for (GasReaction reaction : StandardGasReactions.create()) {
            GasMixture gas = new GasMixture();
            for (int index = 0; index < GasType.COUNT; index++) {
                if (reaction.requiredGases()[index] || reaction.consumptionPerTick()[index] > 0) gas.setMoles(index, 1);
            }
            gas.setTemperature((reaction.minTemperatureK() + reaction.maxTemperatureK()) / 2);
            assertTrue(gas.tryReactions(new GasReaction[]{reaction}), reaction.id());
        }
    }

    @Test
    void coldReactionsCanRunWithLessThanPreviousGlobalThreshold() {
        GasMixture gas = new GasMixture();
        gas.setMoles(GasType.NITROGEN.ordinal(), 0.025f);
        gas.setMoles(GasType.FREON.ordinal(), 0.025f);
        gas.setTemperature(200);
        assertTrue(gas.tryReactions(StandardGasReactions.create()));
        assertTrue(gas.getMoles(GasType.NITROUS_OXIDE.ordinal()) > 0);
    }

    private double heat(GasMixture gas) {
        double heatCapacity = 0;
        for (int index = 0; index < GasType.COUNT; index++) {
            heatCapacity += gas.getMoles(index) * (double) GasType.byIndex(index).getHeatCapacity();
        }
        return heatCapacity * gas.getTemperature();
    }
}
