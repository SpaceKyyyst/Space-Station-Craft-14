package net.ssc14.architecture;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LegacyCommandClientFacadeRemovalTest {
    private static final String[] LEGACY_CLASSES = {
            "net.mcreator.ssc.command.SSC14ChemistryCommandCommand",
            "net.mcreator.ssc.command.SSC14commandsCommand",
            "net.mcreator.ssc.command.Ssc14interactCommand",
            "net.mcreator.ssc.client.HandyMouseCompatibility",
            "net.mcreator.ssc.client.ReagentFluidModels",
            "net.mcreator.ssc.legacy.event.LegacyCargoOrderMenuEvents"
    };

    @Test
    void generatedCommandAndClientFacadesAreRemoved() {
        for (String legacyClass : LEGACY_CLASSES) {
            assertThrows(ClassNotFoundException.class, () -> Class.forName(legacyClass), legacyClass);
        }
    }
}
