package net.ssc14.integration.neoforge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import net.neoforged.fml.common.Mod;
import org.junit.jupiter.api.Test;

class Ssc14ModEntrypointCompatibilityTest {
    @Test
    void canonicalEntrypointOwnsModAnnotation() {
        Mod annotation = Ssc14ModEntrypoint.class.getAnnotation(Mod.class);

        assertNotNull(annotation);
        assertEquals("ssc_14", annotation.value());
        assertNull(net.mcreator.ssc.Ssc14Mod.class.getAnnotation(Mod.class));
    }
}
