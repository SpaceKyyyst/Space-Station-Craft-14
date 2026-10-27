package net.ssc14.client.particle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import net.mcreator.ssc.init.Ssc14ModParticles;
import org.junit.jupiter.api.Test;

class Ssc14ParticleProviderBinderCompatibilityTest {
    @Test
    void legacyBootstrapBindsEveryProviderToCanonicalBinder()
            throws ClassNotFoundException {
        Class.forName(Ssc14ModParticles.class.getName());
        assertEquals(32, Ssc14ParticleProviderBinder.bindingCount());
    }
}
