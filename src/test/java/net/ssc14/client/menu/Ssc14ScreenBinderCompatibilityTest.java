package net.ssc14.client.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModScreens;
import org.junit.jupiter.api.Test;

class Ssc14ScreenBinderCompatibilityTest {
    @Test
    void legacyBootstrapBindsEveryScreenToCanonicalBinder()
            throws ClassNotFoundException {
        Class.forName(Ssc14ModScreens.class.getName());
        assertEquals(21, Ssc14ScreenBinder.bindingCount());
    }

    @Test
    void legacyScreenAccessorExtendsCanonicalStateContract() {
        assertTrue(MenuStateReceiver.class.isAssignableFrom(
                Ssc14ModScreens.ScreenAccessor.class));
    }
}
