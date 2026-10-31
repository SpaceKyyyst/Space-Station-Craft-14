package net.ssc14.architecture;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LegacyNetworkRegistrationRemovalTest {
    private static final String[] LEGACY_CLASSES = {
            "net.mcreator.ssc.network.ProcessReactionsRegistration",
            "net.mcreator.ssc.network.NetConfigGUISyncRegistration",
            "net.mcreator.ssc.network.NetConfigGUIButtonRegistration",
            "net.mcreator.ssc.network.IDcodeButtonRegistration",
            "net.mcreator.ssc.network.MenuStateUpdateRegistration"
    };

    @Test
    void generatedNetworkRegistrationFacadesAreRemoved() {
        for (String legacyClass : LEGACY_CLASSES) {
            assertThrows(ClassNotFoundException.class, () -> Class.forName(legacyClass), legacyClass);
        }
    }
}
