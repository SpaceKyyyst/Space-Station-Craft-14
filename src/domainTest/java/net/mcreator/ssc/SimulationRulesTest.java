package net.mcreator.ssc;

import net.ssc14.common.atmosphere.model.GasType;
import net.mcreator.ssc.atmosphere.GasMixture;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimulationRulesTest {
    @Test void invalidNumbersCannotPoisonCell() {
        GasMixture cell = new GasMixture();
        cell.setMoles(0, 10);
        cell.setMoles(0, Float.NaN);
        cell.setTemperature(Float.POSITIVE_INFINITY);
        assertEquals(10, cell.getMoles(0));
        assertTrue(Float.isFinite(cell.getPressure()));
    }

    @Test void nonCombustibleMixtureCanAttemptRecipes() {
        GasMixture cell = new GasMixture();
        cell.setMoles(GasType.NITROUS_OXIDE.ordinal(), 10);
        cell.setTemperature(2925);
        assertTrue(cell.canReact());
    }

    @Test void invalidDiffusionDoesNotChangeEitherCell() {
        GasMixture source = new GasMixture();
        GasMixture target = new GasMixture();
        source.setMoles(0, 10);
        assertEquals(0, source.diffuseTo(target, Float.NaN, 1));
        assertEquals(10, source.getMoles(0));
        assertEquals(0, target.getMoles(0));
    }
}
