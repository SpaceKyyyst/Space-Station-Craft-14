package net.ssc14.client.particle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Ssc14ParticleProviderBinderCompatibilityTest {
    @Test
    void canonicalBootstrapBindsEveryProvider()
            throws ClassNotFoundException {
        Class.forName(Ssc14ParticleClientBootstrap.class.getName());
        assertEquals(32, Ssc14ParticleProviderBinder.bindingCount());
    }
}
