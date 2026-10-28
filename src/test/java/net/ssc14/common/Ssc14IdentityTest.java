package net.ssc14.common;

import net.mcreator.ssc.Ssc14Mod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Ssc14IdentityTest {
    @Test
    void modIdRemainsStable() {
        assertEquals("ssc_14", Ssc14Identity.MOD_ID);
        assertEquals(Ssc14Identity.MOD_ID, Ssc14Mod.MODID);
    }
}
