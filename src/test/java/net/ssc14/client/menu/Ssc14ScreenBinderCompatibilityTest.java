package net.ssc14.client.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import net.ssc14.client.Ssc14Screens;
import org.junit.jupiter.api.Test;

class Ssc14ScreenBinderCompatibilityTest {
    @Test
    void canonicalBootstrapBindsEveryScreenToCanonicalBinder() {
        Ssc14Screens.ensureInitialized();
        assertEquals(21, Ssc14ScreenBinder.bindingCount());
    }
}
