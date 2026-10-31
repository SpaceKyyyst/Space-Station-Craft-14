package net.ssc14.legacytests;

import net.ssc14.common.atmosphere.model.GasType;
import net.ssc14.common.atmosphere.model.GasMixture;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GasTransportTest {
    @Test void equalPressureDifferentSpeciesMix() {
        var a = new GasMixture();
        var b = new GasMixture();
        a.setMoles(0, 10);
        b.setMoles(1, 10);
        assertEquals(a.getPressure(), b.getPressure());
        a.diffuseTo(b, 1, 1);
        assertTrue(a.getMoles(1) > 0 && b.getMoles(0) > 0);
        assertEquals(10, a.getMoles(0) + b.getMoles(0), 1e-5);
        assertEquals(10, a.getMoles(1) + b.getMoles(1), 1e-5);
    }

    @Test void expansionPreservesMixtureRatioAndTemperature() {
        var a = new GasMixture();
        var b = new GasMixture();
        a.setMoles(0, 10);
        a.setMoles(1, 30);
        a.setTemperature(600);
        a.diffuseTo(b, 1, 1);
        assertEquals(3, b.getMoles(1) / b.getMoles(0), 1e-5);
        assertEquals(600, a.getTemperature(), 1e-3);
        assertEquals(600, b.getTemperature(), 1e-3);
    }

    @Test void speciesAndThermalEnergySurviveRepeatedExchange() {
        var a = new GasMixture();
        var b = new GasMixture();
        for (int i = 0; i < GasType.COUNT; i++) {
            a.setMoles(i, i + 1);
            b.setMoles(i, 20 - i);
        }
        a.setTemperature(100);
        b.setTemperature(900);
        double initialEnergy = energy(a) + energy(b);
        for (int i = 0; i < 1000; i++) a.diffuseTo(b, 1, 0.2f);
        for (int i = 0; i < GasType.COUNT; i++) {
            assertEquals(21, a.getMoles(i) + b.getMoles(i), 0.001);
            assertEquals(a.getMoles(i), b.getMoles(i), 0.001);
        }
        assertEquals(initialEnergy, energy(a) + energy(b), initialEnergy * 0.0001);
        assertEquals(a.getTemperature(), b.getTemperature(), 0.01);
    }

    @Test void hugeStepCannotOvershootEquilibrium() {
        var a = new GasMixture();
        var b = new GasMixture();
        a.setMoles(0, 10);
        a.diffuseTo(b, Float.MAX_VALUE, Float.MAX_VALUE);
        assertEquals(5, a.getMoles(0));
        assertEquals(5, b.getMoles(0));
        assertTrue(Float.isFinite(a.getTemperature()));
    }

    @Test void impermeableWallBlocksMassAndHeat() {
        var a = new GasMixture();
        var b = new GasMixture();
        a.setMoles(0, 10);
        a.setTemperature(900);
        a.diffuseTo(b, 0, 1);
        assertEquals(10, a.getMoles(0));
        assertEquals(0, b.getMoles(0));
        assertEquals(900, a.getTemperature());
        assertEquals(294.15f, b.getTemperature());
    }

    private double energy(GasMixture cell) {
        double capacity = 0;
        for (int i = 0; i < GasType.COUNT; i++) {
            capacity += (double) cell.getMoles(i) * GasType.byIndex(i).getHeatCapacity();
        }
        return capacity * cell.getTemperature();
    }
}
